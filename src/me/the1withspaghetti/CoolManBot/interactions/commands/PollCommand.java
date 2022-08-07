package me.the1withspaghetti.CoolManBot.interactions.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.the1withspaghetti.CoolManBot.Main;
import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import me.the1withspaghetti.CoolManBot.interactions.IComponent;
import me.the1withspaghetti.CoolManBot.interactions.ISlashCommand;
import me.the1withspaghetti.CoolManBot.util.Checks;
import me.the1withspaghetti.CoolManBot.util.CoolEmoji;
import me.the1withspaghetti.CoolManBot.util.DataBase;
import me.the1withspaghetti.CoolManBot.util.objects.PollObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.MessageEditCallbackAction;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class PollCommand extends CommandDataImpl implements ISlashCommand, IComponent {
	
	public PollCommand() {
		super("poll", "Use to create polls that people can vote on");
		this.addSubcommands(
				new SubcommandData("create", "Creates a poll for users to vote on")
					.addOption(OptionType.STRING, "title", "The Title for the poll", true)
					.addOption(OptionType.BOOLEAN, "anonymous", "If this poll should show who voted on each option", true)
					.addOption(OptionType.STRING, "option-1", "The 1st option users can vote on", true)
					.addOption(OptionType.STRING, "option-2", "The 2nd option users can vote on", true)
					.addOption(OptionType.STRING, "option-3", "The 3rd option users can vote on", false)
					.addOption(OptionType.STRING, "option-4", "The 4th option users can vote on", false)
					.addOption(OptionType.STRING, "option-5", "The 5th option users can vote on", false),
				new SubcommandData("view", "Views the results of a given poll, only the owner of the poll and admins can do this")
					.addOption(OptionType.STRING, "message-id", "The Id of the poll to view, r-click a message to obtain it", true),
				new SubcommandData("delete", "Deletes a poll and its saved data")
					.addOption(OptionType.STRING, "message-id", "The Id of the poll to delete, r-click a message to obtain it", true)
				);
	}
	
	public String getId() {
		return "poll";
	}
	
	@Override
	public void run(SlashCommandInteractionEvent event) throws ShowEmbedException {
		// Checks for guild
		if (event.getGuild() == null) {
			event.reply(CoolEmoji.ERROR+" This command is only supported in servers.").setEphemeral(true).queue();
			return;
		}
		
		if (event.getSubcommandName().equals("create")) {
			if (!Checks.checkText(event.getOption("title").getAsString())) {
				event.reply(CoolEmoji.ERROR+" The title contains unsupported characters").setEphemeral(true).queue();
				return;
			}
			
			EmbedBuilder emb = new EmbedBuilder();
			emb.setTitle("Poll: **" + event.getOption("title").getAsString() + "**");
			emb.setFooter("Poll by: " + event.getUser().getName() + "#" + event.getUser().getDiscriminator(),
					event.getUser().getAvatarUrl());
			emb.setColor(0xdae83a);
			
			boolean mapped = false;
			List<OptionMapping> args = event.getOptions();
			for (int i = 2; i < args.size(); i++) {
				if (args.get(i).getAsString().length() > 50) mapped = true;
				if (!Checks.checkText(args.get(i).getAsString())) {
					event.reply(CoolEmoji.ERROR+" One or more of the options contain unsupported characters").setEphemeral(true).queue();
					return;
				}
			}
			
			ArrayList<Button> buttons = new ArrayList<Button>();
			if (mapped) {
				for (int i = 2; i < args.size(); i++) {
					emb.addField("**Option "+CoolEmoji.NUMBERS[i-1]+"** - "+args.get(i).getAsString(), "", true);
					buttons.add(Button.secondary("poll:" + (i-1), "(0) "+CoolEmoji.NUMBERS[i-1]));
				}
			} else {
				for (int i = 2; i < args.size(); i++) {
					String option = args.get(i).getAsString();
					if (option.equalsIgnoreCase("yes") || option.equalsIgnoreCase("true")) {
						buttons.add(Button.success("poll:" + (i-1), "(0) " + option));
					} else if (option.equalsIgnoreCase("no") || option.equalsIgnoreCase("false")) {
						buttons.add(Button.danger("poll:" + (i-1), "(0) " + option));
					} else {
						buttons.add(Button.primary("poll:" + (i-1), "(0) " + option));
					}
					if (!event.getOption("anonymous").getAsBoolean()) emb.addField("**"+option+"**", "No votes", true);
				}
			}
			ArrayList<String> options = new ArrayList<String>(args.size() - 2);
			for (int i = 2; i < args.size(); i++)
				options.add(args.get(i).getAsString());
			final boolean mappedFinal = mapped;
			event.replyEmbeds(emb.build()).addActionRow(buttons).queue((hook) -> {
				hook.retrieveOriginal().queue((msg) -> {
					DataBase.addPoll(msg.getIdLong(), event.getUser().getIdLong(), event.getOption("anonymous").getAsBoolean(), mappedFinal, event.getOption("title").getAsString(), options);
				});
			}); 
		} else if (event.getSubcommandName().equals("view")) {
			
			long messageId;
			try {
				messageId = Long.valueOf(event.getOption("message-id").getAsString());
			} catch (Exception e) {
				event.reply(CoolEmoji.ERROR+" You must input a valid message Id").queue();
				return;
			}
			
			PollObject poll = DataBase.getPoll(messageId);
			if (poll == null) {
				event.reply(CoolEmoji.ERROR+" That poll does not exist.").setEphemeral(true).queue();
				return;
			}
			if (!(event.getMember().hasPermission(Permission.MESSAGE_MANAGE) || event.getUser().getIdLong() == poll.owner)) {
				event.reply(CoolEmoji.ERROR+" You do not have permission to view those poll results.").setEphemeral(true).queue();
				return;
			}
			
			EmbedBuilder emb = new EmbedBuilder();
			emb.setColor(0xdae83a);
			emb.setTitle("Results for poll: " + poll.title);
			for (int i = 0; i < poll.options.length; i++) {
				StringBuilder str = new StringBuilder();
				for (Entry<Long, Integer> v : poll.votes.entrySet()) {
					if (v.getValue().equals(i + 1))
						str.append("<@" + v.getKey() + ">\n");
				}
				if (str.length() == 0) str.append("No Votes");
				emb.addField(
						"**Option " + (i + 1) + "** - " + poll.options[i],
						str.toString(), true);
			}
			event.replyEmbeds(emb.build()).queue();
			
		} else if (event.getSubcommandName().equals("delete")) {
			
			long messageId;
			try {
				messageId = Long.valueOf(event.getOption("message-id").getAsString());
			} catch (Exception e) {
				event.reply(CoolEmoji.ERROR+" You must input a valid message Id").queue();
				return;
			}
			
			event.getChannel().retrieveMessageById(messageId).queue((message) -> {
				if (message.getAuthor().getId().equals(Main.jda.getSelfUser().getId())
						&& message.getEmbeds().get(0).getTitle().startsWith("Poll: ")) {
					PollObject poll = DataBase.getPoll(messageId);
					if (poll == null) {
						event.reply(CoolEmoji.ERROR+" That poll does not exist.").setEphemeral(true).queue();
						return;
					}
					if (!(event.getMember().hasPermission(Permission.MESSAGE_MANAGE) || event.getUser().getIdLong() == poll.owner)) {
						event.reply(CoolEmoji.ERROR+" You do not have permission to delete that poll").setEphemeral(true).queue();
						return;
					}
					DataBase.removePoll(poll.messageId);
					message.delete().queue();
					event.reply(CoolEmoji.CHECK+" Poll successfully removed!").setEphemeral(true).queue();
				} else {
					event.reply(CoolEmoji.ERROR+" That message does not contain a poll!").setEphemeral(true).queue();
				}
			}, (failure) -> {
				event.reply(CoolEmoji.ERROR+" Unable to find that message! Make sure your in the same channel!").setEphemeral(true).queue();
			});
			
		}
		
	}

	@Override
	public void onButtonPress(ButtonInteractionEvent event) throws ShowEmbedException {
		PollObject poll = DataBase.getPoll(event.getMessage().getIdLong());
		List<Button> oldButtons = event.getMessage().getButtons();
		
		Integer vote = Integer.valueOf(event.getComponentId().substring(5));
		poll.votes.put(event.getUser().getIdLong(), vote);
		DataBase.updatePoll(poll);

		ArrayList<Button> newButtons = new ArrayList<Button>(oldButtons.size());
		for (Button b : oldButtons) {
			int cVote = Integer.valueOf(b.getId().substring(5));
			newButtons.add(Button.of(b.getStyle(), b.getId(), "(" + getVotes(poll.votes, cVote)
					+ ") " + (poll.mapped ? CoolEmoji.NUMBERS[cVote] : poll.options[cVote-1])));
		}
		
		MessageEditCallbackAction action = event.editComponents(ActionRow.of(newButtons));
		
		if (!poll.anonymous) {
			EmbedBuilder emb = new EmbedBuilder(event.getMessage().getEmbeds().get(0));
			emb.clearFields();
			if (poll.mapped) {
				for (int i = 0; i < poll.options.length; i++) {
					StringBuilder str = new StringBuilder();
					for (Entry<Long, Integer> v : poll.votes.entrySet()) {
						if (v.getValue().equals(i + 1))
							str.append("<@" + v.getKey() + ">\n");
					}
					if (str.length() == 0) str.append("No Votes");
					emb.addField("**Option "+CoolEmoji.NUMBERS[i+1]+"** - "+poll.options[i], str.toString(), true);
				}
			} else {
				for (int i = 0; i < poll.options.length; i++) {
					StringBuilder str = new StringBuilder();
					for (Entry<Long, Integer> v : poll.votes.entrySet()) {
						if (v.getValue().equals(i + 1))
							str.append("<@" + v.getKey() + ">\n");
					}
					if (str.length() == 0) str.append("No Votes");
					emb.addField("**"+poll.options[i]+"**", str.toString(), true);
				}
			}
			action.setEmbeds(emb.build());
		}
		
		action.queue();
	}
	
	private int getVotes(HashMap<Long, Integer> votes, Integer voteId) {
		int count = 0;
		for (Entry<Long, Integer> i : votes.entrySet())
			if (i.getValue() == voteId) count++;
		return count;
	}
	
	// Unused
	
	@Override
	public void onAutoComplete(CommandAutoCompleteInteractionEvent event) throws ShowEmbedException {}
	@Override
	public void onSelection(SelectMenuInteractionEvent event) throws ShowEmbedException {}
	@Override
	public void onModalSubmit(ModalInteractionEvent event) throws ShowEmbedException {}
	
}

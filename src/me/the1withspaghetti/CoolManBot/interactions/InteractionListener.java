package me.the1withspaghetti.CoolManBot.interactions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InteractionListener extends ListenerAdapter {
	
	private ISlashCommand[] commands;
	private IUserContext[] userContext;
	private IMessageContext[] messageContext;
	
	public InteractionListener(ISlashCommand[] commands, IUserContext[] userContext, IMessageContext[] messageContext) {
		this.commands = commands;
		this.userContext = userContext;
		this.messageContext = messageContext;
	}
	
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		try {
			for (ISlashCommand cmd : commands) {
				if (cmd.getName().equalsIgnoreCase(event.getName())) {
					cmd.run(event);
					return;
				}
			}
			throw new ShowEmbedException("Unrecognized Command", event.getUser());
		} catch (Exception exception) {
			ShowEmbedException e;
			if (exception instanceof ShowEmbedException) e = (ShowEmbedException) exception;
			else e = new ShowEmbedException(exception);
			e.addFields(
				"Command Name", event.getName(),
				"Command Path", event.getCommandPath(),
				"Channel", event.getChannel().getName()+" ("+event.getChannel().getId()+")");
			if (event.getGuild() != null) e.addField("Guild", event.getGuild().getName()+" ("+event.getGuild().getId()+")");
			e.postEmbed();
			if (!event.isAcknowledged()) event.reply("There seems to be an error, this command is not recognized! We will report this issue to the developer.").setEphemeral(true).queue();
		}
	}
	
	@Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
		try {
			for (IUserContext cmd : userContext) {
				if (ArrayUtils.contains(cmd.getNames(), event.getName())) {
					cmd.run(event);
					return;
				}
			}
			throw new ShowEmbedException("Unrecognized Command Name", event.getUser());
		} catch (Exception exception) {
			ShowEmbedException e;
			if (exception instanceof ShowEmbedException) e = (ShowEmbedException) exception;
			else e = new ShowEmbedException(exception);
			e.addFields(
				"Command Name", event.getName(),
				"Channel", event.getChannel().getName()+" ("+event.getChannel().getId()+")");
			if (event.getGuild() != null) e.addField("Guild", event.getGuild().getName()+" ("+event.getGuild().getId()+")");
			e.postEmbed();
		}
    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {
    	try {
    		for (IMessageContext cmd : messageContext) {
				if (ArrayUtils.contains(cmd.getNames(), event.getName())) {
					cmd.run(event);
					return;
				}
			}
			throw new ShowEmbedException("Unrecognized Command Name", event.getUser());
		} catch (Exception exception) {
			ShowEmbedException e;
			if (exception instanceof ShowEmbedException) e = (ShowEmbedException) exception;
			else e = new ShowEmbedException(exception);
			e.addFields(
				"Command Name", event.getName(),
				"Channel", event.getChannel().getName()+" ("+event.getChannel().getId()+")");
			if (event.getGuild() != null) e.addField("Guild", event.getGuild().getName()+" ("+event.getGuild().getId()+")");
			e.postEmbed();
		}
    }
	
	@Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
		try {
			for (ISlashCommand cmd : commands) {
				if (cmd.getName().equalsIgnoreCase(event.getName())) {
					cmd.onAutoComplete(event);
					return;
				}
			}
			throw new ShowEmbedException("Unrecognized Command", event.getUser());
		} catch (Exception exception) {
			ShowEmbedException e;
			if (exception instanceof ShowEmbedException) e = (ShowEmbedException) exception;
			else e = new ShowEmbedException(exception);
			e.addFields(
				"Command Name", event.getName(),
				"Command Path", event.getCommandPath(),
				"Channel", event.getChannel().getName()+" ("+event.getChannel().getId()+")");
			if (event.getGuild() != null) e.addField("Guild", event.getGuild().getName()+" ("+event.getGuild().getId()+")");
			e.postEmbed();
		}
    }
	
	@Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
		try {
			for (ISlashCommand cmd : commands) {
				if (cmd instanceof IComponent) {
					IComponent c = (IComponent) cmd;
					if (event.getComponentId().startsWith(c.getId())) {
						c.onButtonPress(event);
						return;
					}
				}
			}
			throw new ShowEmbedException("Unrecognized Button Id", event.getUser());
		} catch (Exception exception) {
			ShowEmbedException e;
			if (exception instanceof ShowEmbedException) e = (ShowEmbedException) exception;
			else e = new ShowEmbedException(exception);
			e.addFields(
				"Button Id", event.getComponentId(),
				"Channel", event.getChannel().getName()+" ("+event.getChannel().getId()+")");
			if (event.getGuild() != null) e.addField("Guild", event.getGuild().getName()+" ("+event.getGuild().getId()+")");
			e.postEmbed();
		}
    }
	
	@Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
		try {
			for (ISlashCommand cmd : commands) {
				if (cmd instanceof IComponent) {
					IComponent c = (IComponent) cmd;
					if (event.getComponentId().startsWith(c.getId())) {
						c.onSelection(event);
						return;
					}
				}
			}
			throw new ShowEmbedException("Unrecognized Select Menu", event.getUser());
		} catch (Exception exception) {
			ShowEmbedException e;
			if (exception instanceof ShowEmbedException) e = (ShowEmbedException) exception;
			else e = new ShowEmbedException(exception);
			e.addFields(
				"Select Menu Id", event.getComponentId(),
				"Channel", event.getChannel().getName()+" ("+event.getChannel().getId()+")");
			if (event.getGuild() != null) e.addField("Guild", event.getGuild().getName()+" ("+event.getGuild().getId()+")");
			e.postEmbed();
		}
    }
	
	@Override
    public void onModalInteraction(ModalInteractionEvent event) {
		try {
			for (ISlashCommand cmd : commands) {
				if (cmd instanceof IComponent) {
					IComponent c = (IComponent) cmd;
					if (event.getModalId().startsWith(c.getId())) {
						c.onModalSubmit(event);
						return;
					}
				}
			}
			throw new ShowEmbedException("Unrecognized Select Menu", event.getUser());
		} catch (Exception exception) {
			ShowEmbedException e;
			if (exception instanceof ShowEmbedException) e = (ShowEmbedException) exception;
			else e = new ShowEmbedException(exception);
			e.addFields(
				"Modal Id", event.getModalId(),
				"Channel", event.getChannel().getName()+" ("+event.getChannel().getId()+")");
			if (event.getGuild() != null) e.addField("Guild", event.getGuild().getName()+" ("+event.getGuild().getId()+")");
			e.postEmbed();
		}
    }
	
	
	// Breathing club stuff
	String[] channels = {"cool-man-channel-become-cool-man-and-do-cool-thing-thank-you","cool-man-channel"};
	ArrayList<Guild> dinoServers = new ArrayList<Guild>();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.isFromGuild()) return;
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
			// Cool Man Channel
			if (ArrayUtils.contains(channels,event.getChannel().getName()) && !event.getAuthor().isBot()) {
				List<Role> roles = event.getGuild().getRolesByName("COOL MAN", true);
				if (roles.size() == 0) return;
				Role coolMan = roles.get(0);
				
				List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();
				if (messages.size() > 1) event.getGuild().removeRoleFromMember(messages.get(1).getAuthor(), coolMan).queue();
				event.getGuild().addRoleToMember(event.getMember(), coolMan).queue();
			}
			
			// VC doggo
			if (event.getMessage().getContentRaw().contains("vc") && event.getGuild().getId().equals("737472458780704858")) {
				event.getMessage().addReaction(Emoji.fromFormatted("<:doggoreactiondonotdelete:815053317670567937>")).queue();
			}
			
			// Super Epic Dino Control
			if (args[0].equalsIgnoreCase("~superepicdino") && (event.getAuthor().getId().equals("737494537106096161") || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
				event.getMessage().delete().queue();
				switch (args[1]) {
				case "enable":
					dinoServers.add(event.getGuild());
					event.getChannel().sendMessage(":sauropod:").queue();
					break;
				case "disable":
					dinoServers.remove(event.getGuild());
					event.getChannel().sendMessage("rip dino :sauropod:").queue();
					break;
				}
			}
			
			// Super Epic Dino Reactions
			if (dinoServers.contains(event.getGuild()) && !args[0].equalsIgnoreCase("~superepicdino")) {
				event.getMessage().addReaction(Emoji.fromUnicode("U+1F995")).queue();
			}
	}
}

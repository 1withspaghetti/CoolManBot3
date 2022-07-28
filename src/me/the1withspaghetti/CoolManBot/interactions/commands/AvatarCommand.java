package me.the1withspaghetti.CoolManBot.interactions.commands;


import me.the1withspaghetti.CoolManBot.Main;
import me.the1withspaghetti.CoolManBot.interactions.ISlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class AvatarCommand extends CommandDataImpl implements ISlashCommand {

	public AvatarCommand() {
		super("avatar", "Gets the avatar of a user");
		addOption(OptionType.USER, "user", "The user to get the avatar from.", false);
	}

	@Override
	public void run(SlashCommandInteractionEvent event) {
		User user;
		if (event.getOptions().isEmpty()) {
			user = event.getUser();
		} else {
			user = event.getOption("user").getAsUser();
		}
		
		EmbedBuilder emb = new EmbedBuilder();
		emb.clear();
		emb.setAuthor(user.getName()+"#"+user.getDiscriminator());
		emb.setTitle("Avatar");
		emb.setImage(user.getEffectiveAvatarUrl()+"?size=256");
		emb.setColor(Main.COLOR);
		
		event.replyEmbeds(emb.build()).queue();
	}

	@Override
	public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
		// TODO Auto-generated method stub
		
	}

	

}

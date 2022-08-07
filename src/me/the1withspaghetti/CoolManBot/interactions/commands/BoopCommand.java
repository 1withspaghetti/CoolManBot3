package me.the1withspaghetti.CoolManBot.interactions.commands;

import me.the1withspaghetti.CoolManBot.Main;
import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import me.the1withspaghetti.CoolManBot.interactions.ISlashCommand;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class BoopCommand extends CommandDataImpl implements ISlashCommand {
	
	private String[] msgs = {
			"%2 has been **booped** by %1!",
			"%1 just **booped** %2",
			"%1 just **booped** %2 (°ロ°)",
			"Hey %2, how does it feel to be **booped** by %1?",
			"%2 must suffer the wraith of %1's **boop**!",
			"%1 **booped** %2!",
			"%2 just got **booped** by %1",
			"Who **booped** %2? %1 did!",
			"Breaking News! %1 just **booped** %2!"
	};
	
	public BoopCommand() {
		super("boop", "Boop a user (* ^ ω ^)");
		this.addOption(OptionType.USER, "user", "The user too boop", true);
	}

	@Override
	public void run(SlashCommandInteractionEvent event) throws ShowEmbedException {
		User user = event.getOption("user").getAsUser();
		if (user.getId().equals(Main.jda.getSelfUser().getId())) {
			event.reply("I have been **booped** by "+event.getUser().getAsMention()+" (* ^ ω ^)").queue();
		} else if (event.getUser().getId().equals(user.getId())) {
			event.reply(event.getUser().getAsMention()+" just **booped** themselves! (°ロ°)").queue();
		} else {
			event.reply(randomItem(msgs).replace("%1", event.getUser().getAsMention()).replace("%2", user.getAsMention())).queue();
		}
		
	}
	
	private String randomItem(String[] list) {
		return list[(int) Math.floor(Math.random() * list.length)];
	}

	// Unused
	@Override
	public void onAutoComplete(CommandAutoCompleteInteractionEvent event) throws ShowEmbedException {}
}

package me.the1withspaghetti.CoolManBot.interactions;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface ISlashCommand extends CommandData {
	
	public void run(SlashCommandInteractionEvent event) throws ShowEmbedException;
	
	public void onAutoComplete(CommandAutoCompleteInteractionEvent event) throws ShowEmbedException;
}

package me.the1withspaghetti.CoolManBot.interactions;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/*
 *  Use with UserContextInteractionImpl
 */
public interface IUserContext {
	
	public CommandData[] getCommands();
	
	public String[] getNames();
	
	public void run(UserContextInteractionEvent event) throws ShowEmbedException;
	
}

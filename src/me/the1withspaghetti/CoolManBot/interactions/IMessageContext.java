package me.the1withspaghetti.CoolManBot.interactions;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/*
 *  Use with MessageContextInteractionImpl
 */
public interface IMessageContext {
	
	public CommandData[] getCommands();
	
	public String[] getNames();
	
	public void run(MessageContextInteractionEvent event) throws ShowEmbedException;
	
}

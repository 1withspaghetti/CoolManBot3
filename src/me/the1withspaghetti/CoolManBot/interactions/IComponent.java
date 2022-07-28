package me.the1withspaghetti.CoolManBot.interactions;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

public interface IComponent {

	public String getId();
	
	public void onButtonPress(ButtonInteractionEvent event) throws ShowEmbedException;
	
	public void onSelection(SelectMenuInteractionEvent event) throws ShowEmbedException;
	
	public void onModalSubmit(ModalInteractionEvent event) throws ShowEmbedException;
}

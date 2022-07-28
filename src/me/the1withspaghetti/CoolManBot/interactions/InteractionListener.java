package me.the1withspaghetti.CoolManBot.interactions;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InteractionListener extends ListenerAdapter {
	
	private ISlashCommand[] commands;
	
	public InteractionListener(ISlashCommand[] commands) {
		this.commands = commands;
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
}

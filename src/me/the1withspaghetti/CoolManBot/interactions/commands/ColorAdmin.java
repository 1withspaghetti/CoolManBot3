package me.the1withspaghetti.CoolManBot.interactions.commands;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import me.the1withspaghetti.CoolManBot.interactions.ISlashCommand;
import me.the1withspaghetti.CoolManBot.util.CoolEmoji;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class ColorAdmin extends CommandDataImpl implements ISlashCommand {

	public ColorAdmin() {
		super("colorrole-admin", "Used to setup and uninstall color roles in a discord server");
		setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_ROLES));
		addSubcommands(
				new SubcommandData("setup", "Adds all "+ColorCommand.COLORS.length+" roles needed to use the color roles feature"),
				new SubcommandData("uninstall", "Removes all the roles used for coloring")
				);
	}

	@Override
	public void run(SlashCommandInteractionEvent event) throws ShowEmbedException {
		// Checks for guild
		if (event.getGuild() == null) {
			event.reply(CoolEmoji.ERROR+" This command is only supported in servers.").setEphemeral(true).queue();
			return;
		}
		
		if (!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
			event.reply("<:error:833895641000050690> Your must have the `Manage Roles` permission to use this.").setEphemeral(true).queue();
			return;
		}
		
		if (event.getSubcommandName().equals("setup")) {
			
			event.deferReply(true);
			
			for (int i = 0; i < ColorCommand.COLORS.length; i++) {
				if (event.getGuild().getRolesByName(ColorCommand.COLORS[i], true).isEmpty()) {
					event.getGuild().createRole().setPermissions((long) 0).setColor(ColorCommand.VALUES[i]).setName(ColorCommand.COLORS[i]).queue((r) -> {
						if (r.getName().equals(ColorCommand.COLORS[ColorCommand.COLORS.length - 1]))
							event.reply(CoolEmoji.CHECK+" Done, I recommend you make all other roles colorless so that these roles have color priority!").setEphemeral(true).queue();
					});
				}
			}
			
		} else if (event.getSubcommandName().equals("uninstall")) {

			event.deferReply(true);
			
			for (int i = 0; i < ColorCommand.COLORS.length; i++) {
				event.getGuild().getRolesByName(ColorCommand.COLORS[i], true).forEach((role) -> role.delete().queue((res) -> {
					if (role.getName().equals(ColorCommand.COLORS[ColorCommand.COLORS.length - 1]))
						event.reply(CoolEmoji.CHECK+" Successfully removed all color roles").setEphemeral(true).queue();
				}));
			}
			
		} else {
			throw new ShowEmbedException("Unknown Subcommand");
		}
		
	}

	@Override
	public void onAutoComplete(CommandAutoCompleteInteractionEvent event) throws ShowEmbedException {} // unused
}

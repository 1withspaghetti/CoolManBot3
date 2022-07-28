package me.the1withspaghetti.CoolManBot.interactions.commands;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import me.the1withspaghetti.CoolManBot.Main;
import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import me.the1withspaghetti.CoolManBot.interactions.IComponent;
import me.the1withspaghetti.CoolManBot.interactions.ISlashCommand;
import me.the1withspaghetti.CoolManBot.util.CoolEmoji;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu.Builder;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class ColorCommand extends CommandDataImpl implements ISlashCommand, IComponent {
	
	final static String[] COLORS = {"Red","Orange","DarkOrange","Yellow","PlasticGreen","Forest","ArmyGreen","DarkGreen","Seaweed","Pond","Stone","Raw Pink","Aquamarine","Aqua","Blue","Rain","CornFlower","DarkBlue","Storm","Bluewood","Purple","DarkViolet","DarkOrchid","Lilac","LightPink","Pink","Sand","Blush","Salmon","Blossum","RoseBrown","HotPink","Fuchsia","Rose","De1e7e","Magenta","White","PayneGrey","DarkGrey","Black"};
	final static Integer[] VALUES = {0xf52100,0xff7f27,0xaf471e,0xffff55,0x52d170,0x7db379,0x4b5320,0x036e52,0x0e9979,0xa8c5ff,0xb1bfce,0xd84e68,0x7fffd4,0x00ffff,0x00ffff,0x7f97c6,0x6495ed,0x0855fa,0x20457d,0x293f4e,0x732ea1,0x9400d3,0x9932cc,0x9e7ba6,0xfacade,0xffc0cb,0xf4e4c8,0xff9393,0xfa8072,0xff91a4,0xbc8f8f,0xff69b4,0xff33cc,0xe73478,0xde1e7e,0xd40c5a,0xffffff,0x536878,0x2e3136,0x050505};
	final static String[] EMOJI = {"<:F52100:1002021541258727445>","<:FF7F27:1002021542869336145>","<:AF471E:1002021543792091278>","<:FFFF55:1002021545222340658>","<:52D170:1002021545918595134>","<:7DB379:1002021547256582315>","<:4B5320:1002021547730542624>","<:036E52:1002021549097885806>","<:0E9979:1002021550016430130>","<:A8C5FF:1002021551098568724>","<:B1BFCE:1002021552059072613>","<:D84E68:1002021553099247626>","<:7FFFD4:1002021554630172823>","<:00FFFF:1002021557318721586>","<:00FFFF:1002021557318721586>","<:7F97C6:1002021558220501032>","<:6495ED:1002021559462019163>","<:0855FA:1002021560405741729>","<:20457D:1002021561483665428>","<:293F4E:1002021562930700318>","<:732EA1:1002021563517906986>","<:9400D3:1002021564746846328>","<:9932CC:1002021565782818929>","<:9E7BA6:1002021567091445761>","<:FACADE:1002021568030982285>","<:FFC0CB:1002021568605601833>","<:F4E4C8:1002021570153291936>","<:FF9393:1002021571117981769>","<:FA8072:1002021572137201684>","<:FF91A4:1002021573559062528>","<:BC8F8F:1002021574657978378>","<:FF69B4:1002021575933034547>","<:FF33CC:1002021576830623805>","<:E73478:1002021577875013762>","<:DE1E7E:1002021579204603974>","<:D40C5A:1002023007771959388>","<:FFFFFF:1002023008988319854>","<:536878:1002023010133344348>","<:2E3136:1002023011567796265>","<:050505:1002023012532494406>"};
	
	public ColorCommand() {
		super("colorrole", "Use to assign yourself with a custom role color");
	}
	
	public String getId() {
		return "color";
	}

	@Override
	public void run(SlashCommandInteractionEvent event) {
		// Checks for guild
		if (event.getGuild() == null) {
			event.reply(CoolEmoji.ERROR+" This command is only supported in servers.").setEphemeral(true).queue();
			return;
		}
		// Checks for the RED role
		if (event.getGuild().getRolesByName(COLORS[0], true).size() < 1) {
			event.reply(CoolEmoji.ERROR+" This server has not set up color roles yet, contact an admin to do so.").setEphemeral(true).queue();
			return;
		}
		// Builds embed
		EmbedBuilder emb = new EmbedBuilder();
		emb.setColor(Main.COLOR);
		emb.setTitle("Pick Your Role Color!");
		emb.setDescription("Choose from any of the options below");
		// Builds select menus
		Builder b1 = SelectMenu.create("color:1select").setPlaceholder("Select Color...");
		for (int i = 0; i < COLORS.length/2; i++) {
			if (event.getGuild().getRolesByName(COLORS[i], true).size() > 0)
				b1.addOption(COLORS[i], COLORS[i], Emoji.fromFormatted(EMOJI[i]));
		}
		Builder b2 = SelectMenu.create("color:2select").setPlaceholder("Select Color...");
		for (int i = COLORS.length/2; i < COLORS.length; i++) {
			if (event.getGuild().getRolesByName(COLORS[i], true).size() > 0)
				b2.addOption(COLORS[i], COLORS[i], Emoji.fromFormatted(EMOJI[i]));
		}
		Button remove = Button.danger("color:remove", "Remove Role Color");
		
		event.replyEmbeds(emb.build()).addActionRow(b1.build()).addActionRow(b2.build()).addActionRow(remove).queue();
	}

	@Override
	public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {} // Unused

	@Override
	public void onButtonPress(ButtonInteractionEvent event) throws ShowEmbedException {
		if (event.getComponentId().endsWith("remove")) {
			// Removes all color roles on button press
			event.getMember().getRoles().forEach((n) -> {
				if (ArrayUtils.contains(COLORS, n.getName())) {
					event.getGuild().removeRoleFromMember(event.getMember(), n).queue();
				}
			});
			event.reply(CoolEmoji.CHECK+" Done!").setEphemeral(true).queue();
		} else {
			throw new ShowEmbedException("Unrecognized secondary button id");
		}
	}

	@Override
	public void onSelection(SelectMenuInteractionEvent event) throws ShowEmbedException {
		if (event.getComponentId().endsWith("select")) {
			
			String role = event.getValues().get(0);
			// Checks this it is a color
			if (!ArrayUtils.contains(COLORS, role)) {
				event.reply(CoolEmoji.ERROR+" There has been an error with your request.").setEphemeral(true).queue();
				throw new ShowEmbedException("Unrecognized color selection").addField("Selected Color", role);
			}
			// Checks the role exists in the guild
			List<Role> roles = event.getGuild().getRolesByName(role, true);
			if (roles.isEmpty()) {
				event.reply(CoolEmoji.ERROR+" That color is unavilible at the moment, contact a server admin if you belive this is an error").setEphemeral(true).queue();
			}
			// Removes all previous color roles
			event.getMember().getRoles().forEach((n) -> {
				if (ArrayUtils.contains(COLORS, n.getName())) {
					event.getGuild().removeRoleFromMember(event.getMember(), n).queue();
				}
			});
			// Adds role and replies with check once done
			event.getGuild().addRoleToMember(event.getMember(), roles.get(0)).queue((v) -> {
				event.reply(CoolEmoji.CHECK+" Done!").setEphemeral(true).queue();
			});
		} else {
			throw new ShowEmbedException("Unrecognized secondary menu id");
		}
	}

	@Override
	public void onModalSubmit(ModalInteractionEvent event) {} // Unused
	
}

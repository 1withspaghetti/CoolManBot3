package me.the1withspaghetti.CoolManBot;

import me.the1withspaghetti.CoolManBot.interactions.ISlashCommand;
import me.the1withspaghetti.CoolManBot.interactions.InteractionListener;
import me.the1withspaghetti.CoolManBot.interactions.commands.AvatarCommand;
import me.the1withspaghetti.CoolManBot.interactions.commands.ColorAdmin;
import me.the1withspaghetti.CoolManBot.interactions.commands.ColorCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
	
	public static JDA jda;
	public static final String PREFIX = "~";
	public static final int COLOR = 0xdae83a;

	public static void main(String[] args) throws Exception {
		
		//jda = JDABuilder.createDefault("ODMzMDg1MjcwNTMzNDcyMzU2.YHtNHg.kijpi0jHoIrVMTExA98Jrg5sMZY").build();
		jda = JDABuilder.createDefault("ODkzMzExMzIxMjQyNzUxMDE3.Gn27w-.MC6eQGh8H8QqGhMYFTmLH2PrEZ8gal_IAv96YE").build();
		
		//jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, "Cool Man Game", "https://www.youtube.com/watch?v=tPEE9ZwTmy0"));
		jda.awaitReady();
		
		ISlashCommand[] commands = {
				new AvatarCommand(),
				new ColorCommand(),
				new ColorAdmin()
			};
		jda.updateCommands().addCommands(commands).queue();
		jda.addEventListener(new InteractionListener(commands));
	}

}

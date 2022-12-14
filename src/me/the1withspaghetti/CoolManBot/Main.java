package me.the1withspaghetti.CoolManBot;

import java.io.File;
import me.the1withspaghetti.CoolManBot.interactions.IMessageContext;
import me.the1withspaghetti.CoolManBot.interactions.ISlashCommand;
import me.the1withspaghetti.CoolManBot.interactions.IUserContext;
import me.the1withspaghetti.CoolManBot.interactions.InteractionListener;
import me.the1withspaghetti.CoolManBot.interactions.commands.AvatarCommand;
import me.the1withspaghetti.CoolManBot.interactions.commands.BoopCommand;
import me.the1withspaghetti.CoolManBot.interactions.commands.ColorAdmin;
import me.the1withspaghetti.CoolManBot.interactions.commands.ColorCommand;
import me.the1withspaghetti.CoolManBot.interactions.commands.PollCommand;
import me.the1withspaghetti.CoolManBot.interactions.commands.Uwuify;
import me.the1withspaghetti.CoolManBot.util.DataBase;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class Main {
	
	public static JDA jda;
	public static final String PREFIX = "~";
	public static final int COLOR = 0xdae83a;

	public static void main(String[] args) throws Exception {
		
		if (args.length != 1) {
			System.err.println("Usage: java -jar CoolManBot.jar BOT_TOKEN");
			System.exit(1);
		}
		
		jda = JDABuilder.createDefault(args[0]).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
		jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, "Cool Man Game", "https://www.youtube.com/watch?v=tPEE9ZwTmy0"));
		jda.awaitReady();
		
		DataBase.connect(System.getProperty("user.dir")+File.separator+"cool_man.db");
		
		ISlashCommand[] commands = {
				new AvatarCommand(),
				new ColorCommand(),
				new ColorAdmin(),
				new PollCommand(),
				new BoopCommand()
			};
		IUserContext[] userContext = {
				
		};
		IMessageContext[] messageContext = {
				new Uwuify()
		};
		
		CommandListUpdateAction action = jda.updateCommands().addCommands(commands);
		for (IUserContext i : userContext) action.addCommands(i.getCommands());
		for (IMessageContext i : messageContext) action.addCommands(i.getCommands());
		action.queue();
		jda.addEventListener(new InteractionListener(commands, userContext, messageContext));
	}

}

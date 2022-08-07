package me.the1withspaghetti.CoolManBot.interactions.commands;

import java.util.regex.Pattern;

import me.the1withspaghetti.CoolManBot.exceptions.ShowEmbedException;
import me.the1withspaghetti.CoolManBot.interactions.IMessageContext;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Uwuify implements IMessageContext {
	
	private static final String[] commands = {"Uwuify", "Ussify"};
	
	@Override
	public CommandData[] getCommands() {
		return new CommandData[] {
			Commands.message("Uwuify"),
			Commands.message("Ussify")
		};
	}

	@Override
	public String[] getNames() {
		return commands;
	}
	
	
	private static final Pattern vowel_end = Pattern.compile("[aeiouy]+$", Pattern.CASE_INSENSITIVE);
	private static final Pattern uss_end = Pattern.compile("(us)(s)?$", Pattern.CASE_INSENSITIVE);
	@SuppressWarnings("unused")
	private static final Pattern inserts = Pattern.compile("[.,;:'\"_\\-*!? +=\\/]+", Pattern.CASE_INSENSITIVE);
	/*final Pattern vowel_letter = Pattern.compile("[aeiou][bcdfghjklmnzqrstvwxyz]$", Pattern.CASE_INSENSITIVE);
	final Pattern letter_vowel = Pattern.compile("[bcdfghjklmnzqrstvwxyz][aeiou]$", Pattern.CASE_INSENSITIVE);*/
	
	private static final String[] kaomojiJoy        = {" (* ^ ω ^)", " (o^▽^o)", " (≧◡≦)", " ☆⌒ヽ(*\"､^*)chu", " ( ˘⌣˘)♡(˘⌣˘ )", " xD"};
	private static final String[] kaomojiEmbarassed = {" (⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)..", " (*^.^*)..,", "..,", ",,,", "... ", ".. ", " mmm..", "O.o"};
	private static final String[] kaomojiConfuse    = {" (o_O)?", " (°ロ°) !?", " (ーー;)?", " owo?"};
	private static final String[] kaomojiSparkles   = {" *:･ﾟ✧*:･ﾟ✧ ", " ☆*:・ﾟ ", "〜☆ ", " uguu.., ", "-.-"};

	@Override
	public void run(MessageContextInteractionEvent event) throws ShowEmbedException {
		if (event.getTarget().getContentRaw().isBlank()) {
			event.reply("Cannot uwuify a message with no text! UwU").setEphemeral(true).queue();
			return;
		}
			
		
		if (event.getName().equalsIgnoreCase("ussify")) {
			
			/*StringBuilder str = new StringBuilder(event.getTarget().getContentRaw());
			Matcher m = inserts.matcher(str);
			
			int indented = 0;
			while (m.find()) {
				str.insert(m.start()+indented, "ussy");
				indented+=4;
			}*/
			
			String[] split = event.getTarget().getContentRaw().split(" ");
			StringBuilder str = new StringBuilder();
			for (String i : split) {
				if (i.length() > 2) {
					i = vowel_end.matcher(i).replaceAll("");
				}
				uss_end.matcher(i).replaceAll("");
				str.append(i+"ussy ");
			}
			event.reply(str.toString()).queue();
			
		}
		if (event.getName().equalsIgnoreCase("uwuify")) {
			
			String[] words = event.getTarget().getContentRaw().split(" ");
			StringBuilder msg = new StringBuilder();
			for (String i : words) {
				msg.append(uwu_word(i));
			}
			
			event.reply(msg.toString()).queue();
		}
	}
	
	private String uwu_word(String word) {
		if (word.length() < 1) return "";
		StringBuilder uwu = new StringBuilder();
		
		char lastChar = word.charAt(word.length() - 1);
		String end = "";
		
		if (lastChar == '.' || lastChar == '?' || lastChar == '!' || lastChar == ',') {
			word = word.substring(0, word.length() - 1);
			end = lastChar+"";
			if (lastChar == '.') {
				if (randomChance(3)) end = randomItem(kaomojiJoy);
			}
			else if (lastChar == '?') {
				if (randomChance(2)) end = randomItem(kaomojiConfuse);
			}
			else if (lastChar == '!') {
				if (randomChance(2)) end = randomItem(kaomojiJoy);
			}
			else if (lastChar == ',') {
				if (randomChance(3)) end = randomItem(kaomojiEmbarassed);
			}
			
			if (randomChance(4)) {
				end = randomItem(kaomojiSparkles);
			}
		}
		
		if (word.indexOf('l') > -1) {
			if (word.endsWith("le") || word.endsWith("ll")) {
				uwu.append(word.substring(0, word.length()-2).replace("l", "w").replace("r", "w") + word.substring(word.length()-2) + end + ' ');
			}
			else if (word.endsWith("les") || word.endsWith("lls")) {
				uwu.append(word.substring(0, word.length()-3).replace("l", "w").replace("r", "w") + word.substring(word.length()-3) + end + ' ');
			}
			else {
				uwu.append(word.replace("l", "w").replace("r", "w") + end + ' ');
			}
		}
		else if (word.indexOf('r') > -1) {
			if (word.endsWith("er") || word.endsWith("re")) {
				uwu.append(word.substring(0, word.length()-2).replace("r", "w") + word.substring(word.length()-2) + end + ' ');
			}
			else if (word.endsWith("ers") || word.endsWith("res")) {
				uwu.append(word.substring(0, word.length()-3).replace("r", "w") + word.substring(word.length()-3) + end + ' ');
			}
			else {
				uwu.append(word.replace("r", "w") + end + ' ');
			}
		}
		else {
			uwu.append(word + end + ' ');
		}
		
		String str = uwu.toString();
		
		str = str.replaceAll("you're", "ur");
		str = str.replaceAll("youre", "ur");
		str = str.replaceAll("fuck", "fwickk");
		str = str.replaceAll("shit", "poopoo");
		str = str.replaceAll("bitch", "meanie");
		str = str.replaceAll("asshole", "b-butthole");
		str = str.replaceAll("dick", "peenie");
		str = str.replaceAll("penis", "peenie");
		str = str.replaceAll("\\bcum\\b", "cummies");
		str = str.replaceAll("\\bsemen\\b", " cummies ");
		str = str.replaceAll("\\bass\\b", " bussy ");
		str = str.replaceAll("\\bdad\\b", "daddy");
		str = str.replaceAll("\\bfather\\b", "daddy");
		
		if (str.length() > 2 && uwu.substring(0, 1).matches("[a-z]")) {
			if (randomChance(6)) {
				str = str.substring(0, 1) + "-" + str;
			}
		}
		
		return str;
	}
	
	private boolean randomChance(int chance) {
		return (Math.random() * chance < 1);
	}
	
	private String randomItem(String[] list) {
		return list[(int) Math.floor(Math.random() * list.length)];
	}
}

package me.the1withspaghetti.CoolManBot;

import java.io.IOException;
import java.util.HashMap;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.emoji.RichCustomEmoji;

public class ColorEmojiGenerator {
	
	public static JDA jda;
	
	final static String[] colors = {"Red","Orange","DarkOrange","Yellow","PlasticGreen","Forest","ArmyGreen","DarkGreen","Seaweed","Pond","Stone","Raw Pink","Aquamarine","Aqua","Blue","Rain","CornFlower","DarkBlue","Storm","Bluewood","Purple","DarkViolet","DarkOrchid","Lilac","LightPink","Pink","Sand","Blush","Salmon","Blossum","RoseBrown","HotPink","Fuchsia","Rose","De1e7e","Magenta","White","PayneGrey","DarkGrey","Black"};
	final static Integer[] values = {0xf52100,0xff7f27,0xaf471e,0xffff55,0x52d170,0x7db379,0x4b5320,0x036e52,0x0e9979,0xa8c5ff,0xb1bfce,0xd84e68,0x7fffd4,0x00ffff,0x00ffff,0x7f97c6,0x6495ed,0x0855fa,0x20457d,0x293f4e,0x732ea1,0x9400d3,0x9932cc,0x9e7ba6,0xfacade,0xffc0cb,0xf4e4c8,0xff9393,0xfa8072,0xff91a4,0xbc8f8f,0xff69b4,0xff33cc,0xe73478,0xde1e7e,0xd40c5a,0xffffff,0x536878,0x2e3136,0x050505};
	final static String[] emoji = {"<:F52100:1002021541258727445>","<:FF7F27:1002021542869336145>","<:AF471E:1002021543792091278>","<:FFFF55:1002021545222340658>","<:52D170:1002021545918595134>","<:7DB379:1002021547256582315>","<:4B5320:1002021547730542624>","<:036E52:1002021549097885806>","<:0E9979:1002021550016430130>","<:A8C5FF:1002021551098568724>","<:B1BFCE:1002021552059072613>","<:D84E68:1002021553099247626>","<:7FFFD4:1002021554630172823>","<:00FFFF:1002021557318721586>","<:00FFFF:1002021557318721586>","<:7F97C6:1002021558220501032>","<:6495ED:1002021559462019163>","<:0855FA:1002021560405741729>","<:20457D:1002021561483665428>","<:293F4E:1002021562930700318>","<:732EA1:1002021563517906986>","<:9400D3:1002021564746846328>","<:9932CC:1002021565782818929>","<:9E7BA6:1002021567091445761>","<:FACADE:1002021568030982285>","<:FFC0CB:1002021568605601833>","<:F4E4C8:1002021570153291936>","<:FF9393:1002021571117981769>","<:FA8072:1002021572137201684>","<:FF91A4:1002021573559062528>","<:BC8F8F:1002021574657978378>","<:FF69B4:1002021575933034547>","<:FF33CC:1002021576830623805>","<:E73478:1002021577875013762>","<:DE1E7E:1002021579204603974>","<:D40C5A:1002023007771959388>","<:FFFFFF:1002023008988319854>","<:536878:1002023010133344348>","<:2E3136:1002023011567796265>","<:050505:1002023012532494406>"};
	
	public static void main(String[] args) throws IOException, LoginException, InterruptedException {
		
		jda = JDABuilder.createDefault("ODMzMDg1MjcwNTMzNDcyMzU2.YHtNHg.kijpi0jHoIrVMTExA98Jrg5sMZY")
                .build();
		
		jda.getPresence().setActivity(Activity.of(Activity.ActivityType.STREAMING, "Cool Man Game", "https://www.youtube.com/watch?v=tPEE9ZwTmy0"));
		jda.awaitReady();
		
		System.out.println("Creating "+colors.length+" emoji");
		
		/*for (int i = 0; i < colors.length; i++) {
			
			if (jda.getGuildById("833881082926596127").getEmojisByName(String.format("%06X", values[i]), true).size() < 1) {
				BufferedImage img = new BufferedImage(100, 100, BufferedImage.TRANSLUCENT);
				Graphics2D g = (Graphics2D) img.getGraphics();
				
				g.setColor(new Color(values[i]));
				g.fillOval(0, 0, 100, 100);
				
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(img, "PNG", os);
				Icon icon = Icon.from(os.toByteArray(), IconType.PNG);
				jda.getGuildById("833881082926596127").createEmoji(String.format("%06X", values[i]), icon).queue();
				System.out.printf("Created emoji %s %06X\n", colors[i], values[i]);
			} else {
				System.err.printf("Skipping emoji %s %06X\n", colors[i], values[i]);
			}
		}*/
		
		HashMap<String, String> map = new HashMap<>();
		
		for (RichCustomEmoji e : jda.getGuildById("833881082926596127").getEmojis()) {
			if (e.getName().length() == 6) {
				map.put(e.getName(), e.getAsMention());
			}
		}
		
		System.out.print("{");
		for (int i = 0; i < colors.length; i++) {
			System.out.print("\""+map.get(String.format("%06X", values[i]))+"\",");
		}
		System.out.println("}");
		
	}
}

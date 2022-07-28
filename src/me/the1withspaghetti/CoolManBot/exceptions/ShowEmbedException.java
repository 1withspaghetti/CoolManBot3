package me.the1withspaghetti.CoolManBot.exceptions;

import java.awt.Color;
import java.time.Instant;
import java.util.LinkedHashMap;

import me.the1withspaghetti.CoolManBot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

public class ShowEmbedException extends Exception {

	private static final long serialVersionUID = -490262036892416458L;
	
	private User user;
	private LinkedHashMap<String,String> fields = new LinkedHashMap<>();
	
	public ShowEmbedException(String error, String... fields) {
		super(error);
		addFields(fields);
	}
	
	public ShowEmbedException(String error, User user, String... fields) {
		super(error);
		this.user = user;
		addFields(fields);
	}
	
	public ShowEmbedException(Exception e) {
		super(e.getMessage());
		setStackTrace(e.getStackTrace());
	}
	
	public ShowEmbedException addField(String title, String desc) {
		this.fields.put(title, desc);
		return this;
	}
	
	public ShowEmbedException addFields(String... fields) {
		if (fields.length % 2 != 0) {
			throw new IllegalArgumentException("Invalid amount of fields");
		}
		
		for (int i = 0; i < fields.length; i += 2) {
			this.fields.put(fields[i], fields[i+1]);
		}
		return this;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void postEmbed() {
		EmbedBuilder emb = getEmbed();
		Main.jda.getGuildById("833881082926596127").getTextChannelById("1001354822185910332").sendMessageEmbeds(emb.build()).queue();
	}
	
	public EmbedBuilder getEmbed() {
		EmbedBuilder emb = new EmbedBuilder();
		emb.setTitle(getMessage());
		if (user != null) {
			emb.setAuthor(user.getAsTag()+" ("+user.getId()+")", null, user.getEffectiveAvatarUrl());
		}
		
		fields.forEach((name,desc) -> {
			emb.addField(name, desc, true);
		});
		
		emb.setDescription("`"+this.getStackTrace()[0].toString()+"`");
		
		emb.setTimestamp(Instant.now());
		emb.setColor(Color.RED);
		
		return emb;
	}
}

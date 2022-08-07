package me.the1withspaghetti.CoolManBot.util;

public class Checks {
	private static final String r_text = "^[\\w.,!?& ]{1,}$";
	
	public static boolean checkText(String str) {
		return str.matches(r_text);
	}
}

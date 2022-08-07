package me.the1withspaghetti.CoolManBot.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.the1withspaghetti.CoolManBot.util.objects.PollObject;

public class DataBase {
	
	public static Connection con;
	public static Timer timer;
	
	public static void connect(String filePath) {
        try {
        	con = DriverManager.getConnection("jdbc:sqlite:"+filePath);
        	createTables();
        	
        	timer = new Timer();
        	TimerTask task = new TimerTask() {
				@Override
				public void run() {
					purgePoll(System.currentTimeMillis()-1209600000);
				}
        	};
        	timer.schedule(task, 7500, 3600000);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createTables() {
		try {
			Statement stmt = con.createStatement();
			stmt = con.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS POLLS( " +
	                " messageID BIGINT PRIMARY KEY NOT NULL," +
	                " votes TEXT, " + 
	                " timestamp BIGINT NOT NULL, " + 
	                " owner BIGINT NOT NULL," +
	                " anonymous INT NOT NULL," +
	                " mapped INT NOT NULL," +
	                " title TEXT NOT NULL," +
	                " options TEXT NOT NULL" + 
	                " )"; 
	        stmt.executeUpdate(sql);
	        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	public static void addPoll(long messageID, long owner, boolean anonymous, boolean mapped, String title, ArrayList<String> options) {
		try {
		String sql = "INSERT INTO POLLS(\"messageID\",\"votes\",\"timestamp\",\"owner\",\"anonymous\",\"mapped\",\"title\",\"options\") VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setLong(1, messageID);
		pstmt.setString(2, "");
        pstmt.setLong(3, System.currentTimeMillis());
        pstmt.setLong(4, owner);
        pstmt.setInt(5, (anonymous ? 1 : 0));
        pstmt.setInt(6, (mapped ? 1 : 0));
        pstmt.setString(7, title);
        pstmt.setString(8, String.join("|", options));
        pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removePoll(long messageID) {
		try {
			String sql = "DELETE FROM POLLS WHERE \"messageID\" = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, messageID);
	        pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static PollObject getPoll(long messageID) {
		try {
			String sql = "SELECT \"messageID\",\"votes\",\"timestamp\",\"owner\",\"anonymous\",\"mapped\",\"title\",\"options\""
	                + " FROM POLLS WHERE messageID = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, messageID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next() == false) return null;
			return new PollObject(rs.getLong("messageID"), rs.getString("votes"), rs.getLong("timestamp"), rs.getLong("owner"), rs.getInt("anonymous"), rs.getInt("mapped"), rs.getString("title"), rs.getString("options"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void updatePoll(PollObject obj) {
		try {
			String sql = "UPDATE POLLS SET \"votes\" = ? "
	                + "WHERE \"messageID\" = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, obj.getVotesSerialized());
			pstmt.setLong(2, obj.messageId);
	        pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void purgePoll(long minTime) {
		try {
			String sql = "DELETE FROM POLLS WHERE \"timestamp\" < ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, minTime);
	        pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

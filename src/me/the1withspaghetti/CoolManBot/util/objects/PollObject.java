package me.the1withspaghetti.CoolManBot.util.objects;

import java.util.HashMap;

public class PollObject {
	
	public long messageId;
	public HashMap<Long,Integer> votes;
	public long timestamp;
	public long owner;
	public boolean anonymous;
	public boolean mapped;
	public String title;
	public String[] options;
	
	public PollObject(long messageId, String serializedVotes, long timestamp, long owner, int anonymous, int mapped, String title, String options) {
		this.messageId = messageId;
		this.timestamp = timestamp;
		this.owner = owner;
		this.title = title;
		
		this.votes = new HashMap<Long,Integer>();
		String[] voteStr = serializedVotes.split(",", 0);
		for (String vote : voteStr) {
			if (!vote.isEmpty()) {
				String[] data = vote.split(":");
				this.votes.put(Long.valueOf(data[0]), Integer.valueOf(data[1]));
			}
		}
		this.anonymous = (anonymous == 0 ? false : true);
		this.mapped = (mapped == 0 ? false : true);
		this.options = options.split("\\|");
	}
	
	/*
	 * [userId]:[vote],[userId]:[vote], ...
	 */
	public String getVotesSerialized() {
		StringBuilder str = new StringBuilder();
		votes.forEach((id, vote) -> {
			str.append(id+":"+vote+",");
		});
		return str.toString();
	}

}

package me.neoblade298.neoresearch;

import java.util.HashMap;

public class ResearchItem {
	private String name;
	private HashMap<String, Integer> goals;
	private Attributes attrs;
	private int exp;
	private String permission;
	
	public ResearchItem(String name) {
		this.goals = new HashMap<String, Integer>();
		this.attrs = new Attributes();
		this.exp = 0;
		this.name = name;
	}

	public HashMap<String, Integer> getGoals() {
		return goals;
	}

	public void setGoals(HashMap<String, Integer> goals) {
		this.goals = goals;
	}

	public Attributes getAttrs() {
		return attrs;
	}

	public void setAttrs(Attributes attrs) {
		this.attrs = attrs;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}

package com.emailtosms.entities;

import java.util.ArrayList;

public class BulkSms {

	// Attributs
	private String Username;
	private String Token;
	private String Title;
	private ArrayList<Message> Mssg;

	// Constructeurs
	public BulkSms() {
		super();
	}

	public BulkSms(String username, String token, String title, ArrayList<Message> mssg) {
		super();
		Username = username;
		Token = token;
		Title = title;
		Mssg = mssg;
	}

	// Getters and Setters
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public ArrayList<Message> getMssg() {
		return Mssg;
	}

	public void setMssg(ArrayList<Message> mssg) {
		Mssg = mssg;
	}
}

package com.emailtosms.sms;

import java.util.ArrayList;

public class FullSms {

	// Attributs
	private String Username;
	private String Sender;
	private String Sms;
	private String Token;
	private String Flash;
	private ArrayList<Destinataire> Contact;

	// Constructeurs
	public FullSms() {
		super();
	}

	public FullSms(String username, String sender, String sms, String token, String flash,
			ArrayList<Destinataire> contact) {
		super();
		Username = username;
		Sender = sender;
		Sms = sms;
		Token = token;
		Flash = flash;
		Contact = contact;
	}

	// Getters and Setters
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getSms() {
		return Sms;
	}

	public void setSms(String sms) {
		Sms = sms;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getFlash() {
		return Flash;
	}

	public void setFlash(String flash) {
		Flash = flash;
	}

	public ArrayList<Destinataire> getContact() {
		return Contact;
	}

	public void setContact(ArrayList<Destinataire> contact) {
		Contact = contact;
	}

}

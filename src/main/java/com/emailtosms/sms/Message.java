package com.emailtosms.sms;

public class Message {

	// Attributs
	private String Flash;
	private String Sender;
	private String Dest;
	private String Sms;

	// Constructeurs
	public Message() {
		super();
	}

	public Message(String flash, String sender, String dest, String sms) {
		super();
		Flash = flash;
		Sender = sender;
		Dest = dest;
		Sms = sms;
	}

	public String getFlash() {
		return Flash;
	}

	public void setFlash(String flash) {
		Flash = flash;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getDest() {
		return Dest;
	}

	public void setDest(String dest) {
		Dest = dest;
	}

	public String getSms() {
		return Sms;
	}

	public void setSms(String sms) {
		Sms = sms;
	}

}

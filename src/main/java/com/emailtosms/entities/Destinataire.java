package com.emailtosms.entities;

public class Destinataire {

	// Attributs
	private String Dest;

	// Constructeurs
	public Destinataire() {
		super();
	}

	public Destinataire(String dest) {
		super();
		Dest = dest;
	}

	// Getters and Setters
	public String getDest() {
		return Dest;
	}

	public void setDest(String dest) {
		Dest = dest;
	}

}

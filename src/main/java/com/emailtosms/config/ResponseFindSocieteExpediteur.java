package com.emailtosms.config;

public class ResponseFindSocieteExpediteur {

	private String societe;
	private boolean response;

	public ResponseFindSocieteExpediteur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseFindSocieteExpediteur(String societe, boolean response) {
		super();
		this.societe = societe;
		this.response = response;
	}

	public String getSociete() {
		return societe;
	}

	public void setSociete(String societe) {
		this.societe = societe;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

}

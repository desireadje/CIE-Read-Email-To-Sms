package com.emailtosms.entities;

/* Cette classe permet de serialiser le retour du formatage du numero */
public class ResponseFormatNumber {

	private String numero;
	private boolean response;

	public ResponseFormatNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseFormatNumber(String numero, boolean response) {
		super();
		this.numero = numero;
		this.response = response;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

}

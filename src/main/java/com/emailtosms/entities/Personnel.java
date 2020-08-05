package com.emailtosms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "personnel")
public class Personnel {

	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenoms;
	private String numero;

	private String email;
	private String email_password;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateInsertion;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;

	private int etat = 1;

	// constructeur sans params
	public Personnel() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur avec params
	public Personnel(String nom, String prenoms, String numero, String email, String email_password, int etat) {
		super();
		this.nom = nom;
		this.prenoms = prenoms;
		this.numero = numero;
		this.email = email;
		this.email_password = email_password;
		this.etat = etat;
	}
	
	// getters & setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail_password() {
		return email_password;
	}

	public void setEmail_password(String email_password) {
		this.email_password = email_password;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

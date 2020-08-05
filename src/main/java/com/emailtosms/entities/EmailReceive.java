package com.emailtosms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "email_recu")
public class EmailReceive {

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idEmail;

	private String code;
	private String expediteur;
	private String desdinataire;
	private String copie;
	private String subject;
	private String message;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date date_send;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateInsertion;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;

	private int etat = 0;

	// clé etrangère
	@ManyToOne
	@JoinColumn(name = "servername", referencedColumnName = "servername")
	ParametreSmtp parametreSmtp;

	// constructeur sans params
	public EmailReceive() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur avec params
	public EmailReceive(String code, String expediteur, String desdinataire, String copie, String subject,
			String message, Date date_send, Date dateInsertion, Date dateModification, int etat) {
		super();
		this.code = code;
		this.expediteur = expediteur;
		this.desdinataire = desdinataire;
		this.copie = copie;
		this.subject = subject;
		this.message = message;
		this.date_send = date_send;
		this.dateInsertion = dateInsertion;
		this.dateModification = dateModification;
		this.etat = etat;
	}

	// getters & setters
	public Long getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(Long idEmail) {
		this.idEmail = idEmail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExpediteur() {
		return expediteur;
	}

	public void setExpediteur(String expediteur) {
		this.expediteur = expediteur;
	}

	public String getDesdinataire() {
		return desdinataire;
	}

	public void setDesdinataire(String desdinataire) {
		this.desdinataire = desdinataire;
	}

	public String getCopie() {
		return copie;
	}

	public void setCopie(String copie) {
		this.copie = copie;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate_send() {
		return date_send;
	}

	public void setDate_send(Date date_send) {
		this.date_send = date_send;
	}

	public Date getDateInsertion() {
		return dateInsertion;
	}

	public void setDateInsertion(Date dateInsertion) {
		this.dateInsertion = dateInsertion;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public ParametreSmtp getParametreSmtp() {
		return parametreSmtp;
	}

	public void setParametreSmtp(ParametreSmtp parametreSmtp) {
		this.parametreSmtp = parametreSmtp;
	}

}

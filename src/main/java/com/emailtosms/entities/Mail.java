package com.emailtosms.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "emailToSmsMail")
public class Mail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String reference;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_send;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_recup;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date date_insertion;

	@Column(columnDefinition = "TEXT")
	private String sujet;
	@Column(columnDefinition = "TEXT")
	private String fromm;
	@Column(columnDefinition = "TEXT")
	private String too;
	@Column(columnDefinition = "TEXT")

	private String nomPersonnel;
	private String numero;

	@Column(columnDefinition = "TEXT")
	private String text;

	private int paraid;

	private int etat = 0;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateCreation;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;

	private String nomApplicationEmailToSms;
	private String username;
	private String token;
	private String sender;

	// constructeur sans params
	public Mail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Mail(String reference, Date date_send, Date date_recup, Date date_insertion, String sujet, String fromm,
			String too, String nomPersonnel, String numero, String text, int paraid, int etat, Date dateCreation,
			Date dateModification, String nomApplicationEmailToSms, String username, String token, String sender) {
		super();
		this.reference = reference;
		this.date_send = date_send;
		this.date_recup = date_recup;
		this.date_insertion = date_insertion;
		this.sujet = sujet;
		this.fromm = fromm;
		this.too = too;
		this.nomPersonnel = nomPersonnel;
		this.numero = numero;
		this.text = text;
		this.paraid = paraid;
		this.etat = etat;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
		this.nomApplicationEmailToSms = nomApplicationEmailToSms;
		this.username = username;
		this.token = token;
		this.sender = sender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getDate_send() {
		return date_send;
	}

	public void setDate_send(Date date_send) {
		this.date_send = date_send;
	}

	public Date getDate_recup() {
		return date_recup;
	}

	public void setDate_recup(Date date_recup) {
		this.date_recup = date_recup;
	}

	public Date getDate_insertion() {
		return date_insertion;
	}

	public void setDate_insertion(Date date_insertion) {
		this.date_insertion = date_insertion;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getFromm() {
		return fromm;
	}

	public void setFromm(String fromm) {
		this.fromm = fromm;
	}

	public String getToo() {
		return too;
	}

	public void setToo(String too) {
		this.too = too;
	}

	public String getNomPersonnel() {
		return nomPersonnel;
	}

	public void setNomPersonnel(String nomPersonnel) {
		this.nomPersonnel = nomPersonnel;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getParaid() {
		return paraid;
	}

	public void setParaid(int paraid) {
		this.paraid = paraid;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateModification() {
		return dateModification;
	}

	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}

	public String getNomApplicationEmailToSms() {
		return nomApplicationEmailToSms;
	}

	public void setNomApplicationEmailToSms(String nomApplicationEmailToSms) {
		this.nomApplicationEmailToSms = nomApplicationEmailToSms;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

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
@Table(name = "mail")
public class Mail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String code;
	private String idmail;
	private String identifiant;

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

	private String nom;
	private String numero;
	private String service;

	private String cc;
	@Column(columnDefinition = "TEXT")
	private String bcc;

	@Column(columnDefinition = "TEXT")
	private String text;
	@Column(columnDefinition = "TEXT")
	private String html;

	private int etat = 0;
	private int paraid;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateCreation;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;

	// constructeur sans params
	public Mail() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur avec params
	public Mail(String code, String idmail, String identifiant, Date date_send, Date date_recup, Date date_insertion,
			String sujet, String fromm, String too, String nom, String numero, String service,
			String cc, String bcc, String text, String html, int etat, int paraid, Date dateCreation,
			Date dateModification) {
		super();
		this.code = code;
		this.idmail = idmail;
		this.identifiant = identifiant;
		this.date_send = date_send;
		this.date_recup = date_recup;
		this.date_insertion = date_insertion;
		this.sujet = sujet;
		this.fromm = fromm;
		this.too = too;
		this.nom = nom;
		this.numero = numero;
		this.service = service;
		this.cc = cc;
		this.bcc = bcc;
		this.text = text;
		this.html = html;
		this.etat = etat;
		this.paraid = paraid;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdmail() {
		return idmail;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setIdmail(String idmail) {
		this.idmail = idmail;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int getParaid() {
		return paraid;
	}

	public void setParaid(int paraid) {
		this.paraid = paraid;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

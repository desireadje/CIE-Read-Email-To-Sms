package com.emailtosms.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "parametreapi")
public class ParametreApi implements Serializable {


	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String Username;
	private String Sender;
	private String Token;

	private String Url;
	private String Method_one;
	private String Method_full;
	private String Method_bulk;

	private String Flash;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date DateModification;

	private String Title;

	private int Etat = 1;

	// constructeur sans params
	public ParametreApi() {
		super();
	}

	// contructeur avec params
	public ParametreApi(String username, String sender, String token, String url, String method_one, String method_full,
			String method_bulk, String flash, Date dateModification, String title, int etat) {
		super();
		Username = username;
		Sender = sender;
		Token = token;
		Url = url;
		Method_one = method_one;
		Method_full = method_full;
		Method_bulk = method_bulk;
		Flash = flash;
		DateModification = dateModification;
		Title = title;
		Etat = etat;
	}

	// getters a setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getMethod_one() {
		return Method_one;
	}

	public void setMethod_one(String method_one) {
		Method_one = method_one;
	}

	public String getMethod_full() {
		return Method_full;
	}

	public void setMethod_full(String method_full) {
		Method_full = method_full;
	}

	public String getMethod_bulk() {
		return Method_bulk;
	}

	public void setMethod_bulk(String method_bulk) {
		Method_bulk = method_bulk;
	}

	public String getFlash() {
		return Flash;
	}

	public void setFlash(String flash) {
		Flash = flash;
	}

	public Date getDateModification() {
		return DateModification;
	}

	public void setDateModification(Date dateModification) {
		DateModification = dateModification;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public int getEtat() {
		return Etat;
	}

	public void setEtat(int etat) {
		Etat = etat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

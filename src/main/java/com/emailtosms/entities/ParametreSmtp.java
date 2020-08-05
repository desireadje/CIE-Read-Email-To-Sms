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
@Table(name = "parametre_smtp")
public class ParametreSmtp implements Serializable {

	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String servername;
	private String protocol;
	private int port;
	private String host;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateInsertion;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;

	private int etat = 1;

	// constructeur sans params
	public ParametreSmtp() {
		super();
		// TODO Auto-generated constructor stub
	}

	// constructeur avec params
	public ParametreSmtp(String nom_server, String protocol, int port, String host, Date dateInsertion,
			Date dateModification, int etat) {
		super();
		this.servername = nom_server;
		this.protocol = protocol;
		this.port = port;
		this.host = host;
		this.dateInsertion = dateInsertion;
		this.dateModification = dateModification;
		this.etat = etat;
	}

	// getters & setters
	public Long getId_server() {
		return id;
	}

	public void setId_server(Long id) {
		this.id = id;
	}

	public String getNom_server() {
		return servername;
	}

	public void setNom_server(String servername) {
		this.servername = servername;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

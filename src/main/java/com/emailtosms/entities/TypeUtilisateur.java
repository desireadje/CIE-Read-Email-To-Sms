package com.emailtosms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "typeUtilisateur")
public class TypeUtilisateur {

	private static final long serialVersionUID = 1L;

	// Attributs
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idTypeUtilisateur;
	private String nom;
	private int etat = 1;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateCreation;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;

	// Constructeurs
	public TypeUtilisateur() {
		super();
	}

	public TypeUtilisateur(String nom, int etat, Date dateCreation, Date dateModification) {
		super();
		this.nom = nom;
		this.etat = etat;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}

	// Getters and Setters
	public Long getIdTypeUtilisateur() {
		return idTypeUtilisateur;
	}

	public void setIdTypeUtilisateur(Long idTypeUtilisateur) {
		this.idTypeUtilisateur = idTypeUtilisateur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

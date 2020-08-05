package com.emailtosms.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

	// Attributs
	@Id
	private String username;
	private String password;
	private String civilite;
	private String nom;
	private String prenoms;
	private String mobile;
	private String telephone;

	@NotEmpty()
	@Email
	private String email;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateCreation;
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date dateModification;

	private int etat = 1;

	@ManyToOne
	@JoinColumn(name = "creerpar", referencedColumnName = "username")
	private Utilisateur creerPar;

}

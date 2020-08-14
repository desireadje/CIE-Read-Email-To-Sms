package com.emailtosms.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable {

	// Attributs
	@Id
	private String role;
	private String description;
	private int etat = 1;

	// Constructeurs
	public Role() {
		super();
	}

	public Role(String role, String description, int etat) {
		super();
		this.role = role;
		this.description = description;
		this.etat = etat;
	}

	// Getters and Setters
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

}

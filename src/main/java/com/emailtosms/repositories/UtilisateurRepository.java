package com.emailtosms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emailtosms.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String>{

}

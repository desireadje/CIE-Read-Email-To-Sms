package com.emailtosms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emailtosms.entities.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {

	// Cette requête retourne un EmailReceive par son code
	@Query("SELECT m FROM Mail m WHERE m.identifiant= ?1")
	Mail findMailByIdentitifiant(String identifiant);

}

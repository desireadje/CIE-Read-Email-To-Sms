package com.emailtosms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emailtosms.entities.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {

	// Cette requête retourne un EmailReceive par son code
	@Query("SELECT m FROM Mail m WHERE m.code= ?1")
	Mail findMailByIdentitifiant(String code);

	// Cette requête retourne une liste CallMissed
	@Query("SELECT c FROM Mail c ORDER BY c.id DESC")
	List<Mail> findAllMailOrderByDesc();

	// Cette requête retourne une liste CallMissed
	@Query("SELECT c FROM Mail c WHERE c.etat=0")
	List<Mail> findAllMailForSendSms();

}

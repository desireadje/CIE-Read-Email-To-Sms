package com.emailtosms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emailtosms.entities.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {

	// Cette requête retourne un EmailReceive par son code
	@Query("SELECT m FROM Mail m WHERE m.reference= ?1")
	Mail findMailByIdentitifiant(String reference);

	// Cette requête retourne une liste CallMissed
	@Query("SELECT c FROM Mail c ORDER BY c.id DESC")
	List<Mail> findAllMailOrderByDesc();

	// Cette requête retourne une liste CallMissed
	// @Query("SELECT c FROM Mail c WHERE c.etat=0")
	@Query(value = "SELECT * FROM email_to_sms_mail WHERE etat = 0 AND DATE(date_creation) = CURRENT_DATE ORDER BY id DESC", nativeQuery = true)
	List<Mail> findAllMailOftoDayForSendSms();

	/*
	 * Requètes natives exécutées sur d'autres tables autres que la table
	 * email_to_sms_mail
	 */
	@Query(value = "SELECT u.nom, u.username, u.token, g.sender\r\n"
			+ "FROM societe s, pole p, direction_centrale dc, departement d, groupeenvoi g, utilisateur u \r\n"
			+ "WHERE s.id_societe = p.id_societe\r\n" + "AND p.id_pole = dc.id_pole\r\n"
			+ "AND dc.id_direction_centrale = d.id_direction_centrale\r\n"
			+ "AND d.id_departement = g.id_departement\r\n" + "AND g.id_groupe_envoi = u.id_groupe_envoi\r\n"
			+ "AND s.raison_sociale = ?1 AND u.role = 'EmailToSms' AND u.etat = 1 ORDER BY u.username DESC LIMIT 1", nativeQuery = true)
	List<Object[]> findIdGroupeEnvoi(String societe);

	/* Cette requête retourne un ParametreApi */
	@Query(value = "SELECT url, flash FROM parametre_api", nativeQuery = true)
	List<Object[]> findOneParametreApi();

}

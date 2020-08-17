package com.emailtosms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emailtosms.entities.ModeleSms;

public interface ModeleSmsRepository extends JpaRepository<ModeleSms, Long> {

	// Cette requête retourne un EmailReceive par son code
	@Query("SELECT m FROM ModeleSms m WHERE m.etat>=0")
	List<ModeleSms> findAllModeleSms();

	// Cette requête retourne un EmailReceive par son code
	@Query("SELECT m FROM ModeleSms m WHERE m.etat=1")
	ModeleSms findModeleSms();

}

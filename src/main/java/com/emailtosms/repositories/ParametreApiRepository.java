package com.emailtosms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emailtosms.entities.ParametreApi;

public interface ParametreApiRepository extends JpaRepository<ParametreApi, Long> {
	
	// Cette requête retourne la liste des mail a notifier
	@Query("SELECT a FROM ParametreApi a WHERE a.id=1")
	ParametreApi findParamApi();

	// Cette requête retourne un ParamApi par son codes
	@Query("SELECT c FROM ParametreApi c WHERE c.id=?1 AND c.Etat=1")
	ParametreApi findParamApiById(Long id);

}

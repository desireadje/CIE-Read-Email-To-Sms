package com.emailtosms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emailtosms.entities.ParametreMySql;

public interface MySqlRepository extends JpaRepository<ParametreMySql, Long> {

	// Cette requête retourne une liste CallMissed
	@Query("SELECT c FROM ParametreMySql c WHERE c.id=1")
	ParametreMySql findMysqlParam();

}

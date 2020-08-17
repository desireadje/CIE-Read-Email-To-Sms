package com.emailtosms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emailtosms.entities.ParametreMySql;

public interface MySqlRepository extends JpaRepository<ParametreMySql, Long> {

}

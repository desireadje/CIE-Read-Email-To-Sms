package com.emailtosms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emailtosms.entities.Sms;

public interface SmsRepository extends JpaRepository<Sms, Long> {

}

package com.emailtosms.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/parametre-mysql")
public class ParametreMySqlController {

	// Logger
	private static final Logger log = LoggerFactory.getLogger(ParametreMySqlController.class);

	/**
	 * Retourne la liste des utilisateurs
	 */
	@GetMapping()
	public String page(Model model, HttpSession session) {
		// Je retourne ma vue
		return "carnetadresses/contacts/index";

	}

}

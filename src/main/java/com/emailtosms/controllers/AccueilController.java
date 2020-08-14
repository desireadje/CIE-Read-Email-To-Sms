package com.emailtosms.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/")
public class AccueilController {

	@GetMapping()
	public String page(Model model, HttpSession session) {

		// Je passe les utilisateurs dans la vue
		model.addAttribute("Locations", null);
		return "accueil/index";
	}

}
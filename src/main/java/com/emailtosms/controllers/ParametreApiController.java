package com.emailtosms.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emailtosms.repositories.ParametreApiRepository;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/parametre-api")
public class ParametreApiController {
	
	@Autowired
	private ParametreApiRepository apiRepository;
	
	@GetMapping()
	public String page(Model model, HttpSession session) {
		// je recherche les tickets par ordre decroissant
		model.addAttribute("ListApi", apiRepository.findParamApi());
		return "api/index";
	}

}

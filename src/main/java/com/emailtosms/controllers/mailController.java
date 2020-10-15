package com.emailtosms.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emailtosms.repositories.MailRepository;

@Controller
@RequestMapping(value = "/")
public class MailController {

	@Autowired
	private MailRepository mailRepos;

	@GetMapping()
	public String page(Model model, HttpSession session) {
		// je recherche les tickets par ordre decroissant
		model.addAttribute("Mails", mailRepos.findAllMailOrderByDesc());
		return "mail/index";
	}

}
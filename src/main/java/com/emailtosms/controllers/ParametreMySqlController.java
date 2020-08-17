package com.emailtosms.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emailtosms.entities.ParametreMySql;
import com.emailtosms.repositories.MySqlRepository;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/parametre-mysql")
public class ParametreMySqlController {

	@Autowired
	private MySqlRepository mySqlRepos;

	@GetMapping()
	public String page(Model model, HttpSession session) {
		// Je retourne ma vue
		model.addAttribute("Mysql", mySqlRepos.findAll());
		return "mysql/index";

	}

	// Fonction de recherche d'un element
	@PostMapping(value = "/findOne")
	public ParametreMySql findOne(@RequestBody Long id) {
		ParametreMySql m = null;
		try {
			m = mySqlRepos.findById(id).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// Fonction qui affiche la page de modification
	@GetMapping(value = "/formupdate")
	public String formupdate(Long id, Model model) {
		ParametreMySql m = null;
		try {
			m = findOne(id);
			if (m != null) {
				model.addAttribute("Mysql", m);
			} else {
				return "redirect:/parametre-mysql";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "mysql/update";
	}

}

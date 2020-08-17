package com.emailtosms.controllers;

import java.util.Date;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emailtosms.entities.ParametreMySql;
import com.emailtosms.repositories.MySqlRepository;

@Controller
@RequestMapping(value = "/parametre-mysql")
public class ParametreMySqlController {

	@Autowired
	private MySqlRepository mySqlRepos;

	@GetMapping()
	public String page(Model model, HttpSession session) {
		ParametreMySql mysql = mySqlRepos.findMysqlParam();
		
		// Je retourne ma vue
		model.addAttribute("Mysql", mysql);
		
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
	
	/*
	 * Fonction qui modification
	 */
	@PostMapping(value = "/update")
	public String update(ParametreMySql a, BindingResult bindingResult) {
		ParametreMySql msql = null;
		try {
			if (bindingResult.hasErrors()) {
				return "parametre-mysql/update";
			}

			msql = findOne(a.getId());
			if (msql != null) {				
				a.setDateModification(new Date());
				a = mySqlRepos.save(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/parametre-mysql";
	}

}

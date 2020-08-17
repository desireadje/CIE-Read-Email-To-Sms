package com.emailtosms.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emailtosms.entities.ModeleSms;
import com.emailtosms.repositories.ModeleSmsRepository;

@Controller
@CrossOrigin("*")
@RequestMapping(value = "/modele-sms")
public class modeleSmsController {

	@Autowired
	private ModeleSmsRepository modeleSmsRepos;

	@GetMapping()
	public String page(Model model, HttpSession session) {
		// je recherche les tickets par ordre decroissant
		model.addAttribute("Modeles", modeleSmsRepos.findAllModeleSms());
		return "modelesms/index";
	}

	// Cette fonction retourne la page de création
	@GetMapping(value = "/formcreate")
	public String formSociete(Model model) {
		model.addAttribute("modelesms", new ModeleSms());
		return "modelesms/create";
	}

	// Fonction de création
	@PostMapping(value = "/create")
	public String create(ModeleSms a, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				return "modelesms/create";
			}

			a.setEtat(1);
			a.setDateInsertion(new Date());
			a.setDateModification(new Date());

			a = modeleSmsRepos.save(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/modele-sms";
	}

	// Fonction de recherche d'un element
	@PostMapping(value = "/findOne")
	public ModeleSms findOne(@RequestBody Long id) {
		ModeleSms m = null;
		try {
			m = modeleSmsRepos.findById(id).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	// Fonction qui affiche la page de modification
	@GetMapping(value = "/formupdate")
	public String formupdate(Long id, Model model) {
		try {
			ModeleSms m = findOne(id);
			if (m != null) {
				model.addAttribute("modelesms", m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modelesms/update";
	}

	// Fonction de mise à jour
	@PostMapping(value = "/update")
	public String update(ModeleSms a, BindingResult bindingResult) {
		ModeleSms m = null;
		try {
			if (bindingResult.hasErrors()) {
				return "modelesms/update";
			}

			m = findOne(a.getId());
			if (m != null) {
				a.setDateModification(new Date());
				m = modeleSmsRepos.save(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/modele-sms";
	}

	// Fonction de suppression
	@GetMapping(value = "/delete")
	public String delete(Long id) {
		ModeleSms m = null;
		try {
			m = findOne(id);
			if (m != null) {
				m.setEtat(-1);
				m.setDateModification(new Date());
				m = modeleSmsRepos.save(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/modele-sms";
	}

	// Fonction de active & desactive
	@GetMapping(value = "/active")
	public String active(Long id) {
		ModeleSms m = null;
		try {
			m = findOne(id);
			if (m != null) {
				if (m.getEtat() == 0) {
					m.setEtat(1);
				} else {
					m.setEtat(0);
				}
				m.setDateModification(new Date());

				modeleSmsRepos.save(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/modele-sms";
	}

}

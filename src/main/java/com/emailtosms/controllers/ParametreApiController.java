package com.emailtosms.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emailtosms.entities.ParametreApi;
import com.emailtosms.repositories.ParametreApiRepository;

@Controller
@RequestMapping(value = "/parametre-api")
public class ParametreApiController {

	@Autowired
	private ParametreApiRepository apiRepository;

	@GetMapping()
	public String page(Model model, HttpSession session) {
		ParametreApi p = apiRepository.findParamApi();
		// je recherche les tickets par ordre decroissant
		model.addAttribute("Apis", p);
		return "api/index";
	}

	/*
	 * Fonction qui affiche par id
	 */
	public ParametreApi findOne(Long id) {
		ParametreApi api = null;
		try {
			api = apiRepository.findParamApiById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return api;
	}

	/*
	 * Fonction qui affiche la page de modification
	 */
	@GetMapping(value = "/formupdate")
	public String formupdate(Long id, Model model) {
		try {
			ParametreApi api = findOne(id);
			model.addAttribute("API", api);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "api/update";
	}

	/*
	 * Fonction qui modification
	 */
	@PostMapping(value = "/update")
	public String update(ParametreApi a, BindingResult bindingResult) {
		ParametreApi api = null;
		try {
			if (bindingResult.hasErrors()) {
				return "parametre/update";
			}

			api = findOne(a.getId());
			if (api != null) {

				a.setMethod_one(api.getMethod_one());
				a.setMethod_full(api.getMethod_full());
				a.setMethod_bulk(api.getMethod_bulk());

				a.setDateModification(new Date());
				a = apiRepository.save(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/parametre-api";
	}

}

package com.emailtosms.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.emailtosms.methods.Utils;
import com.emailtosms.sms.BulkSms;
import com.google.gson.Gson;

public class BulkSmsHttp {

	public static boolean main(BulkSms bulkSms, String url) {

		boolean reponse = false;

		// 1. Je crée une instance de Gson().
		Gson gson = new Gson();

		// 2. Je crée une instance de HttpHeaders().
		HttpHeaders headers = new HttpHeaders();

		// 3. Je définis le header (en-tête) pour retouner du JSON.
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 4. Je crée une instance de RestTemplate().
		RestTemplate restTemplate = new RestTemplate();

		// 5. J'encode l'objet BulkSms en JSON.
		HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(bulkSms), headers);

		// 6. J'envoi le sms au destinataire finaux.
		ResponseEntity<String> response = restTemplate.postForEntity(url + "/addBulkSms", entity, String.class);
		System.err.println(Utils.dateNow() + " CodeReponse : " + response.getStatusCodeValue());

		// 7. Je vérifie le code de la réponse retrournée.
		if (response.getStatusCodeValue() == 200) {
			reponse = true;
		} else {
			System.err.println(Utils.dateNow() + " Erreur lors de l'envoi des SMS, veuillez réessayer à nouveau.");
		}

		return reponse;
	}
}

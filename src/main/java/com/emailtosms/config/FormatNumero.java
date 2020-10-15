package com.emailtosms.config;

import com.emailtosms.entities.ResponseFormatNumber;

public class FormatNumero {

	public static ResponseFormatNumber number_F(String expediteur) {

		System.out.println("Est numérique : " + isNumeric(expediteur));
		// Mes declarations
		String numero = null;
		String indicatif = null;
		int taille = 0;

		// Je recupère la taille du numéro expéditeur
		taille = expediteur.length();

		// Je creer une instance ResponseFormatNumber()
		ResponseFormatNumber rfn = new ResponseFormatNumber();
		rfn.setResponse(false);

		if (isNumeric(expediteur) == true) {
			// Si le numéro existe alors
			if (taille > 0) {

				rfn.setResponse(true);

				// Je recupère le l'indicatif pays +225
				indicatif = expediteur.substring(0, 4);

				// Si la taille du numéro égale à 12 caractères alors
				if (taille == 11) {
					// Si l'indicatif est +225 alors
					if (indicatif.equalsIgnoreCase("225")) {
						numero = expediteur;
					}

				} else if (taille == 12) {
					// Si l'indicatif est +225 alors
					if (indicatif.equalsIgnoreCase("+225")) {
						numero = expediteur.substring(1, 12);
					}

				} else if (taille == 13) {
					// Si l'indicatif est +225 alors
					if (indicatif.equalsIgnoreCase("225")) {
						numero = expediteur;
					}

				} else if (taille == 14) {
					// Si l'indicatif est +225 alors
					if (indicatif.equalsIgnoreCase("+225")) {
						numero = expediteur.substring(1, 14);
					}

				} else if (taille == 8 && !indicatif.equalsIgnoreCase("225")) {
					numero = "225" + expediteur;
				} else {
					rfn.setResponse(false);
					numero = expediteur;
				}
			}
		}

		rfn.setNumero(numero);

		return rfn;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}

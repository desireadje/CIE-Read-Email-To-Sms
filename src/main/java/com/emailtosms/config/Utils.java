package com.emailtosms.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;

import com.emailtosms.entities.ResponseFormatNumber;

public class Utils {

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

	// Augmenter une date jour par jour
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

	// Augmenter une date jour par jour
	public static Date addMinutes(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute); // minus number would decrement the minute
		return cal.getTime();
	}

	public Date addMonth(Date date, int minute) {
		Date now = date;
		Calendar myCal = Calendar.getInstance();
		myCal.setTime(now);
		myCal.add(Calendar.MONTH, +minute);
		now = myCal.getTime();
		System.out.println("===========> " + now);
		return now;
	}

	public static String dateNow() {

		String dateNow = null;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		dateNow = sdf.format(date);

		return dateNow;
	}

	public static String dateFormat(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);
	}

	public static Date stringToDate(String str) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = null;
		if (str != null) {
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	public static void getLocalDateTime() {

		// Java 8
		System.out.println(LocalDateTime.now().getYear()); // 2015
		System.out.println(LocalDateTime.now().getMonth()); // SEPTEMBER
		System.out.println(LocalDateTime.now().getDayOfMonth()); // 29
		System.out.println(LocalDateTime.now().getHour()); // 7
		System.out.println(LocalDateTime.now().getMinute()); // 36
		System.out.println(LocalDateTime.now().getSecond()); // 51
		System.out.println(LocalDateTime.now().get(ChronoField.MILLI_OF_SECOND)); // 100

	}

	public static String findSocieteExpediteur(String expediteur) {

		String societe = null;
		String delimiter = null;
		String[] parts = null;

		if (expediteur.contains("@") && expediteur.contains(".")) {

			// Je split le mail de l'expediteur par @
			delimiter = "@";
			parts = expediteur.split(delimiter);

			if (parts != null) {
				String part1 = parts[1].trim(); // SOCIETE.COM

				delimiter = "\\.";
				parts = part1.split(delimiter);

				if (parts != null) {
					societe = parts[0].trim().toUpperCase(); // Uppercase
				}
			}
		}

		return societe;
	}

}

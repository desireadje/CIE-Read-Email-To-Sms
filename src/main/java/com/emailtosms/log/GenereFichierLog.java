package com.emailtosms.log;

import java.io.File;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;

import com.emailtosms.methods.Utils;

public class GenereFichierLog {
	
	@Value("${logging.path}")
	private static String loggingpath;
	
	@Value("${userBucket.path}")
	private static String userBucketPath;

	public static void log() throws ParseException {
		String dateActuel = Utils.dateFormat("yyyy-MM-dd");

		String dossier = userBucketPath + dateActuel;

		// Je creer le dossier du jour
		if (!new File(dossier).exists()) {
			// Créer le dossier avec tous ses parents
			new File(dossier).mkdirs();
		}
	}

}

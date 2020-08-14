package com.emailtosms.methods;

import java.io.IOException;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMultipart;

import com.emailtosms.entities.Mail;

public class Messaging {

	/**
	 * Cette méthode retourne l'enveloppe du message recuperer qui contient from,
	 * subject, toList, ccList, vérifie le type de contenu en fonction duquel, elle
	 * traite et récupère le contenu du message
	 * 
	 * @param Message
	 * @param Email
	 * 
	 */
	@SuppressWarnings("null")
	public static Mail mailEnvelope(Message msg, String email) throws Exception {

		Mail mailEnvelope = null;
		Address[] fromAddress = null;
		String from = null;

		String subject = "";
		String toList = "";
		String ccList = "";

		Date sentDate = null;
		Date receivedDate = null;
		String contentType = null;

		String messageContent = "";
		int contentStatus = 0;
		
		// toList = parseAddresses(msg.getRecipients(RecipientType.TO));
		toList = email;
		
		if (msg.getRecipients(RecipientType.CC) != null) {
			ccList = parseAddresses(msg.getRecipients(RecipientType.CC));		
		}		

		fromAddress = msg.getFrom();
		from = fromAddress[0].toString();
		subject = msg.getSubject();

		sentDate = msg.getSentDate();
		receivedDate = msg.getReceivedDate();

		contentType = msg.getContentType();

		System.err.println(Utils.dateNow() + contentType);

		if (contentType.startsWith("multipart/") || contentType.contains("text/plain")
				|| contentType.contains("text/html")) {
			try {
				Object content = msg.getContent();
				if (content != null) {
					contentStatus = 1;
					messageContent = content.toString();
				}
			} catch (Exception ex) {
				messageContent = "[Erreur lors du téléchargement du contenu]";
				ex.printStackTrace();
			}
		}

		/*mailEnvelope.setFrom(from);
		mailEnvelope.setTo(toList);
		mailEnvelope.setCc(ccList);
		mailEnvelope.setSubject(subject);
		mailEnvelope.setSentDate(sentDate);
		mailEnvelope.setReceivedDate(receivedDate);

		mailEnvelope.setContentType(contentType);
		mailEnvelope.setContent(messageContent);

		mailEnvelope.setContentStatus(contentStatus);*/ 

		return mailEnvelope;
	}

	/**
	 * Renvoie une liste d'adresses au format String séparées par une virgule
	 *
	 * @param address
	 *            un tableau d'objets Address
	 * @return une chaîne représente une liste d'adresses
	 */
	public static String parseAddresses(Address[] address) {

		String listAddress = null;

		if (address != null) {
			for (int i = 0; i < address.length; i++) {
				listAddress += address[i].toString() + ", ";
			}
		}

		if (listAddress.length() > 1) {
			listAddress = listAddress.substring(0, listAddress.length() - 2);
		}

		return listAddress;
	}
	
	public static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}
	
	@SuppressWarnings("unused")	
	public static String format(String messageContent) {
		
		String delimiter = "\\n\\n\\n";
		String[] parts = messageContent.split(delimiter);
		
		messageContent = parts[0];		
		
		return messageContent;
	}

}

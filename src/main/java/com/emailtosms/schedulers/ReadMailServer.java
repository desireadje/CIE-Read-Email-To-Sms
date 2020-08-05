package com.emailtosms.schedulers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReadMailServer {

	Date date = new Date();
	String data_now = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);

	// protocol = pop3
	// port_entant = 110
	// port_sortant : 587
	// port_entrant_securise = 995
	// host : mail.infomaniak.com
	// username : smspro@hyperaccesss.com
	// password : SRVsmpp123456 / skriza@19

	String protocol = "pop3";
	String host = "mail.infomaniak.com";
	String port = "995";

	String username = "smspro@hyperaccesss.com";
	String password = "SRVsmpp123456";

	// Cette fonction permet de se connecter au serveur de messagerie
	@Scheduled(fixedDelay = 10000)
	public void ConnexionMailServer() throws Exception {
		System.err.println(data_now + " Démarrage de ConnexionMailServer");
		receiveEmail(protocol, host, port, username, password);
	}

	/**
	 * Renvoie un objet Properties qui est configuré pour un serveur POP3 / IMAP
	 *
	 * @param protocol
	 *            either "imap" or "pop3"
	 * @param host
	 * @param port
	 * @return a Properties object
	 */
	private Properties getServerProperties(String protocol, String host, String port, String username) {

		Properties properties = new Properties();

		properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.pop3.socketFactory.fallback", "false");
		properties.put("mail.pop3.socketFactory.port", port);
		properties.put("mail.pop3.port", port);
		properties.put("mail.pop3.host", host);
		properties.put("mail.pop3.user", username);
		properties.put("mail.store.protocol", protocol);

		return properties;
	}

	/**
	 * Télécharge les nouveaux messages et récupère les détails de chaque message.
	 * 
	 * @param protocol
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public void receiveEmail(String protocol, String host, String port, final String username, final String password)
			throws Exception {

		// 1. Configurer les propriétés de la session de messagerie.
		Properties properties = getServerProperties(protocol, host, port, username);
		Session session = Session.getDefaultInstance(properties);

		try {
			// connects to the message store
			Store store = session.getStore(protocol);
			store.connect(username, password);

			// opens the inbox folder
			Folder folderInbox = store.getFolder("INBOX");
			folderInbox.open(Folder.READ_ONLY);

			// fetches new messages from server
			Message[] messages = folderInbox.getMessages();

			for (int i = 0; i < messages.length; i++) {
				Message msg = messages[i];
				Address[] fromAddress = msg.getFrom();
				String from = fromAddress[0].toString();
				String subject = msg.getSubject();
				String toList = parseAddresses(msg.getRecipients(RecipientType.TO));
				String ccList = parseAddresses(msg.getRecipients(RecipientType.CC));
				String sentDate = msg.getSentDate().toString();

				String contentType = msg.getContentType();
				String messageContent = "";

				if (contentType.contains("text/plain") || contentType.contains("text/html")) {
					try {
						Object content = msg.getContent();
						if (content != null) {
							messageContent = content.toString();
						}
					} catch (Exception ex) {
						messageContent = "[Error downloading content]";
						ex.printStackTrace();
					}
				}

				// print out details of each message
				System.out.println("Message #" + (i + 1) + ":");
				System.out.println("\t From: " + from);
				System.out.println("\t To: " + toList);
				System.out.println("\t CC: " + ccList);
				System.out.println("\t Subject: " + subject);
				System.out.println("\t Sent Date: " + sentDate);
				System.out.println("\t Message: " + messageContent);
			}

			// disconnect
			folderInbox.close(false);
			store.close();
		} catch (NoSuchProviderException ex) {
			System.out.println("No provider for protocol: " + protocol);
			ex.printStackTrace();
		} catch (MessagingException ex) {
			System.out.println("Could not connect to the message store");
			ex.printStackTrace();
		}

	}

	
	/**
     * Renvoie une liste d'adresses au format String séparées par une virgule
     *
     * @param address an array of Address objects
     * @return a string represents a list of addresses
     */
    private String parseAddresses(Address[] address) {
        String listAddress = "";
 
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
}

package com.emailtosms.schedulers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.emailtosms.config.FormatNumero;
import com.emailtosms.config.ResponseFindSocieteExpediteur;
import com.emailtosms.config.Utils;
import com.emailtosms.entities.BulkSms;
import com.emailtosms.entities.Mail;
import com.emailtosms.entities.Message;
import com.emailtosms.entities.ParametreMySql;
import com.emailtosms.entities.ResponseFormatNumber;
import com.emailtosms.repositories.MailRepository;
import com.emailtosms.repositories.MySqlRepository;
import com.google.gson.Gson;

import org.apache.commons.codec.digest.DigestUtils;

@Component
public class ServiceEmailToSms {

	@Autowired
	private MailRepository mailRepos;

	@Autowired
	private MySqlRepository mySqlRepos;

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	/*
	 * Cette tache a pour role de se connecter a la base de données MySql easy via
	 * une connexion JSBC pour récupérer les Mail et les Numuro du personnel
	 */
	@Scheduled(fixedDelay = 10000)
	public void ReadMySqlDatabaseJDBC() throws Exception {

		// Je ferme les connexions.
		close();

		// Je déclare les variables locals.
		String host = null;
		String port = null;
		String dbname = null;
		String username = null;
		String password = null;
		String driver = null;

		System.out.println(Utils.dateNow() + " Run ReadMySqlDatabaseJDBC...");

		// Je recherhce les paramètres de la base de données MySQL.
		ParametreMySql mysql = mySqlRepos.findMysqlParam();

		if (mysql != null) {

			/*
			 * Je récupère les paramètres de connexion au SGBD MySql et le nom de la base de
			 * données MySQL.
			 */
			host = mysql.getHost();
			port = mysql.getPort();
			dbname = mysql.getDbname();
			username = mysql.getUsername();
			password = mysql.getPassword();
			driver = mysql.getDriver();

			try {
				// Je charge le profile de pilote MySQL.
				Class.forName(driver);

				// Je configure la connexion avec le DB.
				connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname + "?"
						+ "user=" + username + "&password=" + password + "");

				if (connection != null) {

					// Je prepare 'statement' l'excuteur des requêtes SQL vers la DB MySQL.
					statement = connection.createStatement();

					// J'implemente le script de la vue récupérant les mails pas encore recupérés
					String selectQuery = "SELECT `mail`.`idmail` AS `idmail`,`mail`.`identifiant` AS `identifiant`,`mail`.`date_send` AS `date_send`,`mail`.`date_recup` AS `date_recup`,`mail`.`sujet` AS `sujet`,`mail`.`fromm` AS `fromm`,`mail`.`cc` AS `cc`,`mail`.`bcc` AS `bcc`,`mail`.`text` AS `text`,`mail`.`html` AS `html`,`mail`.`etat` AS `etat`,`mail`.`paraid` AS `paraid`,`personnel`.`nom` AS `nom`,`personnel`.`prenoms` AS `prenoms`,`personnel`.`numero` AS `numero`,`personnel`.`service` AS `service`"
							+ "from (`personnel` join `mail` on((`personnel`.`service` = `mail`.`too`)))"
							+ "where (`mail`.`etat` = 0)";

					// Je récupère le résultat de la requête SQL (resultSet)
					resultSet = statement.executeQuery(selectQuery);

					// J'effectue la traitement des données récupérées
					if (!resultSet.next()) { // if resultSet.next() retourne false
						System.out.println(Utils.dateNow() + " Aucun mail récupéré");
					} else {
						do {
							// Je recupère l'expéditeur du mail a débité.
							String fromm = resultSet.getString("fromm");
							int idmail = resultSet.getInt("idmail");
							String numero = resultSet.getString("numero");

							/*
							 * J'effectue un traitement sur le mail de l'expediteur (récuperer le nom de la
							 * societé).
							 */
							String societe = Utils.findSocieteExpediteur(fromm);

							/*
							 * Je format le numero du destinataire ayant reçu l'appel (donnée récupérée du
							 * champ 'destinataire')
							 */
							ResponseFormatNumber rfn = FormatNumero.number_F(numero);

							// Si la societé est bien récupérée et que le numero est bien formaté alors...
							if (societe != null && rfn.isResponse()) {

								/*
								 * Je recherche une societe ayant une groupe d'envoi affilié a une application
								 * (utilisateur) ayant pour role exceptionnel 'EmailToSms'. OBJECTIF : récupérer
								 * l'identifiant du groupe d'envoi
								 */
								List<Object[]> objects = mailRepos.findIdGroupeEnvoi(societe);

								if (objects.size() != 0) {

									/* Je recupère les information de l'application */
									String applicationEmailToSms = null;
									String username1 = null;
									String token = null;
									String sender = null;

									for (Object[] object : objects) {
										applicationEmailToSms = object[0].toString();
										username1 = object[1].toString();
										token = object[2].toString();
										sender = object[3].toString();
									}

									String line = null;

									// Je parcours le résultat de l'exécution pour recupérer les autres données.
									String identifiant = resultSet.getString("identifiant");
									Date date_send = resultSet.getDate("date_send");
									Date date_recup = resultSet.getDate("date_recup");
									String sujet = resultSet.getString("sujet");
									String too = resultSet.getString("service");
									String cc = resultSet.getString("cc");
									String bcc = resultSet.getString("bcc");
									String text = resultSet.getString("text");
									String html = resultSet.getString("html");
									int paraid = resultSet.getInt("paraid");

									String nom = resultSet.getString("nom");
									String prenoms = resultSet.getString("prenoms");

									// Je foramat une reference de mail pour la ligne.
									line = idmail + identifiant + date_send + date_recup + sujet + fromm + too + cc
											+ bcc + text + paraid + nom + prenoms + numero;

									String date_format = Utils.dateFormat("yyyyMMdd");
									String code = DigestUtils.md5Hex(date_format + line);

									// 14. Je vérifie si l'identifiant du mail n'est pas déja enregistré.
									Mail mail = null;
									mail = mailRepos.findMailByIdentitifiant(code);

									if (mail == null) {

										Mail m = new Mail();

										m.setApplicationEmailToSms(applicationEmailToSms);
										m.setUsername(username1);
										m.setToken(token);
										m.setSender(sender);

										m.setCode(code);
										m.setIdmail(String.valueOf(idmail));
										m.setIdentifiant(identifiant);

										m.setDate_send(date_send);
										m.setDate_recup(date_recup);

										m.setSujet(sujet);
										m.setFromm(fromm);
										m.setToo(too);
										m.setCc(cc);
										m.setBcc(bcc);
										m.setText(text);
										m.setHtml(html);

										m.setEtat(0);
										m.setParaid(paraid);

										m.setNom(nom + " " + prenoms);
										m.setNumero(rfn.getNumero());

										m.setDateCreation(new Date());
										m.setDateModification(new Date());

										m = mailRepos.save(m);

										updateMail(connection, 1, idmail); // mise a jour du mail dans la DB MySQL
									} else {
										updateMail(connection, 1, idmail); // mise a jour du mail dans la DB MySQL
									}
								} else {
									updateMail(connection, 1, idmail);
								}
							} else {
								// Je met a jour le mail dans Mysql. mail expéditeur incorrect.
								updateMail(connection, 1, idmail);
							}
						} while (resultSet.next());
					}
				}
			} catch (Exception e) {
				throw e;
			} finally {
				// Je ferme les connexions.
				close();
			}
		}
	}

	/* Cette tache sendBulkSms permet d'envoyer les SMS via l'api par la method */
	@Scheduled(fixedDelay = 5000)
	public void SendBulkSms() {

		System.out.println(Utils.dateNow() + " Run SendBulkSms...");

		// Je déclare les variables locals.
		List<Mail> mails = null;
		List<Object[]> api = null;
		String username = null;
		String token = null;
		String sender = null;
		String title = null;

		String url = null;
		String flash = null;

		// Je récupère les mails a envoyé.
		mails = mailRepos.findAllMailOftoDayForSendSms();

		// Si la liste des mails n'est pas vide alors...
		if (mails != null) {

			// Je recupère le params Api.
			api = mailRepos.findOneParametreApi();

			// Si le parametre API n'est pas null alors...
			if (api != null) {

				// Je recupère l'url et flash dans l'api
				for (Object[] objects : api) {
					url = objects[0].toString();
					flash = objects[1].toString();
				}

				// Je parcours la liste des mails
				for (Mail mail : mails) {

					username = mail.getUsername();
					token = mail.getToken();
					sender = mail.getSender();
					title = mail.getApplicationEmailToSms();

					// Je crée une instance de ArrayList<Message>.
					ArrayList<Message> messageString = new ArrayList<Message>();

					// Je crée une instance de Message().
					Message message = new Message();

					// Je charge les attributs de l'objet message.
					message.setDest(mail.getNumero());
					message.setSms(mail.getText());
					message.setFlash(flash);
					message.setSender(sender);

					messageString.add(message);

					if (messageString != null) {

						// Je crée une instance de BulkSms().
						BulkSms bulkSms = new BulkSms();

						// J'attribut le username, le token et le titre de l'objet BulkSms().
						bulkSms.setUsername(username);
						bulkSms.setToken(token);
						bulkSms.setTitle(title);

						// Je charge Mssg de l'objet BulkSms.
						bulkSms.setMssg(messageString);

						// Je crée une instance de Gson().
						Gson gson = new Gson();

						// Je crée une instance de HttpHeaders().
						HttpHeaders headers = new HttpHeaders();

						// Je définis le header (en-tête) pour retouner du JSON.
						headers.setContentType(MediaType.APPLICATION_JSON);

						// Je crée une instance de RestTemplate().
						RestTemplate restTemplate = new RestTemplate();

						// J'encode bulkSms en JSON.
						HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(bulkSms), headers);

						// J'envoi le sms au destinataire finaux.
						ResponseEntity<String> response = restTemplate.postForEntity(url + "/addBulkSms", entity,
								String.class);
						System.out.println(
								Utils.dateNow() + " SendBulkSms CodeReponse : " + response.getStatusCodeValue());

						// Je vérifie le code de la réponse retrournée.
						if (response.getStatusCodeValue() == 200) {
							// Je mets a jour les tickets (Etat = 1) notifiés.
							updateBulkSms(mails);
						} else {
							System.out.println(Utils.dateNow() + " Erreur lors de l'envoi des SMS.");
						}
					}
				}
			}
		}
	}

	/* mise a jour du mail dans la base de données MySql */
	private void updateMail(Connection connection, int statusTraite, int idmail) throws SQLException {
		// mise a jour du mail dans la base de données MySql
		String updateQuery = "UPDATE Mail SET etat=? WHERE idmail=?";

		preparedStatement = connection.prepareStatement(updateQuery);
		preparedStatement.setInt(1, statusTraite);
		preparedStatement.setInt(2, idmail);

		int rowsUpdated = preparedStatement.executeUpdate();
		if (rowsUpdated > 0) {
			System.out.println(Utils.dateNow() + " Un mail id " + idmail + " existant a été mis à jour avec succès !");
		}
	}

	/* Cette fonction permet de fermer toutes les instances */
	private void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/* Cette fonction permet de meettre a jour les tickets traités */
	public void updateBulkSms(List<Mail> mail) {

		if (mail != null) {
			for (Mail m : mail) {
				m.setEtat(1);
				m.setDateModification(new Date());

				mailRepos.save(m);
			}
			System.out.println(Utils.dateNow() + " Tous les mails ont été notifié et mis à jour.");
		} else {
			System.out.println(Utils.dateNow() + " La liste des mails est vide pour la mise à jour.");
		}
	}

}

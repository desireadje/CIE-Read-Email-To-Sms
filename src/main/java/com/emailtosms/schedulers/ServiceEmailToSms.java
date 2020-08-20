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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.emailtosms.entities.Mail;
import com.emailtosms.entities.ParametreApi;
import com.emailtosms.entities.ParametreMySql;
import com.emailtosms.http.BulkSmsHttp;
import com.emailtosms.log.GenereFichierLog;
import com.emailtosms.methods.Utils;
import com.emailtosms.repositories.MailRepository;
import com.emailtosms.repositories.MySqlRepository;
import com.emailtosms.repositories.ParametreApiRepository;
import com.emailtosms.sms.BulkSms;
import com.emailtosms.sms.Message;
import org.apache.commons.codec.digest.DigestUtils;

@Component
public class ServiceEmailToSms {

	@Autowired
	private MailRepository mailRepos;

	@Autowired
	private ParametreApiRepository apiRepos;

	@Autowired
	private MySqlRepository mySqlRepos;

	private Connection con = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	/**
	 * Cette tache a pour role de se connecter a la base de données MySql easy via
	 * une connexion JSBC pour récupérer les Mail et les Numuro du personnel
	 * 
	 * @param dbname
	 * @param user
	 * @param password
	 */
	@Scheduled(fixedDelay = 10000)
	public void ReadMySqlDatabase() throws Exception {

		System.err.println(Utils.dateNow() + " Run readMySqldDataBase");

		// GenereFichierLog fichierLog = new GenereFichierLog();
		// fichierLog.log();

		// Je recherhce les params de la db distant.
		ParametreMySql mysql = mySqlRepos.findMysqlParam();

		String dbname, username, password, driver = null;

		if (mysql != null) {

			// Je declare les paramètres de connexion au SGBD MySql et le nom de la bdd
			dbname = mysql.getDbname();
			username = mysql.getUsername();
			password = mysql.getPassword();
			driver = mysql.getDriver();

			try {
				// 1. Je charge le profile de pilote MySQL
				Class.forName("com.mysql.jdbc.Driver");

				// 2. Je configure la connexion avec le DB
				con = DriverManager.getConnection("jdbc:mysql://10.10.130.67:3306/" + dbname + "?" + "user=" + username
						+ "&password=" + password + "");

				if (con != null) {

					// 3. Je prepare 'statement' l'excuteur des requêtes SQL vers la base de données
					statement = con.createStatement();

					// 4. J'implemente le script de la vue récupérant les mails pas encore recupérés
					String selectQuery = "SELECT `mail`.`idmail` AS `idmail`,`mail`.`identifiant` AS `identifiant`,`mail`.`date_send` AS `date_send`,`mail`.`date_recup` AS `date_recup`,`mail`.`sujet` AS `sujet`,`mail`.`fromm` AS `fromm`,`mail`.`cc` AS `cc`,`mail`.`bcc` AS `bcc`,`mail`.`text` AS `text`,`mail`.`html` AS `html`,`mail`.`etat` AS `etat`,`mail`.`paraid` AS `paraid`,`personnel`.`nom` AS `nom`,`personnel`.`prenoms` AS `prenoms`,`personnel`.`numero` AS `numero`,`personnel`.`service` AS `service`"
							+ "from (`personnel` join `mail` on((`personnel`.`service` = `mail`.`too`)))"
							+ "where (`mail`.`etat` = 0)";

					// 4. Je récupère le résultat de la requête SQL (resultSet)
					resultSet = statement.executeQuery(selectQuery);

					// 5. J'effectue la traitement des données récupérées
					// ResultSet est initialement avant le premier ensemble de données

					if (!resultSet.next()) { // if resultSet.next() retourne false
						System.err.println(Utils.dateNow() + " Aucun mail récupéré");
					} else {
						do {
							String line = null;

							int idmail = resultSet.getInt("idmail");
							String identifiant = resultSet.getString("identifiant");

							Date date_send = resultSet.getDate("date_send");
							Date date_recup = resultSet.getDate("date_recup");

							String sujet = resultSet.getString("sujet");
							String fromm = resultSet.getString("fromm");
							String too = resultSet.getString("service");
							String cc = resultSet.getString("cc");
							String bcc = resultSet.getString("bcc");
							String text = resultSet.getString("text");
							String html = resultSet.getString("html");
							int paraid = resultSet.getInt("paraid");

							String nom = resultSet.getString("nom");
							String prenoms = resultSet.getString("prenoms");
							String numero = resultSet.getString("numero");

							// foramatge du code du sms de la ligne
							line = idmail + identifiant + date_send + date_recup + sujet + fromm + too + cc + bcc + text
									+ paraid + nom + prenoms + numero;
							String code = DigestUtils.md5Hex(Utils.dateNow() + line);

							// 14. Je vérifie si l'identifiant du mail n'est pas déja enregistré.
							Mail mail = null;
							mail = mailRepos.findMailByIdentitifiant(code);

							if (mail == null) {
								Mail m = new Mail();

								m.setCode(code);
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
								m.setNumero("225" + numero);

								m.setDateCreation(new Date());
								m.setDateModification(new Date());

								m = mailRepos.save(m);

								// mise a jour du mail dans la base de données MySql
								String updateQuery = "UPDATE Mail SET etat=? WHERE idmail=?";

								preparedStatement = con.prepareStatement(updateQuery);
								preparedStatement.setInt(1, 1);
								preparedStatement.setInt(2, idmail);

								int rowsUpdated = preparedStatement.executeUpdate();
								if (rowsUpdated > 0) {
									System.err.println(Utils.dateNow() + " Un mail id " + idmail
											+ " existant a été mis à jour avec succès !");
								}
							} else {
								System.err.println(Utils.dateNow() + " Ce mail est déja enregistré");
							}
						} while (resultSet.next());
					}
				}
			} catch (Exception e) {
				throw e;
			} finally {
				close();
			}
		} else {
			System.err.println(Utils.dateNow() + " La base de données distant n'est pas configuré.");
		}

	}

	// Cette fonction permet de fermer toutes les instances
	private void close() {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
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

	/**
	 * Cette tache sendBulkSms permet d'envoyer les SMS via l'api par la method
	 * BulkSms (Chaque destiantaire reçoit son contenu de SMS)
	 * 
	 * @return void
	 */
	// @Scheduled(fixedDelay = 10000)
	public void SendSmsBulkWorker() {

		System.err.println(Utils.dateNow() + " Run SendSmsBulkWorker");

		// http://10.10.130.76:8080/api/addOneSms?Username=justine&Token=$2a$10$bbqS7kicnAPCFkjbCSPN1OGOhMxavdsMOnRPir7Q39vQeu4msF5y6&Dest=22584046064&Sms=ok&Flash=0&Sender=CIE&Titre=TESTER;

		// 1. Je recherche les paramètres de l'api.
		ParametreApi api = null;
		api = apiRepos.findParamApi();
		System.err.println(api);

		if (api != null) {

			// 2. Je crée une instance de BulkSms().
			BulkSms bulkSms = new BulkSms();

			// 3. J'initialise les paramètres de l'instance de BulkSms().
			String username, token, sender, flash, title, url = null;

			// 3. Je récupère les paramètres de l'api.
			username = api.getUsername();
			token = api.getToken();
			sender = api.getSender();
			flash = api.getFlash();
			title = api.getTitle();

			// String url = "http://10.10.130.76:8080/api";
			url = api.getUrl();

			// 4. J'attribut le username, token, title et url de l'objet BulkSms().
			bulkSms.setUsername(username);
			bulkSms.setToken(token);
			bulkSms.setTitle(title);

			// 5. Je crée une instance de ArrayList<Message>.
			ArrayList<Message> messageString = new ArrayList<Message>();

			// 6. Je récupère la liste des mails.
			List<Mail> mails = null;
			mails = mailRepos.findAllMailForSendSms();

			// 8. Je verifie que les tickets (CallMissed) et modele de sms ne sont pas
			// null.
			if (mails.size() != 0) {

				// 9. Je parcours la lite de tickets.
				for (Mail mail : mails) {

					// 10. Je crée une instance de Message().
					Message message = new Message();

					// 11. J'initialise les variables du message.
					String sms = null;
					String dest = null;

					// 12. Je recheche le modèle de SMS approprié au type de ticket.
					sms = mail.getText();
					dest = mail.getNumero();

					// Si le numéro eexiste alors...
					if (dest != null) {

						// 13. Je charge les attribut de l'objet message.
						message.setDest(dest);
						message.setSms(sms);

						message.setFlash(flash);
						message.setSender(sender);

						messageString.add(message);
					}
				}

				// 14. Je charge Mssg de l'objet BulkSms.
				bulkSms.setMssg(messageString);

				// 15. J'envoi les sms via une requète http
				if (BulkSmsHttp.main(bulkSms, url)) {
					// 16. Je mets a jour les mail (Etat = 1) notifiés
					updateBulkSms(mails);
				}
			} else {
				System.err.println(Utils.dateNow() + " Aucun mail reçu disponible.");
			}
		} else {
			System.err.println(Utils.dateNow() + " Aucun paramètre Api disponible.");
		}
	}

	/**
	 * Cette fonction permet de meettre a jour les tickets traités
	 * 
	 * @param List<Mail>
	 * @return void
	 * 
	 */
	public void updateBulkSms(List<Mail> mail) {

		if (mail != null) {
			for (Mail m : mail) {
				m.setEtat(1);
				m.setDateModification(new Date());

				mailRepos.save(m);
			}
			System.err.println(Utils.dateNow() + " Tous les mails ont été notifié et mis à jour.");
		} else {
			System.err.println(Utils.dateNow() + " La liste des mails est vide pour la mise à jour.");
		}
	}

}

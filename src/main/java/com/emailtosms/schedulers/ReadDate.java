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

import com.emailtosms.entities.BulkSms;
import com.emailtosms.entities.Mail;
import com.emailtosms.entities.Message;
import com.emailtosms.entities.ParametreApi;
import com.emailtosms.methods.Utils;
import com.emailtosms.repositories.MailRepository;
import com.emailtosms.repositories.ParametreApiRepository;
import com.google.gson.Gson;

@Component
public class ReadDate {

	@Autowired
	private MailRepository mailRepos;

	@Autowired
	private ParametreApiRepository apiRepository;

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
	public void readMySqldDataBase() throws Exception {

		System.err.println(Utils.dateNow() + " Run readMySqldDataBase");

		// Je declare les paramètres de connexion au SGBD MySql et le nom de la bdd
		String dbname = "easy";
		String user = "root";
		String password = "";

		try {
			// 1. Je charge le profile de pilote MySQL
			Class.forName("com.mysql.jdbc.Driver");

			// 2. Je configure la connexion avec le DB
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/" + dbname + "?" + "user=" + user + "&password=" + password + "");

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
					System.err.println(Utils.dateNow() + " Aucun enregistrement trouvé");
				} else {
					do {

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

						// 14. Je vérifie si l'identifiant du mail n'est pas déja enregistré.
						Mail mail = null;
						mail = mailRepos.findMailByIdentitifiant(identifiant);

						if (mail == null) {
							Mail m = new Mail();

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

							m.setEtat(1);
							m.setParaid(paraid);

							m.setNom(nom);
							m.setPrenoms(prenoms);
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
	public void sendBulkSms() {
	}

}

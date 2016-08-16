package com.naugrim.zeecontainer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.naugrim.zeecontainer.frame.Dag;
import com.naugrim.zeecontainer.frame.Person;

public class DatabaseManager {
	Connection con;
	Statement stmt;
	String host, username, password;

	public DatabaseManager(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
		connect();

	}

	public void connect() {
		try {
			con = DriverManager.getConnection(host, username, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			System.err.println(
					"Failed to create a connection with the provided database");
			e.printStackTrace();
		}
	}

	/**
	 * @param sql
	 */
	public Person[] request(String sql) throws Exception {
		// TODO RETURN PERSON ARRAY. PROCESS THIS ARRAY IN CALLING FUNCTION
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		
		// TODO CREATE PERSON FROM VARIABLED THEN ADD THAT PERSON TO AN ARRAY.
		// THIS ARRAY WILL HAVE TO BE FED INTO THE DATA AND THE TABLE
		ArrayList<Person> plist = new ArrayList<>();
		while (rs.next()) {
			int DBID = rs.getInt("DatabaseID");
			int inschrijfnummer = rs.getInt("Inschrijfnummer");
			String voornaam = rs.getString("Voornaam");
			String achternaam = rs.getString("Achternaam");
			boolean reglement = (rs.getInt("Reglementen" ) == 0 ) ? false : true;
			String instantie = rs.getString("Instantie");
			String contperInstantie = rs.getString("ContactpersoonInstantie");
			String telefoonnummerContact = rs.getString("TelefoonnummerContact");
			String emailContact = rs.getString("E-mailContact");
			String adres = rs.getString("Adres");
			String postcode = rs.getString("Postcode");
			String woonplaats = rs.getString("Woonplaats");
			String telefoonnummer = rs.getString("Telefoonnummer");
			String mail = rs.getString("E-mailadres");
			int volwassenen = rs.getInt("Volwassenen");
			int kinderen = rs.getInt("Kinderen");
			Dag dag = Dag.fromString(rs.getString("Winkeldag"));
			
			plist.add(new Person(dag, voornaam, achternaam, adres, postcode, woonplaats, telefoonnummer, mail, instantie, contperInstantie, telefoonnummerContact, emailContact, DBID, inschrijfnummer, volwassenen, kinderen, reglement));
		}
		System.out.println(rs.getRow());
		System.out.println(rs.getFetchSize() + "/" + rs.getFetchDirection());

		Person[] parr = new Person[plist.size()];

		int i = 0;
		for (Iterator<Person> iterator = plist.iterator(); iterator
				.hasNext();) {
			Person person = iterator.next();
			parr[i] = person;
			i++;
		}

		return parr;
	}

	public void SendPassword(String PW) {
		String sql = "INSERT INTO `zeecontainer`.`password` (`hash`) VALUES ('"
				+ PW.hashCode() + "');";

		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void put(String table_name, Person[] Values) {
		String sql = "";
		for (int i = 0; i < Values.length; i++) {
			sql = "INSERT INTO `zeecontainer`.`" + table_name
					+ "` (`Voornaam`, `Achternaam`, `Dag`, "
					+ "`Adres`, `Postcode`, `Woonplaats`) VALUES ('"
					+ Values[i].voornaam + "', '" + Values[i].achternaam
					+ "', '" + Values[i].dag.toString() + "', '"
					+ Values[i].adres + "', ' " + Values[i].postcode + "', '"
					+ Values[i].woonplaats + "');";
			try {
				stmt.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getNextID() {

		return 1;
	}

	public void Delete(String string) throws Exception {
		stmt = con.createStatement();
		stmt.executeUpdate(string);
	}

	public int getPassword() {
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM zeecontainer.password;");
			int pwhash = 0;

			while (rs.next()) {
				pwhash = rs.getInt(2);
			}

			System.out.println(pwhash);
			return pwhash;

		} catch (Exception e) {

		}
		return 0;
	}
}

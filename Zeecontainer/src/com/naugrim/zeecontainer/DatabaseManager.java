package com.naugrim.zeecontainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.naugrim.zeecontainer.frame.Dag;
import com.naugrim.zeecontainer.frame.Person;
import com.naugrim.zeecontainer.util.RandomString;

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
			System.err.println("Failed to create a connection with the provided database");
			e.printStackTrace();
		}
	}

	/**
	 * @param sql
	 */
	public Person[] request(String sql) throws Exception {
		// TODO RETURN PERSON ARRAY. PROCESS THIS ARRAY IN CALLING FUNCTION
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM data");
		String voornaam, achternaam, adres, postcode, woonplaats;
		Dag dag;
		//TODO CREATE PERSON FROM VARIABLED THEN ADD THAT PERSON TO AN ARRAY. THIS ARRAY WILL HAVE TO BE FED INTO THE DATA AND THE TABLE
		while (rs.next()) {
			voornaam = rs.getString("Voornaam");
			achternaam = rs.getString("Achternaam");
			dag = Dag.fromString(rs.getString("Dag"));
			adres = rs.getString("adres");
			postcode = rs.getString("Postcode");
			woonplaats = rs.getString("Woonplaats");
		}
		System.out.println(rs.getRow());
		System.out.println(rs.getFetchSize() + "/" + rs.getFetchDirection());

		return new Person[] {};
	}

	public void put(String table_name, Person[] Values) {
		String sql = "";
		for (int i = 0; i < Values.length; i++) {
			sql = "INSERT INTO `zeecontainer`.`" + table_name + "` (`Voornaam`, `Achternaam`, `Dag`, " + "`Adres`, `Postcode`, `Woonplaats`) VALUES ('" + Values[i].voornaam + "', '" + Values[i].achternaam + "', '" + Dag.toString(Values[i].dag) + "', '" + Values[i].adres + "', ' " + Values[i].postcode + "', '" + Values[i].stad + "');";
			try {
				stmt.execute(sql);
			} catch (SQLException e) {http://download.eclipse.org/egit/updates
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Person> dummyData(int amountofpersons, int namelength) {
		// System.out.println("DatabaseManager.dummyData()");
		ArrayList<Person> array = new ArrayList<Person>();
		for (int i = 0; i < amountofpersons; i++) {
			// System.out.println("DatabaseManager.dummyData()");
			array.add(new Person(RandomString.generateRandomString(namelength), RandomString.generateRandomString(namelength), Dag.RandomDag(), RandomString.generateRandomString(namelength), RandomString.generateRandomZipCode("nnnnll"), RandomString.generateRandomString(namelength)));
		}
		return array;
	}

}

package com.naugrim.zeecontainer.frame;

import java.io.Serializable;

public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5370307042704985068L;
	public Dag dag;
	public String voornaam, achternaam;
	public String adres, postcode, stad;
	public int databaseID;

	public Person(int id, String voornaam, String achternaam, Dag dag, String adres, String postcode, String stad) {
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.dag = dag;
		this.adres = adres;
		this.postcode = postcode;
		this.stad = stad;
		databaseID = id;
	}
}

package com.naugrim.zeecontainer.ui;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.lowagie.text.Font;
import com.naugrim.zeecontainer.frame.Person;
import java.awt.Toolkit;

public class Details extends JFrame {

	JPanel contentPane;
	JLabel lblVoornaam;
	JTextField txtVoornaam;
	JLabel lblChternaam;
	JTextField txtAchternaam;
	JLabel lblwinkeldag;
	JTextField cbDag;
	JLabel lblStraat;
	JTextField txtAdres;
	JLabel lblWoonplaats;
	JTextField txtWoonplaats;
	JLabel lblPostcode;
	JTextField txtPostcode;
	JTextField txtInschrijfnummer;
	JLabel lblMailadres;
	JTextField txtEmail;
	JPanel persoonsgegevens;
	JLabel lblVolwassenen;
	JTextField txtVolwassenen;
	JLabel lblKinderenTm;
	JTextField txtKinderen;
	JLabel lblRegelementenGelezenEn;
	JLabel lblContactpersoonInstantie;
	JLabel lblInstantie;
	JTextField txtInstantie;
	JTextField txtContInstantie;
	JLabel lblTelefoonnummer;
	JTextField txtTelInstantie;
	JLabel lblEmailadres;
	JTextField txtMailContact;
	JPanel dagenregelementen;
	JPanel contactgegevensinstantie;
	JTextField txtTelefoon;

	/**
	 * Create the frame.
	 */

	public Details(Person p) {
		System.out.println("Toevoegen.Toevoegen()");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 599, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		persoonsgegevens = new JPanel();
		TitledBorder pg = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Persoonsgegevens", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0));
		pg.setTitleFont(pg.getTitleFont().deriveFont(Font.BOLD));
		persoonsgegevens.setBorder(pg);
		persoonsgegevens.setBounds(10, 11, 556, 255);
		contentPane.add(persoonsgegevens);
		persoonsgegevens.setLayout(null);

		lblVoornaam = new JLabel("Voornaam");
		lblVoornaam.setBounds(10, 47, 65, 14);
		persoonsgegevens.add(lblVoornaam);

		txtVoornaam = new JTextField();
		txtVoornaam.setEditable(false);
		txtVoornaam.setBounds(135, 47, 136, 20);
		persoonsgegevens.add(txtVoornaam);
		txtVoornaam.setColumns(10);

		lblChternaam = new JLabel("Achternaam");
		lblChternaam.setBounds(292, 47, 108, 14);
		persoonsgegevens.add(lblChternaam);

		txtAchternaam = new JTextField();
		txtAchternaam.setEditable(false);
		txtAchternaam.setBounds(410, 44, 136, 20);
		persoonsgegevens.add(txtAchternaam);
		txtAchternaam.setColumns(10);

		lblStraat = new JLabel("Straat + huisnummer");
		lblStraat.setBounds(10, 75, 127, 14);
		persoonsgegevens.add(lblStraat);

		txtAdres = new JTextField();
		txtAdres.setEditable(false);
		txtAdres.setBounds(135, 72, 136, 20);
		persoonsgegevens.add(txtAdres);

		lblWoonplaats = new JLabel("Woonplaats");
		lblWoonplaats.setBounds(10, 127, 115, 14);
		persoonsgegevens.add(lblWoonplaats);

		txtWoonplaats = new JTextField();
		txtWoonplaats.setEditable(false);
		txtWoonplaats.setBounds(135, 124, 136, 20);
		persoonsgegevens.add(txtWoonplaats);

		lblPostcode = new JLabel("Postcode");
		lblPostcode.setBounds(10, 103, 127, 14);
		persoonsgegevens.add(lblPostcode);

		txtPostcode = new JTextField();
		txtPostcode.setEditable(false);
		txtPostcode.setBounds(135, 100, 136, 20);
		persoonsgegevens.add(txtPostcode);
		txtPostcode.setColumns(10);

		JLabel lblInschrijfnnummer = new JLabel("Inschrijfnummer");
		lblInschrijfnnummer.setBounds(10, 22, 115, 14);
		persoonsgegevens.add(lblInschrijfnnummer);

		txtInschrijfnummer = new JTextField();
		txtInschrijfnummer.setEditable(false);
		txtInschrijfnummer.setBounds(135, 22, 136, 20);
		persoonsgegevens.add(txtInschrijfnummer);
		txtInschrijfnummer.setColumns(10);

		JLabel lblTelefoonnr = new JLabel("Telefoonnummer");
		lblTelefoonnr.setBounds(10, 152, 115, 14);
		persoonsgegevens.add(lblTelefoonnr);

		txtTelefoon = new JTextField();
		txtTelefoon.setEditable(false);
		txtTelefoon.setBounds(135, 149, 136, 20);
		persoonsgegevens.add(txtTelefoon);

		lblMailadres = new JLabel("E-mailadres");
		lblMailadres.setBounds(10, 177, 115, 14);
		persoonsgegevens.add(lblMailadres);

		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setBounds(135, 174, 136, 20);
		persoonsgegevens.add(txtEmail);
		txtEmail.setColumns(10);

		lblVolwassenen = new JLabel("Volwassenen");
		lblVolwassenen.setBounds(10, 202, 115, 14);
		persoonsgegevens.add(lblVolwassenen);

		txtVolwassenen = new JTextField();
		txtVolwassenen.setEditable(false);
		txtVolwassenen.setBounds(135, 199, 136, 20);
		persoonsgegevens.add(txtVolwassenen);
		txtVolwassenen.setColumns(10);

		lblKinderenTm = new JLabel("Kinderen t/m 17 jaar");
		lblKinderenTm.setBounds(10, 227, 127, 14);
		persoonsgegevens.add(lblKinderenTm);

		txtKinderen = new JTextField();
		txtKinderen.setEditable(false);
		txtKinderen.setBounds(135, 224, 136, 20);
		persoonsgegevens.add(txtKinderen);
		txtKinderen.setColumns(10);

		dagenregelementen = new JPanel();
		dagenregelementen.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dag en reglementen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		dagenregelementen.setBounds(10, 277, 273, 90);
		contentPane.add(dagenregelementen);
		dagenregelementen.setLayout(null);

		lblwinkeldag = new JLabel("Winkel-dag");
		lblwinkeldag.setBounds(6, 19, 115, 14);
		dagenregelementen.add(lblwinkeldag);

		cbDag = new JTextField();
		cbDag.setBounds(131, 16, 136, 20);
		cbDag.setEditable(false);
		dagenregelementen.add(cbDag);

		lblRegelementenGelezenEn = new JLabel("<html>Reglementen gelezen en getekend</html>");
		lblRegelementenGelezenEn.setBounds(6, 44, 126, 35);
		dagenregelementen.add(lblRegelementenGelezenEn);

		JCheckBox checkregelementen = new JCheckBox("");
		checkregelementen.setEnabled(false);
		checkregelementen.setBounds(131, 43, 97, 36);
		dagenregelementen.add(checkregelementen);

		contactgegevensinstantie = new JPanel();
		contactgegevensinstantie.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contactgegevens instantie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contactgegevensinstantie.setBounds(297, 277, 273, 128);
		contentPane.add(contactgegevensinstantie);
		contactgegevensinstantie.setLayout(null);

		lblContactpersoonInstantie = new JLabel("<html>Contactpersoon instantie</html>");
		lblContactpersoonInstantie.setBounds(6, 44, 115, 28);
		contactgegevensinstantie.add(lblContactpersoonInstantie);

		lblInstantie = new JLabel("Instantie");
		lblInstantie.setBounds(6, 19, 115, 14);
		contactgegevensinstantie.add(lblInstantie);

		txtInstantie = new JTextField();
		txtInstantie.setEditable(false);
		txtInstantie.setBounds(131, 16, 136, 20);
		contactgegevensinstantie.add(txtInstantie);
		txtInstantie.setColumns(10);

		txtContInstantie = new JTextField();
		txtContInstantie.setEditable(false);
		txtContInstantie.setBounds(131, 45, 136, 20);
		contactgegevensinstantie.add(txtContInstantie);
		txtContInstantie.setColumns(10);

		lblTelefoonnummer = new JLabel("Telefoonnummer");
		lblTelefoonnummer.setBounds(6, 79, 115, 14);
		contactgegevensinstantie.add(lblTelefoonnummer);

		txtTelInstantie = new JTextField();
		txtTelInstantie.setEditable(false);
		txtTelInstantie.setBounds(131, 76, 136, 20);
		contactgegevensinstantie.add(txtTelInstantie);
		txtTelInstantie.setColumns(10);

		lblEmailadres = new JLabel("E-mailadres");
		lblEmailadres.setBounds(6, 104, 115, 14);
		contactgegevensinstantie.add(lblEmailadres);

		txtMailContact = new JTextField();
		txtMailContact.setEditable(false);
		txtMailContact.setBounds(131, 101, 136, 20);
		contactgegevensinstantie.add(txtMailContact);
		txtMailContact.setColumns(10);

		txtVoornaam.setText(p.voornaam);
		txtAchternaam.setText(p.achternaam);
		txtAdres.setText(p.adres);
		txtPostcode.setText(p.postcode);
		txtWoonplaats.setText(p.woonplaats);
		txtContInstantie.setText(p.contactInstantie);
		txtEmail.setText(p.emailadres);
		txtInschrijfnummer.setText(String.valueOf(p.inschrijfnummer));
		txtInstantie.setText(p.instantie);
		txtKinderen.setText(String.valueOf(p.kinderen));
		txtMailContact.setText(p.emailContact);
		txtTelInstantie.setText(p.telefoonnummerContact);
		txtVolwassenen.setText(String.valueOf(p.volwassenen));
		txtTelefoon.setText(p.telefoonnummer);
		cbDag.setText(p.dag.toString());
	}

}

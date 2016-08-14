package com.naugrim.zeecontainer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.naugrim.zeecontainer.frame.Dag;
import com.naugrim.zeecontainer.frame.Person;

@SuppressWarnings("serial")
public class WijzigenWijzig extends JFrame {

	public JPanel contentPane;
	public JLabel lblVoornaam;
	public JTextField txtVoornaam;
	public JLabel lblChternaam;
	public JTextField txtAchternaam;
	public JLabel lblNewLabel;
	public JComboBox<String> cbDag;
	public JLabel lblStraat;
	public JTextField txtAdres;
	public JLabel lblWoonplaats;
	public JTextField txtWoonplaats;
	public JButton btnWijzigen;
	public JLabel lblPostcode;
	public JTextField txtPostcode;

	/**
	 * Create the frame.
	 */

	public WijzigenWijzig(Person p) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblVoornaam = new JLabel("Voornaam");
		lblVoornaam.setBounds(10, 11, 65, 14);
		contentPane.add(lblVoornaam);

		txtVoornaam = new JTextField();
		txtVoornaam.setBounds(85, 8, 136, 20);
		contentPane.add(txtVoornaam);
		txtVoornaam.setColumns(10);

		lblChternaam = new JLabel("Achternaam");
		lblChternaam.setBounds(10, 36, 65, 14);
		contentPane.add(lblChternaam);

		txtAchternaam = new JTextField();
		txtAchternaam.setBounds(85, 33, 136, 20);
		contentPane.add(txtAchternaam);
		txtAchternaam.setColumns(10);

		lblNewLabel = new JLabel("Dag");
		lblNewLabel.setBounds(10, 61, 46, 14);
		contentPane.add(lblNewLabel);

		cbDag = new JComboBox<String>();
		cbDag.setBounds(85, 58, 136, 20);
		cbDag.addItem("Maandag");
		cbDag.addItem("Dinsdag");
		cbDag.addItem("Woensdag");
		cbDag.addItem("Donderdag");
		cbDag.addItem("Vrijdag");
		cbDag.addItem("Zaterdag");

		contentPane.add(cbDag);

		lblStraat = new JLabel("Straat + huisnummer");
		lblStraat.setBounds(10, 86, 115, 14);
		contentPane.add(lblStraat);

		txtAdres = new JTextField();
		txtAdres.setBounds(135, 83, 86, 20);
		contentPane.add(txtAdres);

		lblWoonplaats = new JLabel("Woonplaats");
		lblWoonplaats.setBounds(10, 136, 65, 14);
		contentPane.add(lblWoonplaats);

		txtWoonplaats = new JTextField();
		txtWoonplaats.setBounds(85, 133, 136, 20);
		contentPane.add(txtWoonplaats);

		btnWijzigen = new JButton("Wijzigen");
		btnWijzigen.setBounds(10, 161, 214, 23);
		btnWijzigen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				p.voornaam = txtVoornaam.getText();
				p.achternaam = txtAchternaam.getText();
				p.dag = Dag.getDagFromDagnumber(cbDag.getSelectedIndex() + 1);
				p.adres = txtAdres.getText();
				p.postcode = txtPostcode.getText();
				p.woonplaats = txtWoonplaats.getText();

				MainFrame.populateTable(MainFrame.table, MainFrame.data);
				MainFrame.populateTable(WijzigenZoek.table, MainFrame.data);

				try {
					MainFrame.manager
							.Delete("UPDATE `zeecontainer`.`data` SET `Voornaam`='"
									+ p.voornaam + "', `Achternaam`='"
									+ p.achternaam + "', `Dag`='"
									+ p.dag.toString() + "', `Adres`='"
									+ p.adres + "', `Postcode`='" + p.postcode
									+ "', `Woonplaats`='" + p.woonplaats
									+ "' WHERE `idData`='" + p.databaseID
									+ "';");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				dispose();

			}
		});
		contentPane.add(btnWijzigen);

		lblPostcode = new JLabel("Postcode");
		lblPostcode.setBounds(10, 111, 46, 14);
		contentPane.add(lblPostcode);

		txtPostcode = new JTextField();
		txtPostcode.setBounds(85, 108, 136, 20);
		contentPane.add(txtPostcode);
		txtPostcode.setColumns(10);
	}

}

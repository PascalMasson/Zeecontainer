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
public class Toevoegen extends JFrame {

	private JPanel contentPane;
	private JLabel lblVoornaam;
	private JTextField txtVoornaam;
	private JLabel lblChternaam;
	private JTextField txtAchternaam;
	private JLabel lblNewLabel;
	private JComboBox<String> cbDag;
	private JLabel lblStraat;
	private JTextField txtAdres;
	private JLabel lblWoonplaats;
	private JTextField txtWoonplaats;
	private JButton btnToevoegen;
	private JLabel lblPostcode;
	private JTextField txtPostcode;

	/**
	 * Create the frame.
	 */

	public Toevoegen() {
		System.out.println("Toevoegen.Toevoegen()");
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

		btnToevoegen = new JButton("Toevoegen");
		btnToevoegen.setBounds(10, 161, 214, 23);
		btnToevoegen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int id = MainFrame.data
						.get(MainFrame.data.size() - 1).databaseID;
				id++;
				System.out.println(id);
				MainFrame.data.add(new Person(id, txtVoornaam.getText(),
						txtAchternaam.getText(),
						Dag.getDagFromDagnumber(cbDag.getSelectedIndex() + 1),
						txtAdres.getText(), txtPostcode.getText().trim(),
						txtWoonplaats.getText()));
				MainFrame.populateTable(MainFrame.table, MainFrame.data);

				MainFrame.manager.put("data",
						new Person[] { new Person(id, txtVoornaam.getText(),
								txtAchternaam.getText(),
								Dag.getDagFromDagnumber(
										cbDag.getSelectedIndex() + 1),
								txtAdres.getText(), txtPostcode.getText(),
								txtWoonplaats.getText()) });
				dispose();
			}
		});
		contentPane.add(btnToevoegen);

		lblPostcode = new JLabel("Postcode");
		lblPostcode.setBounds(10, 111, 46, 14);
		contentPane.add(lblPostcode);

		txtPostcode = new JTextField();
		txtPostcode.setBounds(85, 108, 136, 20);
		contentPane.add(txtPostcode);
		txtPostcode.setColumns(10);
	}
}

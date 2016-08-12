package com.naugrim.zeecontainer.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.naugrim.zeecontainer.frame.Dag;
import com.naugrim.zeecontainer.frame.Person;

@SuppressWarnings("serial")
public class Verwijderen extends JFrame implements DocumentListener {

	public static Verwijderen instance;
	private JPanel contentPane;
	final JTable table;

	final TableRowSorter<TableModel> sorter;
	DefaultTableModel model;
	private JTextField textField;

	public Verwijderen() {
		instance = this;
		model = new DefaultTableModel();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null }, },
				new String[] { "Voornaam", "Achternaam", "Dag", "Adres",
						"Postcode", "Woonplaats" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class,
					String.class, String.class, Object.class, String.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		((DefaultTableModel) table.getModel()).setRowCount(0);
		scrollPane.setViewportView(table);
		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);

		JButton btnVerwijderen = new JButton("New button");
		btnVerwijderen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int n = JOptionPane.showConfirmDialog(Verwijderen.instance,
						"Weet je zeker dat je de geselecteerde items wil verwijderen?",
						"", JOptionPane.YES_NO_OPTION);
				System.out.println(n);
				if (n == 1) {
					return;
				}

				int row = table.getSelectedRow();
				String voornaam, achternaam, adres, postcode, woonplaats;
				Dag dag;

				voornaam = (String) table.getValueAt(row, 0);
				achternaam = (String) table.getValueAt(row, 1);
				dag = Dag.fromString((String) table.getValueAt(row, 2));
				adres = (String) table.getValueAt(row, 3);
				postcode = (String) table.getValueAt(row, 4);
				woonplaats = (String) table.getValueAt(row, 5);

				System.out.println(voornaam);
				System.out.println(achternaam);
				System.out.println(dag.toString());
				System.out.println(adres);
				System.out.println(postcode);
				System.out.println(woonplaats);

				for (int i = 0; i < MainFrame.data.size(); i++) {
					Person person = MainFrame.data.get(i);
					if (person.voornaam == voornaam) {
						if (person.achternaam == achternaam) {
							if (person.dag == dag) {
								if (person.adres == adres) {
									if (person.postcode == postcode) {
										if (person.stad == woonplaats) {
											// alles is hetzelfde, ga ervan uit
											// dat je het juiste persoon hebt
											try {
												MainFrame.manager
														.Delete("DELETE FROM `zeecontainer`.`data` WHERE `idData`='"
																+ person.databaseID
																+ "';");
											} catch (Exception e1) {
												// TODO Auto-generated catch
												// block
												e1.printStackTrace();
											}

											MainFrame.data.remove(i);
											MainFrame.populateTable(table,
													MainFrame.data);
											MainFrame.populateTable(
													MainFrame.table,
													MainFrame.data);

										} else {
											continue;
										}
									} else {
										continue;
									}
								} else {
									continue;
								}
							} else {
								continue;
							}
						} else {
							continue;
						}
					} else {
						continue;
					}
				}

			}
		});
		contentPane.add(btnVerwijderen, BorderLayout.SOUTH);

		textField = new JTextField();
		textField.getDocument().addDocumentListener(this);
		contentPane.add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		updateTableFilter();

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		updateTableFilter();
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		updateTableFilter();
	}

	public void updateTableFilter() {
		String text = textField.getText();
		if (text.length() == 0) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter(text));
		}
	}
}

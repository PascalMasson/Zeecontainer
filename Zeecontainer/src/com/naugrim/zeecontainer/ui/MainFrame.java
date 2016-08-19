package com.naugrim.zeecontainer.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.naugrim.zeecontainer.frame.Dag;
import com.naugrim.zeecontainer.frame.Person;
import com.naugrim.zeecontainer.util.DatabaseManager;
import com.naugrim.zeecontainer.util.Encryption;
import com.naugrim.zeecontainer.util.Excel;
import com.naugrim.zeecontainer.util.RandomString;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	private static Dag filterDag = Dag.ALLE;
	public static DatabaseManager manager;
	public static ArrayList<Person> data = new ArrayList<Person>();
	public static String[] TableFields = new String[] { "Voornaam", "Achternaam", "Dag", "Adres", "Postcode", "Woonplaats" };
	public static Encryption encrypter, MasterEncryption;
	public static HashMap<String, String> directoryLocation = new HashMap<>();
	private String password;

	private JPanel contentPane;
	static JTable table;
	private JMenu mnFilter;
	private JMenu mnOverzichtenDag;
	private JMenuItem mnOvDMaandag;
	private JMenuItem mnOvDDinsdag;
	private JMenuItem mnOvDWoensdag;
	private JMenu mnSelectie;
	private JMenu mnSelectieDag;
	private JMenuItem mnSeDMaandag;
	private JMenuItem mnSeDDinsdag;
	private JMenuItem mnSeDWoensdag;
	private JMenuItem mnSeDDonderdag;
	private JMenuItem mnSeDVrijdag;
	private JMenuItem mnSeDZaterdag;
	private JMenuItem mnSelectieVerwijderen;
	private JMenuItem mnOvDDonderdag;
	private JMenuItem mnOvDVrijdag;
	private JMenuItem mnOvDZaterdag;
	private JMenuItem mnOvDAlle;
	private JMenu mnPrint;
	private JMenu mnPrDag;
	private JMenuItem mnPrDMaandag;
	private JMenuItem mnPrDDinsdag;
	private JMenuItem mnPrDWoensdag;
	private JMenuItem mnPrDDonderdag;
	private JMenuItem mnPrDVrijdag;
	private JMenuItem mnPrDZaterdag;
	private JMenuItem mnPrWeek;
	private JMenu mnClienten;
	private JMenuItem mnClToevoegen;
	private JMenuItem mnClWijzigen;
	private JMenuItem mnClVerwijderen;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		String host = "jdbc:mysql://192.168.178.28/zeecontainer";
		/*
		 * InetAddress adrr; try { adrr = InetAddress.getLocalHost(); String
		 * hostname = adrr.getHostName(); System.out.println("hostname: " +
		 * hostname); if (hostname == "Pascal-School") { host =
		 * "jdbc:mysql://localhost/zeecontainer"; } } catch
		 * (UnknownHostException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		manager = new DatabaseManager(host, "java", "javapw");

		try {
			MasterEncryption = new Encryption(new SecretKeySpec("SUPER".getBytes(), "Blowfish"));
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		directoryLocation.put("FirstBoot", "c:\\zeecontainer\\bootflag.txt");

		if (!Paths.get(directoryLocation.get("FirstBoot")).toFile().exists()) {
			new File(directoryLocation.get("FirstBoot")).mkdirs();
			// first time boot

			directoryLocation.put("MKL", "C:\\zeecontainer\\" + RandomString.generateRandomString(5) + ".serMK");
			directoryLocation.put("UDL", "C:\\zeecontainer\\" + RandomString.generateRandomString(5) + ".serUD");

			try {
				if (!Paths.get("C:\\zeecontainer\\FWRhU.serDL").toFile().exists())
					new File("C:\\zeecontainer\\").mkdirs();
				MasterEncryption.Write("c:\\zeecontainer\\FWRhU.serDL", directoryLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("FirstTimeBoot");

			String pw = JOptionPane.showInputDialog("What would you like to use as a password");

			manager.SendPassword(pw);

		}
		else {

			try {
				directoryLocation = MasterEncryption.Read("C:\\zeecontainer\\FWRhU.serDL");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		while (!checkPassword()) {
			System.out.println("Waiting for correct password");
		}

		checkDirectories(directoryLocation);
		/*
		 * while (password == null){ try { Thread.sleep(100); } catch
		 * (InterruptedException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } }
		 */
		try {
			encrypter = new Encryption(new SecretKeySpec(password.getBytes(), "Blowfish"));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			MasterEncryption.Write(directoryLocation.get("MKL"), password);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// data = manager.dummyData(100, 5);
		// UI Code start
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		setTitle("De Zeecontainer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);

		mnClienten = new JMenu("Cli\u00EBnten");
		menuBar.add(mnClienten);

		mnClToevoegen = new JMenuItem("Toevoegen");
		mnClToevoegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Toevoegen t = new Toevoegen();
							t.setVisible(true);
							System.out.println("MainFrame.MainFrame().new ActionListener() {...}.actionPerformed()");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}
		});
		mnClienten.add(mnClToevoegen);

		mnClWijzigen = new JMenuItem("Wijzigen");
		mnClWijzigen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WijzigenZoek wz = new WijzigenZoek();
				wz.setVisible(true);
				populateTable(wz.table, data);
			}
		});
		mnClienten.add(mnClWijzigen);

		mnClVerwijderen = new JMenuItem("Verwijderen");
		mnClVerwijderen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Verwijderen v = new Verwijderen();
				v.setVisible(true);

				populateTable(v.table, data);

			}
		});
		mnClienten.add(mnClVerwijderen);

		mnFilter = new JMenu("Overzichten");
		menuBar.add(mnFilter);

		mnOverzichtenDag = new JMenu("Dag");
		mnFilter.add(mnOverzichtenDag);

		mnOvDMaandag = new JMenuItem("Maandag");
		mnOvDMaandag.addActionListener(this);

		mnOvDAlle = new JMenuItem("Alle");
		mnOvDAlle.addActionListener(this);
		mnOverzichtenDag.add(mnOvDAlle);
		mnOverzichtenDag.add(mnOvDMaandag);

		mnOvDDinsdag = new JMenuItem("Dinsdag");
		mnOvDDinsdag.addActionListener(this);
		mnOverzichtenDag.add(mnOvDDinsdag);

		mnOvDWoensdag = new JMenuItem("Woensdag");
		mnOvDWoensdag.addActionListener(this);
		mnOverzichtenDag.add(mnOvDWoensdag);

		mnOvDDonderdag = new JMenuItem("Donderdag");
		mnOvDDonderdag.addActionListener(this);
		mnOverzichtenDag.add(mnOvDDonderdag);

		mnOvDVrijdag = new JMenuItem("Vrijdag");
		mnOvDVrijdag.addActionListener(this);
		mnOverzichtenDag.add(mnOvDVrijdag);

		mnOvDZaterdag = new JMenuItem("Zaterdag");
		mnOvDZaterdag.addActionListener(this);
		mnOverzichtenDag.add(mnOvDZaterdag);

		mnPrint = new JMenu("Print");
		menuBar.add(mnPrint);

		mnPrDag = new JMenu("Dag");
		mnPrint.add(mnPrDag);

		mnPrDMaandag = new JMenuItem("Maandag");
		mnPrDMaandag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HashMap<String, Object[]> map = new HashMap<>();

				int i = 1;

				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					Person p = (Person) iterator.next();
					map.put("0", new String[] { "Voornaam", "Achternaam", "Dag", "Adres", "Postcode", "Woonplaats" });
					if (p.dag == Dag.MAANDAG)
						map.put(String.valueOf(i), new Object[] { p.voornaam, p.achternaam, p.dag.toString(), p.adres, p.postcode, p.woonplaats });

					i++;
				}

				try {
					new Excel(map, "c:\\zeecontainer\\maandag.xls");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPrDag.add(mnPrDMaandag);

		mnPrDDinsdag = new JMenuItem("Dinsdag");
		mnPrDDinsdag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HashMap<String, Object[]> map = new HashMap<>();

				int i = 0;

				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					Person p = (Person) iterator.next();

					if (p.dag == Dag.DINSDAG)
						map.put(String.valueOf(i), new Object[] { p.voornaam, p.achternaam, p.dag.toString(), p.adres, p.postcode, p.woonplaats });

					i++;
				}

				try {
					new Excel(map, "c:\\zeecontainer\\dinsdag.xls");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPrDag.add(mnPrDDinsdag);

		mnPrDWoensdag = new JMenuItem("Woensdag");
		mnPrDWoensdag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HashMap<String, Object[]> map = new HashMap<>();

				int i = 0;

				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					Person p = (Person) iterator.next();

					if (p.dag == Dag.WOENSDAG)
						map.put(String.valueOf(i), new Object[] { p.voornaam, p.achternaam, p.dag.toString(), p.adres, p.postcode, p.woonplaats });

					i++;
				}

				try {
					new Excel(map, "c:\\zeecontainer\\woensdag.xls");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPrDag.add(mnPrDWoensdag);

		mnPrDDonderdag = new JMenuItem("Donderdag");
		mnPrDDonderdag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HashMap<String, Object[]> map = new HashMap<>();

				int i = 0;

				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					Person p = (Person) iterator.next();

					if (p.dag == Dag.DONDERDAG)
						map.put(String.valueOf(i), new Object[] { p.voornaam, p.achternaam, p.dag.toString(), p.adres, p.postcode, p.woonplaats });

					i++;
				}

				try {
					new Excel(map, "c:\\zeecontainer\\donderdag.xls");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPrDag.add(mnPrDDonderdag);

		mnPrDVrijdag = new JMenuItem("Vrijdag");
		mnPrDVrijdag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HashMap<String, Object[]> map = new HashMap<>();

				int i = 0;

				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					Person p = (Person) iterator.next();

					if (p.dag == Dag.VRIJDAG)
						map.put(String.valueOf(i), new Object[] { p.voornaam, p.achternaam, p.dag.toString(), p.adres, p.postcode, p.woonplaats });

					i++;
				}

				try {
					new Excel(map, "c:\\zeecontainer\\vrijdag.xls");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPrDag.add(mnPrDVrijdag);

		mnPrDZaterdag = new JMenuItem("Zaterdag");
		mnPrDZaterdag.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HashMap<String, Object[]> map = new HashMap<>();

				int i = 0;

				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					Person p = (Person) iterator.next();

					if (p.dag == Dag.ZATERDAG)
						map.put(String.valueOf(i), new Object[] { p.voornaam, p.achternaam, p.dag.toString(), p.adres, p.postcode, p.woonplaats });

					i++;
				}

				try {
					new Excel(map, "c:\\zeecontainer\\zaterdag.xls");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnPrDag.add(mnPrDZaterdag);

		mnPrWeek = new JMenuItem("Weekoverzicht");
		mnPrint.add(mnPrWeek);

		mnSelectie = new JMenu("Selectie");
		// menuBar.add(mnSelectie);

		mnSelectieDag = new JMenu("Dag");
		mnSelectie.add(mnSelectieDag);

		mnSeDMaandag = new JMenuItem("Maandag");
		mnSelectieDag.add(mnSeDMaandag);

		mnSeDDinsdag = new JMenuItem("Dinsdag");
		mnSelectieDag.add(mnSeDDinsdag);

		mnSeDWoensdag = new JMenuItem("Woensdag");
		mnSelectieDag.add(mnSeDWoensdag);

		mnSeDDonderdag = new JMenuItem("Donderdag");
		mnSelectieDag.add(mnSeDDonderdag);

		mnSeDVrijdag = new JMenuItem("Vrijdag");
		mnSelectieDag.add(mnSeDVrijdag);

		mnSeDZaterdag = new JMenuItem("Zaterdag");
		mnSelectieDag.add(mnSeDZaterdag);

		mnSelectieVerwijderen = new JMenuItem("Verwijderen");
		mnSelectie.add(mnSelectieVerwijderen);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null, null, null, null, null }, }, new String[] { "Inschrijfnummer", "Voornaam", "Achternaam", "Dag", "Adres", "Postcode", "Woonplaats", "E-mailadres", "Telefoonnummer", "Volwassenen", "Kinderen" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, Object.class, String.class, String.class, String.class, String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		((DefaultTableModel) table.getModel()).setRowCount(0);

		table.setBounds(10, 94, 534, 276);

		scrollPane.setViewportView(table);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// After the table is created populate it with the data in the list
		populateTable(table, data);

		/*
		 * try { // Use enryption to write all userdata to the stored location
		 * encrypter.Write(directoryLocation.get("UDL"), data.toArray()); }
		 * catch (Exception e) { e.printStackTrace(); }
		 */

		Person[] tmp = null;

		try {
			tmp = manager.request("SELECT * FROM data;");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		data.removeAll(data);

		for (int j = 0; j < tmp.length; j++) {
			Person person = tmp[j];
			data.add(person);
		}
		populateTable(table, data);

	}

	public void actionPerformed(ActionEvent e) {

		// check of een andere dag moet worden laten zien
		if ((JMenuItem) e.getSource() == mnOvDMaandag) {
			// Laat alleen maandag zien
			ChangeFilter(Dag.MAANDAG);
			System.out.println("Maandag filter");
		}
		if ((JMenuItem) e.getSource() == mnOvDDinsdag) {
			ChangeFilter(Dag.DINSDAG);
			// Laat alleen dinsdag zien
			System.out.println("Dinsdag filter");
		}
		if ((JMenuItem) e.getSource() == mnOvDWoensdag) {
			ChangeFilter(Dag.WOENSDAG);
			// Laat alleen woensdag zien
			System.out.println("Woensdag filter");
		}
		if ((JMenuItem) e.getSource() == mnOvDDonderdag) {
			// Laat alleen Donderdag zien
			ChangeFilter(Dag.DONDERDAG);
			System.out.println("Donderdag filter");
		}
		if ((JMenuItem) e.getSource() == mnOvDVrijdag) {
			// laat alleen vrijdag zien
			ChangeFilter(Dag.VRIJDAG);
			System.out.println("Vrijdag filter");
		}
		if ((JMenuItem) e.getSource() == mnOvDZaterdag) {
			// laat alleen zaterdag zien
			ChangeFilter(Dag.ZATERDAG);
			System.out.println("Zaterdag filter");
		}
		if ((JMenuItem) e.getSource() == mnOvDAlle) {
			ChangeFilter(Dag.ALLE);
		}
	}

	public static void populateTable(JTable table, ArrayList<Person> list) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Person cur;
		model.setRowCount(0);
		for (int i = 0; i < list.size(); i++) {
			cur = list.get(i);
			if (filterDag == Dag.ALLE || filterDag == cur.dag)
				model.addRow(new Object[] { cur.inschrijfnummer, cur.voornaam, cur.achternaam, cur.dag.toString(), cur.adres, cur.postcode, cur.woonplaats, cur.emailadres, cur.telefoonnummer, cur.volwassenen, cur.kinderen });

		}
	}

	void ChangeFilter(Dag dag) {
		filterDag = dag;
		((DefaultTableModel) table.getModel()).setRowCount(0);
		populateTable(table, data);

	}

	Person getPersonInRow(int row) {
		return data.get(row);
	}

	void print(String print) {
		JTextPane textPane = new JTextPane();
		textPane.setText(print);
		try {
			textPane.print();
		} catch (PrinterException e) {
			e.printStackTrace();
		}
	}

	public void checkDirectories(HashMap<String, String> locs) {
		Collection<String> a = locs.values();
		for (Iterator<String> iterator = a.iterator(); iterator.hasNext();) {
			String string = iterator.next();
			Path p = Paths.get(string);
			if (!p.toFile().exists()) {
				new File(p.toString());
			}
		}
	}

	public boolean checkPassword() {

		String result = JOptionPane.showInputDialog("Password?");
		int DBPW = manager.getPassword();

		if (DBPW == result.hashCode()) {
			password = result;
			return true;
		}
		else {
			checkPassword();
		}

		return false;

	}
}

package com.naugrim.zeecontainer.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;

import com.naugrim.zeecontainer.Zeecontainer;
import com.naugrim.zeecontainer.frame.Dag;
import com.naugrim.zeecontainer.frame.Person;
import com.naugrim.zeecontainer.util.DatabaseManager;
import com.naugrim.zeecontainer.util.Encryption;
import com.naugrim.zeecontainer.util.RandomString;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	private static Dag filterDag = Dag.ALLE;
	public static DatabaseManager manager;
	public static ArrayList<Person> data = new ArrayList<Person>();
	public static String[] TableFields = new String[] { "Voornaam", "Achternaam", "Dag", "Adres", "Postcode", "Woonplaats" };
	public static Encryption encrypter, MasterEncryption;
	public static HashMap<String, String> directoryLocation = new HashMap<>();

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
	private JButton btnToevoegen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
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
		}
		else {
			try {
				directoryLocation = MasterEncryption.Read("C:\\zeecontainer\\FWRhU.serDL");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		checkDirectories(directoryLocation);
		LinkedList<String> lls = new LinkedList<>();
		String s = (String) JOptionPane.showInputDialog("Encryption Key?");
		while (s == null) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Waiting for input");
		}
		lls.add(RandomString.generateRandomString(s.length()));
		lls.add(RandomString.generateRandomString(s.length()));
		lls.add(s);
		lls.add(RandomString.generateRandomString(s.length()));
		lls.add(RandomString.generateRandomString(s.length()));
		try {
			encrypter = new Encryption(new SecretKeySpec(s.getBytes(), "Blowfish"));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			MasterEncryption.Write(directoryLocation.get("MKL"), s);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		manager = new DatabaseManager("jdbc:mysql://localhost/zeecontainer", "java", "javapw");
		data = manager.dummyData(100, 5);
		// UI Code start
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		setTitle("De Zeecontainer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);

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
		mnPrDag.add(mnPrDMaandag);

		mnPrDDinsdag = new JMenuItem("Dinsdag");
		mnPrDag.add(mnPrDDinsdag);

		mnPrDWoensdag = new JMenuItem("Woensdag");
		mnPrDag.add(mnPrDWoensdag);

		mnPrDDonderdag = new JMenuItem("Donderdag");
		mnPrDag.add(mnPrDDonderdag);

		mnPrDVrijdag = new JMenuItem("Vrijdag");
		mnPrDag.add(mnPrDVrijdag);

		mnPrDZaterdag = new JMenuItem("Zaterdag");
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
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null }, }, new String[] { "Voornaam", "Achternaam", "Dag", "Adres", "Postcode", "Woonplaats" }) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, Object.class, String.class };

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
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

		// create a popupmenu for a rightclick event on the table
		JPopupMenu popupMenu = new JPopupMenu();

		// create a menuItem with an actionListener
		JMenuItem deleteItem = new JMenuItem("Delete");
		deleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(Zeecontainer.frame, "Deleted...!!!");
			}
		});

		// add the items to the popupmenu
		popupMenu.add(deleteItem);
		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
						if (rowAtPoint > -1) {
							table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
						}
					}
				});

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent arg0) {

			}
		});

		table.setComponentPopupMenu(popupMenu);

		btnToevoegen = new JButton("Toevoegen");
		btnToevoegen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Toevoegen t = new Toevoegen();
				t.setVisible(true);
				System.out.println("MainFrame.MainFrame().new ActionListener() {...}.actionPerformed()");
			}
		});
		contentPane.add(btnToevoegen, BorderLayout.SOUTH);

		try {
			// Use enryption to write all userdata to the stored location
			encrypter.Write(directoryLocation.get("UDL"), data.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// convert the object array to a person array
		// convert the list to an object array. since you can't convert
		// an object array to a person array. first dimpp the object into a
		// person arraylist since an object can be converted to a person. after
		// that create a person array with a size, the zise of the list. after
		// that
		// use an iterator to loop through the list andd add all persons to a
		// new array. this array will be uploaded to the database

		Object[] oarr = data.toArray();
		ArrayList<Person> plist = new ArrayList<>();

		for (int i = 0; i < oarr.length; i++) {
			Object object = oarr[i];
			plist.add((Person) object);
		}

		Person[] parr = new Person[plist.size()];
		int i = 0;
		for (Iterator<Person> iterator = plist.iterator(); iterator.hasNext();) {
			Person person = iterator.next();
			parr[i] = person;
			i++;
		}
		// TODO REMOVE From here
		// Upload the data to the database
		manager.put("data", parr);
		try {
			manager.request("");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO STOP REMOVE HERE
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

	static void populateTable(JTable table, ArrayList<Person> list) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Person cur;
		for (int i = 0; i < list.size(); i++) {
			cur = list.get(i);
			if (filterDag == Dag.ALLE || filterDag == cur.dag)
				model.addRow(new Object[] { cur.voornaam, cur.achternaam, Dag.toString(cur.dag), cur.adres, cur.postcode, cur.stad });

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
}

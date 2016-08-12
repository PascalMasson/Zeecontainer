package com.naugrim.zeecontainer.frame;

import java.util.Random;

public enum Dag {
	MAANDAG(1), DINSDAG(2), WOENSDAG(3), DONDERDAG(4), VRIJDAG(5), ZATERDAG(
			6), ZONDAG(7), ALLE(8);

	private final int dagnummer;

	Dag(int num) {
		dagnummer = num;
	}

	public int dagnummer() {
		return dagnummer;
	}

	public static Dag getDagFromDagnumber(int num) {
		switch (num) {
			case 1:
				return MAANDAG;
			case 2:
				return DINSDAG;
			case 3:
				return WOENSDAG;
			case 4:
				return DONDERDAG;
			case 5:
				return VRIJDAG;
			case 6:
				return ZATERDAG;
			case 7:
				return ZONDAG;
			default:
				return ALLE;
		}
	}

	public static Dag RandomDag() {
		// TODO Auto-generated method stub
		return getDagFromDagnumber(new Random().nextInt(5) + 1);
	}

	public static String toString(Dag dag) {
		if (dag == MAANDAG) {
			return "Maandag";
		} else if (dag == DINSDAG) {
			return "Dinsdag";
		} else if (dag == WOENSDAG) {
			return "Woensdag";
		} else if (dag == DONDERDAG) {
			return "Donderdag";
		} else if (dag == VRIJDAG) {
			return "Vrijdag";
		} else if (dag == ZATERDAG) {
			return "Zaterdag";
		} else if (dag == ZONDAG) {
			return "Zondag";
		} else if (dag == ALLE) {
			return "Alle";
		} else {
			return "";
		}
	}

	public static Dag fromString(String s) {
		if (s.equalsIgnoreCase("maandag")) {
			return MAANDAG;
		} else if (s.equalsIgnoreCase("dinsdag")) {
			return DINSDAG;
		} else if (s.equalsIgnoreCase("woensdag")) {
			return WOENSDAG;
		} else if (s.equalsIgnoreCase("donderdag")) {
			return DONDERDAG;
		} else if (s.equalsIgnoreCase("vrijdag")) {
			return VRIJDAG;
		} else if (s.equalsIgnoreCase("zaterdag")) {
			return ZATERDAG;
		} else if (s.equalsIgnoreCase("zondag")) {
			return ZONDAG;
		} else if (s.equalsIgnoreCase("alle")) {
			return ALLE;
		}
		return null;
	}
}

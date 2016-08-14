package com.naugrim.zeecontainer;

import java.awt.Graphics2D;
import java.awt.SplashScreen;

import com.naugrim.zeecontainer.ui.MainFrame;

public class Zeecontainer {
	public static MainFrame frame;
	public static Zeecontainer instance;

	public static void main(String[] args) {
		new Zeecontainer();
	}

	public Zeecontainer() {
		frame = new MainFrame();
		frame.setVisible(true);
	}
}

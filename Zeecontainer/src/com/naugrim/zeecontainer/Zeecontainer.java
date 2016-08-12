package com.naugrim.zeecontainer;

import com.naugrim.zeecontainer.ui.MainFrame;

public class Zeecontainer {
	public static MainFrame frame;
	public static Zeecontainer instance;

	public static void main(String[] args) {
		new Zeecontainer();
	}

	public Zeecontainer() {
		instance = this;
		frame = new MainFrame();
		frame.setVisible(true);
	}
}

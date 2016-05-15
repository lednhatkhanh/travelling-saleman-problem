package code;

import java.awt.EventQueue;

import gui.Traveling_Saleman_GUI;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Traveling_Saleman_GUI window = new Traveling_Saleman_GUI();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

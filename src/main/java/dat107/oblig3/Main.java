package dat107.oblig3;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import dat107.oblig3.gui.AppWindow;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hello world!");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}

	private static void createAndShowGui() {
		AppWindow frame = new AppWindow();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
}
package main;
import javax.swing.SwingUtilities;

public class Initializer {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TheDungeon();
			}
		});
	}
}
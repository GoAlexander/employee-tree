package start;

import gui.SwingGUI5;
import gui.SwingGUI5Model;

public class SwingApp5 {

	public static void main(String[] args) {
		SwingGUI5Model theAppModel = new SwingGUI5Model();
		SwingGUI5 theFrame = new SwingGUI5(theAppModel);
		theFrame.show();

	}
}

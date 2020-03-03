import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MenuButton extends JButton{
	public MenuButton (String name) {
		super(name);
	}
}

class Reload extends MenuButton {
	
	public Reload(String name) {
		super(name);
		addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				TwoColors.gameFinished = false;
				TwoColors.moves = 0;
				TwoColors.rePopulate();
			}  
		});  
	}
}


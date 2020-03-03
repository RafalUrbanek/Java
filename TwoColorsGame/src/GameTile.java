import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameTile extends JButton {
	boolean tileState;
	int posX, posY;
	
	// returns true or false at 50/50 chance
	public static boolean randomState() {
		boolean state;
		if (Math.random()>0.5) {
			state = true;
		}else state = false;
		return state;
	}
	
	// reverses the state of the tile
	public void reverseState() {
		if (tileState == true) {
			tileState = false;
			setBackground(Color.RED);
		} else {
			tileState = true;
			setBackground(Color.GREEN);
		}
	}	
	
	// random state constructor
	public GameTile(int posX, int posY) {
		this( posX, posY, randomState());
		}
	
	// defined position and state constructor
	public GameTile(int posX, int posY, boolean state){
		this.posX = posX;
		this.posY = posY;
		
		if (state == false) {
			setBackground(Color.RED);
			tileState = false;
		}
		else {
			setBackground(Color.GREEN);
			tileState = true;
		}
		
		addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				if (TwoColors.gameFinished == false)
				TwoColors.tileClick(posX, posY);
			}  
		});  
	}
}
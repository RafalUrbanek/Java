package main;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Tabs extends JPanel{
	
	static JButton[] commandButton;
	static JPanel commandPanel;
	static int counter = 0;
	
	public Tabs() {
		commandButton = new JButton[TheDungeon.commandList.length];
		commandPanel = new JPanel();
		commandPanel.setLayout(new GridLayout(6, 2));
		
		for(String command : TheDungeon.commandList) {
			commandPanel.add(addButton(command));
		}
		add(commandPanel);
	}
	
	// adds button to the button list
	private static JButton addButton(String buttonName) {
		commandButton[counter] = new JButton(buttonName);
		commandButton[counter].setVisible(false);
		commandButton[counter].setPreferredSize(new Dimension(130, 80));
		commandButton[counter].setFont(commandButton[counter].getFont().deriveFont(18.0f));
		
		// command button action listener
		commandButton[counter].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
				TheDungeon.actionArea.append(buttonName.toUpperCase() + "\n");
				Support.pause(200);
				Commands.userCommand(buttonName);
				buttonsUpdate();
			}  
		});  
		counter++;
		return commandButton[counter -1];
	}
	
	// enables and disables buttons based on the current state of the game
	public static void buttonsUpdate() {
		for(int i=0; i<TheDungeon.commandList.length-1; i++) {
			if (Commands.isValid(i)) {
				commandButton[i].setEnabled(true);
			} else {
				commandButton[i].setEnabled(false);
			}
		}
	}
}

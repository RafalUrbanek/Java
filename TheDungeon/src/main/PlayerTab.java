package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import res.Picture;

public class PlayerTab extends JPanel {
	JLabel stats = new JLabel();
	static JLabel inventory = new JLabel();
	JLabel leftStatsPanel = new JLabel();
	static JTextArea statsText = new JTextArea();
	JProgressBar health = new JProgressBar();
	private final static int SLOTS_X = 4;
	private final static int SLOTS_Y = 5;
	static JButton[] invButton = new JButton[SLOTS_X * SLOTS_Y];
	JPanel topMenu = new JPanel();
	static JPanel statButtonPanel = new JPanel();
	static JButton[] statButtonArray = new JButton[4];
	static JButton statButton;
	static ArrayList<String> possesedItems = new ArrayList<String>();

	Border standardMargin = BorderFactory.createMatteBorder(0, 10, 10, 10, TheDungeon.defaultColor);
	Border standardMargin2 = BorderFactory.createMatteBorder(3, 0, 3, 10, TheDungeon.defaultColor);
	Border standardBorder = BorderFactory.createRaisedBevelBorder();
	Border border = BorderFactory.createCompoundBorder(standardMargin, standardBorder);
	Border statBorder = BorderFactory.createCompoundBorder(standardMargin2, standardBorder);
	Border statBorderPoints = BorderFactory.createEmptyBorder(108, 5, 0, 0);

	public PlayerTab() {
		possesedItems.add("potions"); // fills slot of the array that corresponds with potions
		setLayout(new BorderLayout());
		statsUpdate();
		statsText.setBackground(TheDungeon.defaultColor);
		statsText.setBorder(statBorder);
		statsText.setEditable(false);
		statsText.setFont(statsText.getFont().deriveFont(16.0f));
		statsText.setPreferredSize(new Dimension(220, 212)); // Stats panel size
		statButtonPanel.setPreferredSize(new Dimension(40, 212)); // Button panel size
		statButtonPanel.setBorder(statBorderPoints);
		addStatButons();

		topMenu.setLayout(new BorderLayout());
		topMenu.add(statsText, BorderLayout.EAST);
		topMenu.add(statButtonPanel, BorderLayout.WEST);

		inventory.setBorder(border);
		inventory.setLayout(new GridLayout(SLOTS_X, SLOTS_Y));
		inventory.setPreferredSize(new Dimension(300, 210)); // Inventory size
		populateInv();

		add(topMenu, BorderLayout.CENTER);
		add(inventory, BorderLayout.SOUTH);
	}
	
	private static String statButtonDescription(int i) {
		String description = "";
		switch(i) {
		case 0: description = "<html>Health points increase by 10</html>"; break;
		case 1: description = "<html>Strength increase by 1. <br>Stronger attacks.</html>"; break;
		case 2: description = "<html>Agility increase by 1. <br>Better chance to flee from the oponent</html>"; break;
		case 3: description = "<html>Inteligence increase by 1. <br>Better chance to find your way out of the dungeon</html>"; break;
		}
		return description;
	}

	private static void addStatButons() {
		for (int i = 0; i < statButtonArray.length; i++) {
			final int j = i;
			statButton = new JButton();
			statButton.setPreferredSize(new Dimension(26, 16)); // Button size
			statButton.setVisible(false);
			statButtonArray[i] = statButton;
			statButtonArray[i].setIcon(new Picture("misc/skillPoint.png", 26, 16).scaledPic);
			statButtonPanel.add(statButtonArray[i]);
			statButtonArray[i].setToolTipText(statButtonDescription(i));
			statButtonArray[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					statButtonClick(j);
				}
			});
		}
	}

	protected static void statButtonClick(int i) {
		switch (i) {
		case 0:
			Player.hp += 10;
			Player.maxHp += 10;
			TheDungeon.health.setMaximum(Player.maxHp);
			attributePointUsed();
			break;

		case 1:
			Player.strength += 1;
			attributePointUsed();
			break;

		case 2:
			Player.agility += 1;
			attributePointUsed();
			break;

		case 3:
			Player.inteligence += 1;
			attributePointUsed();
			break;
		}
	}

	private static void attributePointUsed() {
		Player.attributePoints -= 1;
		if (Player.attributePoints < 1) {
			for (int i = 0; i < statButtonArray.length; i++) {
				statButtonArray[i].setVisible(false);
			}
		}
		Support.refresh();
	}

	public static void statsUpdate() {
		statsText.setText("");
		statsText.append("  level:\t" + Player.level + "\n" );
		statsText.append("  exp:\t" + Player.exp + "/" + Player.nextLvl() + "\n");
		statsText.append("  Skill Points:\t" + Player.attributePoints + "\n");
		statsText.append("  gold:\t" + Player.gold + "\n");
		statsText.append("\n  HEALTH:\t" + Player.maxHp + " / " + Player.hp);
		statsText.append("\n  STRENGTH:\t" + Player.strength);
		statsText.append("\n  AGILITY:\t" + Player.agility);
		statsText.append("\n  INT:\t" + Player.inteligence + "\n");
		statsText.append("\n  ARMOR\t" + "WEAPON\n");
		statsText.append("  " + Player.armour.name + "\t" + Player.weapon.name + "\n");
		statsText.append("  " + (Player.armour.armour + Player.weapon.armour) + "\t(" + (Player.weapon.minDmg + Player.armour.minDmg) 
				+ "-" + (Player.weapon.maxDmg + Player.armour.maxDmg) + ")");
	}

	// sets buttons for the inventory
	private static void populateInv() {
		for (int i = 0; i < invButton.length; i++) {
			final int j = i;
			invButton[i] = new JButton();
			invButton[i].setPreferredSize(new Dimension(30, 30));
			invButton[i].setEnabled(false);
			inventory.add(invButton[i]);

			invButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					invClick(j);
				}
			});
		}
		addPotion();
		Support.refresh();
	}

	public static void invClick(int i) {
		if (i == 0) {
			if (Player.HPPotions > 0) {
				Player.modifyHP(30);
				Player.HPPotions -= 1;
				if (Player.HPPotions == 0) {
					invButton[0].setEnabled(false);
				}
				Commands.testCommand(3);
				Commands.print("You drink a health potion restoring 30 health points.");
			} else
				Commands.print("There are no more potions left.");
		} else {
			setClickedItem(possesedItems.get(i));
		}
		Support.refresh();
	}

	private static void addPotion() {
		invButton[0].setIcon(new Picture("misc/healthPotion2.png", 50, 50).scaledPic);
		invButton[0].setHorizontalTextPosition(JButton.CENTER);
		invButton[0].setVerticalTextPosition(JButton.CENTER);
		invButton[0].setText(Integer.toString(Player.HPPotions));
		invButton[0].setFont(invButton[0].getFont().deriveFont(12.0f));
		invButton[0].setEnabled(true);
		invButton[0].setToolTipText("adds 30HP");
	}

	static int nextEmptyButton() {
		int buttonPos = -1;
		for (int i = 1; i < invButton.length; i++) {
			if (!invButton[i].isEnabled()) {
				buttonPos = i;
				break;
			}
		}

		return buttonPos;
	}

	public static boolean addItem(String ID) {
		Item item = new Item(ID);
		boolean added = false;
		if (!possesedItems.contains(ID)) {
			int nextEmpty = nextEmptyButton();
			if (nextEmpty != -1) {
				invButton[nextEmpty].setIcon(new Picture(item.url, invButton[nextEmpty].getWidth(),
						invButton[nextEmpty].getHeight()).scaledPic);
				invButton[nextEmpty].setEnabled(true);
				possesedItems.add(ID);
				added = true;
			}
		}
		return added;
	}

	private static void setClickedItem (String ID) {
		if (Integer.parseInt(ID)/100 == 1) {	
		Player.weapon = new Item(ID);
		}
		if (Integer.parseInt(ID)/200 == 1) {
		Player.armour = new Item(ID);
		}
	}
}

import java.awt.Image;

import javax.swing.ImageIcon;

public class Support {
	
	// returns random number between the given min-max
	public static int random (int min, int max) {
		int random =0;
		if (min>max) {
			int temp = min;
			min = max;
			max = temp;			
		}
		double randomDouble = min + Math.random()*(max - min);
		random = (int) Math.round(randomDouble);
		return random;
	}
	
	// freezes processing for given amount of milliseconds
	public static void pause(int miliSec) {
		try {
			Thread.sleep(miliSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// refreshes the state of the pictureWindow, healthBar, enemy, player,
	public static void refresh() {
		TheDungeon.health.setValue(Player.hp);
		TheDungeon.health.setString(Player.hp + " / " + Player.maxHp);
		TheDungeon.enemyHealth.setValue(TheDungeon.enemy.hp);
		TheDungeon.enemyHealth.setString(TheDungeon.enemy.hp + " / " + TheDungeon.enemy.maxHp);
		PlayerTab.statsUpdate();
		PlayerTab.invButton[0].setText(Integer.toString(Player.HPPotions));
		if (Player.HPPotions > 0) {
			PlayerTab.invButton[0].setEnabled(true);
		}
		EnemyTab.statsUpdate();
		Tabs.buttonsUpdate();
		Picture.pictureUpdate();
		TheDungeon.pictureWindow.revalidate();
		TheDungeon.pictureWindow.repaint();
	}
	
	// progresses the player. Sends the player either to the next fight or to treasure
	public static void progress() {
		if (random(0, 100)<= TheDungeon.LOOT_CHANCE + Player.inteligence - 10) {
			Commands.print("You enter the next chamber fully expecting to be jumped by another dungeon dweller. "
					+ "Instead you feast your eyes on a beatiful \nornamental chest. It must be hiding somesthing usefull");
			TheDungeon.state = 2;
			refresh();
		} else {
			TheDungeon.state = 1;
			TheDungeon.enemy = getEnemy();
			Enemy.healthBar();
			Commands.print(TheDungeon.enemy.enemyText);
			refresh();
		}
	}
	public static Enemy getEnemy() {
		return getEnemy("");
	}
	// returns new enemy based on current level
	public static Enemy getEnemy(String name) {
		TheDungeon.state = 1;
		Enemy enemy = null;
		int enemyType;
		switch (TheDungeon.dungeonLevel) {
		case 0:
		case 1: enemyType = 1;
			break;
		case 2:
		case 3: enemyType = random(1, 2);
			break;
		case 4: enemyType = random(1, 3);
			break;
		case 5:	enemyType = random(2, 3);
			break;
		case 6: enemyType = random(2, 4);
			break;
		case 7: enemyType = random(3, 4);
			break;
		case 8: enemyType = random(3, 5);
		break;
		case 9: enemyType = random(4, 5);
		break;
		case 10: enemyType = 6;
		break;
		default: enemyType = 1;
		}
		
		if (name.equalsIgnoreCase("Mimic")) enemyType = 7;
		
		switch (enemyType) {
		case 1: enemy = new Goblin();
			break;
		case 2: enemy = new Troll();
			break;
		case 3: enemy = new Zombie();
			break;
		case 4: enemy = new Vampire();
			break;
		case 5: enemy = new Ogre();
			break;
		case 6: enemy = new Boss();
			break;
		case 7: enemy = new Mimic();
			break;
		}
		return enemy;
	}
	
	public static ImageIcon scale(ImageIcon original, int width, int height) {
		ImageIcon pic = original;
		ImageIcon scaled = new ImageIcon(pic.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return scaled;
	}
}

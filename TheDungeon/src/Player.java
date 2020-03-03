import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class Player {
	static int maxHp = 100;
	static int hp = 100;
	static int strength = 10;
	static int agility = 10;
	static int inteligence = 10;
	static Item weapon = new Item("900");
	static Item armour = new Item("901");
	static int level = 1;
	static int defeatedEnemies = 0;
	static int armourVal = 0;
	static int exp = 0;
	static int gold = 0;
	static int attributePoints = 0;
	static int HPPotions = TheDungeon.START_POTIONS;
	static int openedChests = 0;
	
	public static void modifyHP(int change) {
		hp+=change;
		if (hp> maxHp) hp = maxHp;
		if (hp<= 0) {
			hp = 0;
			TheDungeon.state = 3;
			}
	}
	
	public static int attack() {
		int baseAttack = strength + (Support.random(0, strength));
		int attack = baseAttack + Support.random(weapon.minDmg + armour.minDmg, weapon.maxDmg + armour.maxDmg);
		return attack;
	}
	
	public static void healthBar() {
       	TheDungeon.health.setBorder(TheDungeon.border);
       	TheDungeon.health.setMinimum(0);
       	TheDungeon.health.setMaximum(Player.maxHp);
       	TheDungeon.health.setValue(Player.hp);
       	TheDungeon.health.setForeground(Color.RED);
       	TheDungeon.health.setBackground(Color.WHITE);
       	TheDungeon.health.setSize(50, 100);
       	TheDungeon.health.setStringPainted(true);
       	TheDungeon.health.setString(Player.hp + " / " + Player.maxHp);
       	TheDungeon.healthPanel.setLayout(new BorderLayout());       	
       	TheDungeon.healthPanel.add(TheDungeon.health, BorderLayout.CENTER);
       	TheDungeon.healthPanel.setPreferredSize(new Dimension(50, 50));
	}

	// gains XP based on the killed monster, level up if applicable
	public static void xpGain(int xp) {
		exp = exp + xp;
		if (exp>=nextLvl()) {
			exp -= nextLvl();
			level +=1;
			Commands.print("YOU HAVE LEVELLED UP! You are now level " + level);
			Player.hp = Player.maxHp;
			attributePoints+=2;
			for (int i=0; i<PlayerTab.statButtonArray.length; i++) {
				PlayerTab.statButtonArray[i].setVisible(true);
			}
		}
	}
	
	// returns required xp to level up
	public static int nextLvl() {
		if (level == 1) return 100;
		else return 100 + level*level*2;
	}
}

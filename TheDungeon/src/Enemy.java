import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public abstract class Enemy {
	int hp;
	int maxHp;
	int attack;
	int latestAttack;
	int gold;
	int xp;
	int agility;
	int lootChance;
	String name = "unknown";
	String enemyText = null;
	String imgUrl = null;
	
	public static void healthBar() {
	TheDungeon.enemyHealth.setBorder(TheDungeon.border);
	TheDungeon.enemyHealth.setMinimum(0);
	TheDungeon.enemyHealth.setMaximum(TheDungeon.enemy.maxHp);
	TheDungeon.enemyHealth.setValue(TheDungeon.enemy.hp);
	TheDungeon.enemyHealth.setForeground(new Color(140,52,52));
	TheDungeon.enemyHealth.setBackground(Color.WHITE);
	TheDungeon.enemyHealth.setSize(50, 100);
	TheDungeon.enemyHealth.setStringPainted(true);
	TheDungeon.enemyHealth.setString(TheDungeon.enemy.hp + " / " + TheDungeon.enemy.maxHp);
	TheDungeon.enemyHealthPanel.setLayout(new BorderLayout());       	
	TheDungeon.enemyHealthPanel.add(TheDungeon.enemyHealth, BorderLayout.CENTER);
	TheDungeon.enemyHealthPanel.setPreferredSize(new Dimension(50, 50));
	}
}

class Placeholder extends Enemy{
	public Placeholder() {
	hp = 0;
	maxHp = 0;
	attack = 0;
	gold = 0;
	xp = 0;
	agility = 0;
	lootChance = 0;
	name = "";
	enemyText = "";
	imgUrl = "";
	}
}

class Mimic extends Enemy{
	public Mimic() {
	hp = 250;
	maxHp = 250;
	attack = 30;
	gold = Support.random(50, 500);
	xp = 200 + Support.random(-20, 20);
	agility = 20;
	lootChance = 100;
	name = "Mimic";
	enemyText = "This is not a normal chest! THIS IS NOT A NORMAL CHEST!";
	imgUrl = "resources\\monsters\\mimic.jpg";
	}
}

class Goblin extends Enemy{
	public Goblin() {
	hp = 50;
	maxHp = 50;
	attack = 8;
	gold = Support.random(5, 20);
	xp = 40 + Support.random(-5, 5);
	agility = 9;
	lootChance = 20; 
	name = "Goblin";
	enemyText = "Initially you thought that the creature that you saw bent over in the corner of the "
			+ "chamber was one of the children that vanished\nfrom the village. The unnatural hiss "
			+ "and glimmer of your torch light reflected in the goblins eyes soon show you how wrong you were.";
	imgUrl = "resources\\monsters\\goblin.jpg";
	}
}

class Troll extends Enemy{
	public Troll() {
	hp = 140;
	maxHp = 140;
	attack = 14;
	gold = Support.random(15, 50);
	xp = 70 + Support.random(-10, 10);
	agility = 10;
	lootChance = 25;
	name = "Troll";
	enemyText = "Previously faint, the stench that you smelled ever since entering the dungeon"
			+ " starts to be more and more overwhelming.\nIt doesn't take you long to pinpoint the source";
	imgUrl = "resources\\monsters\\troll.jpg";
	}
}

class Zombie extends Enemy{
	public Zombie() {
	hp = 250;
	maxHp = 250;
	attack = 12;
	gold = Support.random(20, 70);
	xp = 100 + Support.random(-10, 10);
	agility = 10;
	lootChance = 25;
	name = "Zombie";
	enemyText = "You have gotten used to the sight of corpses lying around the place. Walking ones... not so much.";
	imgUrl = "resources\\monsters\\zombie.jpg";
	}
}

class Vampire extends Enemy{
	public Vampire() {
	hp = 150;
	maxHp = 150;
	attack = 30;
	gold = Support.random(50, 100);
	xp = 150 + Support.random(-10, 10);
	agility = 15;
	lootChance = 30;
	name = "Vampire";
	enemyText = "You feel something lurking in the darkness. Suddenly a shadowy figure emerges from the dark.";
	imgUrl = "resources\\monsters\\vampire.jpg";
	}
}

class Ogre extends Enemy{
	public Ogre() {
	hp = 300;
	maxHp = 300;
	attack = 25;
	gold = Support.random(100, 200);
	xp = 250 + Support.random(-20, 20);
	agility = 18;
	lootChance = 40;
	name = "Ogre";
	enemyText = "Deep grunt briefly fills the dungeons stale air. It seems that you have been spotted. Heavy footsteps soon shake the whole area.";
	imgUrl = "resources\\monsters\\ogre.jpg";
	}
}

class Boss extends Enemy{
	public Boss() {
	hp = 1000;
	maxHp = 1000;
	attack = 35;
	gold = Support.random(1000, 5000);
	xp = 0;
	agility = 100;
	lootChance = 0;
	name = "Warlock";
	enemyText = "It seems that you finally meet the puppet master standing behind all this. ";
	imgUrl = "resources\\monsters\\boss.jpg";
	TheDungeon.bossFight = true;
	}
}

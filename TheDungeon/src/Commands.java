import javax.swing.JOptionPane;

public class Commands {

	static JOptionPane winPane = new JOptionPane();
	
	// every array represents a command, rows represent state
									//	0enter	1attack 2flee 	3potion 4leave 	5take 	6open 	7inspect
	static boolean[][] isValidMatrix = {{true,	false, 	false, 	true, 	false, 	false, 	false, 	false }, // 		0 entrance
										{false,	true, 	true, 	true, 	false, 	false, 	false, 	false }, // 		1 fight
										{false,	false, 	false, 	true, 	true, 	true, 	true, 	false }, // 		2 treasure
										{false,	false, 	false, 	false, 	false, 	false, 	false, 	false }, //	 		3 death
										{true, 	false, 	false, 	true, 	true, 	false, 	true, 	false }, // 		4 doorway
										{false,	false, 	false, 	true, 	true, 	true, 	true, 	false }, // 		5 loot
										{false,	false, 	false, 	false, 	false, 	false, 	false, 	false } }; // 		6 endgame

	public static void userCommand(String command) {
		String upperCommand = command.toUpperCase().strip();
		switch (upperCommand) {

		case "ENTER": // used for entering the dungeon or new chambers
		case "GO IN":
		case "WALK IN":
			if (testCommand(0)) {
				if (TheDungeon.state == 0) {
					TheDungeon.dungeonLevel = 1;
					TheDungeon.mainWindow.setTitle("The Dungeon - level 1");
					print("The temperature drops significantly as you enter. You have a "
							+ "feeling like something focusing it's evil attention on you\n");
						Support.pause(200);
						EnemyTab.addEnemyTab();
						Support.refresh();
						TheDungeon.enemyTab.setVisible(true);
						TheDungeon.tabs.setSelectedIndex(2);

				} else {
					print("You have entered the chamber...");
					TheDungeon.state = 1;
				}
				Support.progress();
			}
			break;

		case "FIGHT": // attack an opponent
		case "ATTACK":
		case "HIT":
			if (testCommand(1)) {
				int playerAttack = Player.attack();
				if (TheDungeon.enemy.hp >= playerAttack)
					TheDungeon.enemy.hp -= playerAttack;
				else {
					TheDungeon.enemy.hp = 0;
				}
				print("You have attacked the " + TheDungeon.enemy.name + " dealing " + playerAttack + " damage.");
				if (TheDungeon.enemy.hp > 0) {
					int enemyAttack = attack();
					if (enemyAttack > Player.armourVal) {
						Player.modifyHP(-enemyAttack + Player.armourVal);
						print(TheDungeon.enemy.name + " fought back dealing " + (enemyAttack - Player.armourVal)
								+ " damage.");
						if (Player.hp <= 0)
							print("You tried your best but you were no match for the "
									+ "creatures in the dungeon.\nSoon your body will raise again and "
									+ "join the dark army hiding in the depths."); // <-- death comment
					} else
						print("The monster attacks back but barely scratched your armour"); // <-- armour > enemy attack
				} else {
					enemyDefeated(TheDungeon.enemy.lootChance);
				}
			}
			Support.refresh();
			break;

		// try to run from fight. Successful escape based on player agility and enemy agility
		case "FLEE":
		case "RUN":
			if (testCommand(2)) {
				TheDungeon.actionArea.append("You have decided to run for it! ");
				if (TheDungeon.bossFight == true) {print("There is no way out. You have to face this monster.");
				} else if (Player.agility + Support.random(-5, 5) >= TheDungeon.enemy.agility) {
					TheDungeon.actionArea.append("Thanks to your agility you managed " + "to outrun the "
							+ TheDungeon.enemy.name + ". Once his footsteps "
							+ "can no longer be heard \nthrough the corridor you start "
							+ "to wander through the halls again.\n");
					TheDungeon.state = 4;
					TheDungeon.enemy = new Placeholder();
					
				} else {
					int damage = attack();
					TheDungeon.actionArea.append(" You are fast, but the enemy is faster. "
							+ "Your run attempt only left you voulnerable to the beasts attack!\n" + "You took "
							+ damage + " points of damage");
					Player.modifyHP(-damage);
				}
			}
			Support.refresh();
			break;

		case "POTION":
		case "HEAL":
			if (testCommand(3))
				PlayerTab.invClick(0);
			break;

		case "LEAVE":
		case "ESCAPE":
		case "EXIT":
			if (testCommand(4)) {
				print("You are drained, tired and banged up. It is time to go back. You just hope that you "
						+ "will be able to find your way back.\n");
				if (escape()) {
					print("You have managed to navigate the dungeon all the way up to the surface.");
					TheDungeon.dungeonLevel = 0;
					TheDungeon.state = 6;
					int score = Player.gold + Player.defeatedEnemies*20 + Player.openedChests*50 + Player.HPPotions*50;
					String winMessage = "CONGRATULATIONS\n You have managed to escape from the dungeon.\n Your final score is: " + score;
					winPane.showMessageDialog(TheDungeon.mainWindow, winMessage);
					Support.refresh();
				}
			}
			break;

		case "LOOT":
		case "SEARCH":
		case "GRAB":
		case "TAKE":
		case "OPEN":
			if (testCommand(5)) {
				if (TheDungeon.state == 5) {
					print("You search through the " + TheDungeon.enemy.name + "s remains and find "
							+ TheDungeon.enemy.gold + " gold");
					Player.gold += TheDungeon.enemy.gold;
					TheDungeon.state = 4;
				}
				if (TheDungeon.state == 2) {
					Player.openedChests +=1;
					TheDungeon.state = 4;
					lootChest();
				}
				Support.pause(200);
				Support.refresh();
			}
			break;

		case "DEBUG": // <--- commands testing
			PlayerTab.addItem("101");
			PlayerTab.addItem("102");
			PlayerTab.addItem("103");
			PlayerTab.addItem("104");
			PlayerTab.addItem("105");
			PlayerTab.addItem("106");
			PlayerTab.addItem("107");
			PlayerTab.addItem("108");
			PlayerTab.addItem("109");
			PlayerTab.addItem("110");
			PlayerTab.addItem("201");
			PlayerTab.addItem("202");
			PlayerTab.addItem("203");
			PlayerTab.addItem("204");
			PlayerTab.addItem("205");
			break;

		default:
			print("You feel confused");
		}
	}

	private static void enemyDefeated(int lootChance) {
		if (TheDungeon.bossFight == true) {
			print ("You did it! You have defeated the demon responsible for the horrible fate of the dwellers of this place!\n"	// <-- defeat the boss
					+ "THANK YOU FOR PLAYING The Dungeon!");
			TheDungeon.state = 6;
			int score = Player.gold + Player.defeatedEnemies*20 + Player.openedChests*50 + Player.HPPotions*50;
			String winMessage = "CONGRATULATIONS\n You have managed to Defeat the evil warlock. Thank you for playing the game.\n Your final score is: " + score;
			winPane.showMessageDialog(TheDungeon.mainWindow, winMessage);
			print("\nFinal score: " + score);
			Support.refresh();
		} else {
			print("Your foe lies dead at your feet. Another vermin removed from the face of the earth."
				+ " You have gained " + TheDungeon.enemy.xp + " experience"); // <-- fight win comment
			Player.xpGain(TheDungeon.enemy.xp);
			Player.defeatedEnemies += 1;
			TheDungeon.setLevel();
			if (lootChance >= Support.random(0, 100)) {
				TheDungeon.state = 2;
			} else {
				TheDungeon.state = 5;
			}
			Support.refresh();
		}
	}

	private static void lootChest() {
		// test for mimic based on dungeon level
		if (Support.random(1, 20) > (TheDungeon.dungeonLevel - 1) && TheDungeon.enemy != null
				&& !TheDungeon.enemy.name.equals("Mimic")) {
			TheDungeon.state = 1;
			TheDungeon.enemy = Support.getEnemy("MIMIC");
			Enemy.healthBar();
		} else {
			Item.chestBounty();
		}
	}

	// dungeon escape chances
	private static boolean escape() {
		
			if (Player.inteligence + Support.random(-10, 10) >= TheDungeon.dungeonLevel*2 + 10) { // test for escape
				TheDungeon.dungeonLevel = 0;
				print("You have escaped the dungeon!");
				
				return true;
		} else {
			print("You are utterly lost. What is worse, it seems that you have wandered into "
					+ "a part of the maze you should not be in...");
			TheDungeon.state = 1;
			TheDungeon.enemy = Support.getEnemy();
			Enemy.healthBar();
			return false;
		}
	}

	// unlock the command if valid
	public static boolean testCommand(int commIndex) {
		if (isValid(commIndex)) {
			Tabs.commandButton[commIndex].setVisible(true);
			Tabs.commandButton[commIndex].revalidate();
			Tabs.commandButton[commIndex].repaint();
			return true;
		} else {
			print("You feel confused");
			return false;
		}
	}

	// checks if the command is valid. Based on boolean[][] isValid
	public static boolean isValid(int commIndex) {
		if (commIndex == 3 &&Player.HPPotions <1) {
			return false;
		} else 
			return isValidMatrix[TheDungeon.state][commIndex];
	}

	// print out text on the action area
	static void print(String text) {
		TheDungeon.actionArea.append(text + "\n");
	}
	
	public static int attack() {
		int damage = (TheDungeon.enemy.attack/2) + (Support.random(0, TheDungeon.enemy.attack));
		return (damage);
	}
}

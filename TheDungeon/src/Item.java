import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Item {

	private static String ItemFileUrl = "src\\resources\\ItemID.txt";
	private static File itemFile = new File(ItemFileUrl);
	String name;
	int minDmg;
	int maxDmg;
	int armour;
	int rarity;
	String url;
	static String line;
	static String[] itemList;
	static Scanner scanner = null;
	// 0 1 2 3 4 5 6 7 8 9 dungeon level
	static int[][] lootChance = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // not available
			{ 1, 100, 80, 50, 30, 0, 0, 0, 0, 0, 0 }, // common
			{ 0, 20, 50, 80, 100, 60, 40, 10, 0, 0, 0 }, // rare
			{ 0, 0, 0, 20, 50, 60, 100, 80, 40, 10, 0 }, // very rare
			{ 0, 0, 0, 0, 0, 5, 10, 20, 40, 100, 0 } }; // unique

	public Item(String ID) {

		itemList = getItemlist();
		line = lineLoader(ID);
		name = getName(ID);
		minDmg = getMinDmg(ID);
		maxDmg = getMaxDmg(ID);
		armour = getArmor(ID);
		rarity = getRarityType(ID);
		url = getUrl(ID);
		Player.armourVal = armour;
	}

	private static int getMinDmg(String ID) {
		line = lineLoader(ID);
		return Integer.parseInt(line.substring(line.indexOf("min:") + 4, line.indexOf("min:") + 6));
	}

	private static int getMaxDmg(String ID) {
		line = lineLoader(ID);
		return Integer.parseInt(line.substring(line.indexOf("max:") + 4, line.indexOf("max:") + 6));
	}

	private static int getArmor(String ID) {
		line = lineLoader(ID);
		return Integer.parseInt(line.substring(line.indexOf("armor:") + 6, line.indexOf("armor:") + 8));
	}

	public static int getRarityType(String ID) {
		line = lineLoader(ID);
		return Integer.parseInt(line.substring(line.indexOf("rarity:") + 7, line.indexOf("rarity:") + 8));
	}

	private static String getName(String ID) {
		line = lineLoader(ID);
		return line.substring(line.indexOf("name:") + 5, line.indexOf("min:"));
	}

	private static String getUrl(String ID) {
		line = lineLoader(ID);
		return line.substring(line.indexOf("url:") + 4, line.length());
	}

	public static String[] getItemlist() {

		int numberOfItems = 0;
		scanner = getScanner();
		while (scanner.hasNextLine()) {
			scanner.nextLine();
			numberOfItems += 1;
		}
		scanner.close();
		String[] itemList = new String[numberOfItems];
		scanner = getScanner();

		for (int i = 0; i < numberOfItems; i++) {
			itemList[i] = scanner.nextLine().substring(3, 6);

		}
		scanner.close();
		return itemList;
	}

	private static String lineLoader(String ID) {
		String line = null;
		String key = "ID:" + ID;
		Scanner scanner = getScanner();
		while (scanner.hasNextLine()) {
			if (scanner.hasNext(key)) {
				line = scanner.nextLine();
			}
			if (scanner.hasNextLine())
				scanner.nextLine();
		}
		scanner.close();
		return line;
	}

	private static Scanner getScanner() {
		try {
			scanner = new Scanner(itemFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return scanner;
	}

	// gets the rarity type of item based on ID
	public static int itemRarity(String ID) {
		int rarityType = getRarityType(ID);
		int rarity = lootChance[rarityType][TheDungeon.dungeonLevel];
		return rarity;
	}

	// returns item ID based on the weight of the items for that dungeon level
	public static String getLoot() {
		String lootID = "000";
		int lootPool = 0;

		for (int i = 0; i < itemList.length; i++) {
			lootPool += itemRarity(itemList[i]);
		}
		int randomChance = Support.random(1, lootPool);
		for (String ID : itemList) {
			randomChance -= itemRarity(ID);
			if (randomChance <= 0) {
				lootID = ID;
				break;
			}
		}

		return lootID;
	}

	// calculates the chance to get an item based on int (typically enemyLootChance)
	public static void chestBounty() {
			String item = getLoot();
			int gold = Support.random(50, 200);
			Commands.print("The chest contains " + gold + " gold pieces.");
			Player.gold += gold;
			Commands.print("You search through the treasure and find a " + getName(item) + ".");
			
			if (item.equals("304") || item.equals("303") || item.equals("302") || item.equals("301")) {
				Player.HPPotions += 1;
			} else {
				if (PlayerTab.possesedItems.contains(item)) {
					Commands.print(
					"You dig deeper and also find a health potion.");
					Player.HPPotions+=1;
				} else {
					PlayerTab.addItem(item);
				}
			}
		
		Support.refresh();
	}
}

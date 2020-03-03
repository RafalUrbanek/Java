import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class Potion {
	int type;
	int hp;
	int rarity;
	String picUrl;
	ImageIcon icon;
	JButton button = new JButton("test");
	
	public static ImageIcon scale(ImageIcon original) {
		ImageIcon pic = original;
		ImageIcon scaled = new ImageIcon(pic.getImage().getScaledInstance(48, 48, Image.SCALE_DEFAULT));
		return scaled;
	}
	
	public JButton getPotion() {
		ImageIcon potionIcon = scale(icon);
		JButton potionButton = new JButton();
		potionButton.setIcon(potionIcon);
		return potionButton;
	}
}

class HealthSmall extends Potion{
	public HealthSmall() {
		type = 0;
		hp = 30;
		rarity = 20;
		picUrl = "src\\resources\\healthPotion2.png";
		icon = new ImageIcon(picUrl);
	}
}

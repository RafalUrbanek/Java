package res;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import main.TheDungeon;


public class Picture extends ImageIcon {

	static JLabel picLabel = new JLabel();
	static int pictureIndex = 0;
	public ImageIcon scaledPic;
	BufferedImage image;
	BufferedImage scaledImage;
	
	public Picture () {
		try {
			image = ImageIO.read(ResourceLoader.load(picURL()));
		} catch (IOException e) {}
		
		scaledPic = new ImageIcon(image.getScaledInstance(TheDungeon.DEF_PIC_WIDTH,+
				TheDungeon.DEF_PIC_HEIGHT, Image.SCALE_DEFAULT));
		picLabel.setIcon(scaledPic);
	}
	
	public Picture (String url, int width, int height) {
		try {
			image = ImageIO.read(ResourceLoader.load(url));
		} catch (IOException e) {}
		
		scaledPic = new ImageIcon(image.getScaledInstance(width,+
				height, Image.SCALE_DEFAULT));
		picLabel.setIcon(scaledPic);
	}
	public JLabel getPictureLabel() {
		return picLabel;
	}
	
	private static String picURL() {
		switch(TheDungeon.state) {
		
			//entrance pic
		case 0: 
			return "misc/cave entrance.jpg";
			
			//enemy pic
		case 1: 
			return TheDungeon.enemy.imgUrl;
			
			// treasure pic
		case 2: 
			return "misc/treasure.jpg";
			
			// death pic
		case 3: 
			return "misc/dead hero.jpg";
			
			//doorway pic
		case 4: 
			return "misc/door.jpg";
			
			// loot pic
		case 5: 
			return "misc/treasure 2.JPG";
			
			// endgame
		case 6: 
			return "misc/the escape_2.jpg";
			
		default: 
			System.out.println("Incorrect URL in picURL or missing picture");
			return "null";
		}
	}
	
	public static void pictureUpdate() {
		new Picture();
	}
}

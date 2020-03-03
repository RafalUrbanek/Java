import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// required pictures: MIMIC, DOORWAY

public class Picture extends ImageIcon {

	static JLabel picLabel = new JLabel();
	static int pictureIndex = 0;
	ImageIcon pic, scaledPic;
	
	public Picture () {
		pic = new ImageIcon(this.getClass().getResource(PicURL()));
		scaledPic = new ImageIcon(pic.getImage().getScaledInstance(TheDungeon.DEF_PIC_WIDTH,+
				TheDungeon.DEF_PIC_HEIGHT, Image.SCALE_DEFAULT));
		picLabel.setIcon(scaledPic);
	}
	public static JLabel getPicture() {
		return picLabel;
	}
	
	private static String PicURL() {
		switch(TheDungeon.state) {
		
			//entrance pic
		case 0: 
			return "resources//cave entrance.jpg";
			
			//enemy pic
		case 1: 
			return TheDungeon.enemy.imgUrl;
			
			// treasure pic
		case 2: 
			return "resources//treasure.jpg";
			
			// death pic
		case 3: 
			return "resources//dead hero.jpg";
			
			//doorway pic
		case 4: 
			return "resources//door.jpg";
			
			// loot pic
		case 5: 
			return "resources//treasure 2.JPG";
			
			// endgame
		case 6: 
			return "resources//the escape_2.jpg";
			
		default: 
			System.out.println("Incorrect URL or missing picture");
			return "null";
		}
	}
	
	public static void pictureUpdate() {
		new Picture();
	}
}

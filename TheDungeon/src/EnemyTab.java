import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class EnemyTab extends JPanel {
	
	JLabel stats = new JLabel();
	JLabel inventory = new JLabel();
	static JTextArea statsText = new JTextArea();
	JProgressBar health = new JProgressBar();
	
	Border standardMargin = BorderFactory.createMatteBorder(10, 10, 10, 10, TheDungeon.defaultColor);
   	Border standardBorder = BorderFactory.createRaisedBevelBorder();
   	Border border = BorderFactory.createCompoundBorder(standardMargin, standardBorder);
	
	public EnemyTab() {
		
		setLayout(new BorderLayout());
		statsUpdate();
		statsText.setBackground(TheDungeon.defaultColor);
		statsText.setBorder(border);
		stats.setBorder(border);
		stats.add(statsText);
		statsText.setEditable(false);
		
		inventory.setBorder(border);
		add(statsText, BorderLayout.NORTH);
		add(inventory, BorderLayout.SOUTH);
	}
	
	public static void statsUpdate() {
		statsText.setText("");
		statsText.append(TheDungeon.enemy.name.toUpperCase());
		statsText.append("\nHEALTH:\t" + TheDungeon.enemy.maxHp + " / " + TheDungeon.enemy.hp);
		statsText.append("\nSTRENGTH:\t" + TheDungeon.enemy.attack);
	}
	
	public static void addEnemyTab() {
		TheDungeon.enemyTab = new EnemyTab();
		TheDungeon.tabs.addTab("enemy", TheDungeon.enemyTab);
	}
}

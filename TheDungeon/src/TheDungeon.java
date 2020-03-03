import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class TheDungeon {
	static JFrame mainWindow = new JFrame("The Dungeon - entrance");
	JPanel centralWindow = new JPanel();
	static JPanel pictureWindow = new JPanel();
	JPanel bottomMenu = new JPanel();
	Tabs commandsTab = new Tabs();
	PlayerTab playerTab = new PlayerTab();
	static EnemyTab enemyTab;
	static JTabbedPane tabs = new JTabbedPane();
	static JTextArea actionArea = new JTextArea();
	JTextField inputArea = new JTextField();
	Dimension textPaneSize = new Dimension(100, 250);
	Dimension inputPaneSize = new Dimension(20, 50);
	JButton submit = new JButton("  submit  ");
	String userInput = "";
	static int state = 0;
	JScrollPane actionScrollPane = new JScrollPane(actionArea);
	static JProgressBar health = new JProgressBar(SwingConstants.VERTICAL);
	static JProgressBar enemyHealth = new JProgressBar(SwingConstants.VERTICAL);
	static JPanel healthPanel = new JPanel();
	static JPanel enemyHealthPanel = new JPanel();
	static Picture mainPicture = new Picture();
	static Enemy enemy = new Placeholder();
	static int dungeonLevel = 0;
	static boolean bossFight = false;
	
	// constants
	final static int LOOT_CHANCE = 50; 	// % chance to find treasure chest in room
	final static int WINDOW_WIDTH = 900;
	final static int WINDOW_HEIGHT = 865;
	final static int START_POTIONS = 5;	// how many health potions the player starts with
	
	// command array index:			 0			1		2		3	      4			5		6(not used)
	static String[] commandList = {"enter", "attack", "flee", "potion", "leave", "take", "inspect"};
	
	// default values
	static final int DEF_PIC_WIDTH = 480;
	static final int DEF_PIC_HEIGHT = 505;
	static Color defaultColor = new Color(238,238,238);
	
	//borders
		static Border standardMargin = BorderFactory.createMatteBorder(10, 10, 10, 10, defaultColor);
       	static Border standardBorder = BorderFactory.createRaisedBevelBorder();
       	static Border border = BorderFactory.createCompoundBorder(standardMargin, standardBorder);
       	
	public TheDungeon() {

		Player.healthBar();
		enemy.healthBar();

       	//setting up tabs
       	tabs.addTab("commands", commandsTab);
       	tabs.addTab("player", playerTab);
        tabs.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 10, defaultColor));
       	tabs.setPreferredSize(new Dimension(280,200));
        tabs.setBackground(defaultColor);
        tabs.setFont(tabs.getFont().deriveFont(16.0f));
       	
       	actionScrollPane.setBorder(border);
       	actionScrollPane.setPreferredSize(textPaneSize);
       	actionArea.setEditable(false);
       	inputArea.setBorder(border);
       	inputArea.setPreferredSize(inputPaneSize);
       	inputArea.setFont(inputArea.getFont().deriveFont(18.0f));
       	
       	// ENTER key listener
       	inputArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                  submit.doClick();
               }
            }
         });
       	
       	// bottom menu setup
       	bottomMenu.setLayout(new BorderLayout());
       	bottomMenu.add(inputArea, BorderLayout.CENTER);
       	submit.setBorder(border);
       	bottomMenu.add(submit, BorderLayout.EAST);
       	
       	// submit button action
       	submit.addActionListener(new ActionListener() {
       		public void actionPerformed(ActionEvent e) {
       			actionArea.append(inputArea.getText().toUpperCase() + "\n");
       			Commands.userCommand(inputArea.getText());
       			inputArea.setText("");
       			Support.pause(200);
       			Tabs.buttonsUpdate();
       			Support.refresh();
            }
        });
       	
       	// Image display setup
		pictureWindow.setBounds(0, 0, DEF_PIC_WIDTH, DEF_PIC_HEIGHT);
		pictureWindow.setBorder(BorderFactory.createMatteBorder(18, 0, 0, 0, defaultColor));
		pictureWindow.add(mainPicture.getPicture());
		pictureWindow.setBackground(defaultColor);
			
			//centralWindow setup
		centralWindow.setLayout(new BorderLayout());
		centralWindow.setBackground(defaultColor);
		centralWindow.setBounds(100, 100, 600, 400);
		centralWindow.setMinimumSize(new Dimension(800, 800));
		centralWindow.add(pictureWindow, BorderLayout.CENTER);
		centralWindow.add(tabs, BorderLayout.EAST);
		centralWindow.add(actionScrollPane, BorderLayout.NORTH);
		centralWindow.add(bottomMenu, BorderLayout.SOUTH);
			
			//MainWindow setup
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		mainWindow.add(healthPanel, BorderLayout.WEST);
		mainWindow.add(enemyHealthPanel, BorderLayout.EAST);
		mainWindow.add(centralWindow, BorderLayout.CENTER);
		mainWindow.setResizable(false);
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Commands.print("Welcome to The Dungeon.\nThe entrance looks like it has been forgotten centuries "
				+ "ago. Thick overgrowth hides ancient runes and makes the place seem \nabbandoned, "
				+ "however you have heard enough rumours to know that something lurks in those murky "
				+ "depths. Will you dare to enter?\nType ENTER to venture into the darkness.\n");
	}

	public static int setLevel() {
		dungeonLevel = Player.defeatedEnemies/5 +1;
		if (dungeonLevel>=10) {
			dungeonLevel=10;
		mainWindow.setTitle("The Dungeon - Warlocks quarters");	
			}else 
		mainWindow.setTitle("The Dungeon - level " + dungeonLevel);
		return dungeonLevel;
	}
}
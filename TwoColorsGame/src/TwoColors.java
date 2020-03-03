import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

public class TwoColors extends JFrame {

	static boolean gameFinished = false;
	static int gridSize = 3;
	static int moves = 0;
	static GameTile[][] layout = new GameTile[7][7];
	static Popup winPopup;
	static JPanel gamePanel = new JPanel();
	static MenuButton reloadButton = new Reload("RELOAD");
	static GameTile gameTile;
	static JPanel menu = new JPanel();
	JComboBox gameSize = new GameSize();
	String[] arrayStrings= {"one","two","three"};
	Dimension dim = new Dimension(280, 320);
	JButton reload = new JButton("reload");
	static PopupFactory pf = new PopupFactory();
	static JPanel winPopupPanel = new JPanel();
	static JLabel winLabel = new JLabel();
	JLabel filler = new JLabel("     ");
	Font winFont = winLabel.getFont();
	static GridLayout gameLayout = new GridLayout(6,6);
	Insets margin = new Insets(3, 20, 3, 20);
	
	
	public TwoColors() {
		super("Two Colors");
		setLayout(new BorderLayout());
		populate();
			
		winLabel.setFont(new Font(winFont.getName(), Font.PLAIN, 15));
		winPopupPanel.add(winLabel);
		winPopupPanel.setBackground(Color.LIGHT_GRAY);
		winPopupPanel.setPreferredSize(new Dimension(300, 80));
		winPopupPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		
		menu.setLayout(new BorderLayout());
		reloadButton.setMargin(margin);
		menu.add(reloadButton);
		menu.add(gameSize, BorderLayout.EAST);
		
		add(gamePanel, BorderLayout.CENTER);
		add(menu, BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(dim);
		setSize(500,450);
		setVisible(true);
	}
	
		//populating the layout array
	public static void populate() {
		gameLayout.setColumns(gridSize+1);
		gameLayout.setRows(gridSize);
		gamePanel.setLayout(gameLayout);
		for (int i=0; i<gridSize; i++) {
			for (int j=0; j<gridSize+1; j++) {
				gameTile = new GameTile(i, j);
				layout[i][j]=gameTile;
				gamePanel.add(gameTile);
			}
		}
		gamePanel.revalidate();
		gamePanel.repaint();
	}
	
	public static void dePopulate() {
		for (int i=0; i<gridSize; i++) {
			for (int j=0; j<gridSize+1; j++) {
			gamePanel.remove(layout[i][j]);
			}
		}
	}
	
	// Action when tile is clicked
	public static void tileClick (int posX, int posY) {
		layout[posX][posY].reverseState();								// reverse clicked tile
		if (posX>0) {layout[posX-1][posY].reverseState();}				// reverse left tile
		if (posX<gridSize-1) {layout[posX+1][posY].reverseState();}
		if (posY>0) {layout[posX][posY-1].reverseState();}
		if (posY<gridSize) {layout[posX][posY+1].reverseState();}
		moves = moves + 1;
		testForWin();
	}
	
	// Test if the winning condition was met
		private static void testForWin() {
		test:
			for (int i=0; i<gridSize; i++) {
			for (int j=0; j<gridSize+1; j++) {
				if (layout[i][j].tileState == false) {
					break test;
				} if (i==gridSize-1 && j==gridSize && layout[gridSize-1][gridSize-1].tileState == true) {
					winLabel.setText("<html><center>CONGRATULATIONS<br>You have beat the "+ gridSize +  "x" + (gridSize+1) + 
							" matrix in " + moves + " moves</center>");
					winPopup = pf.getPopup(gamePanel, winPopupPanel, gamePanel.getWidth()/2-140, gamePanel.getHeight()/2);
					winPopup.show();
					gameFinished = true;
				}
			}
		}
	}

		
		public static void rePopulate() {
			if (winPopup != null) {
				winPopup.hide();
			}
			
			for (int i=0; i<gridSize; i++) {
				for (int j=0; j<gridSize+1; j++) {
				gamePanel.remove(layout[i][j]);
				gameTile = new GameTile(i, j);
				layout[i][j] = gameTile;
				gamePanel.add(gameTile);
			}
		}
			gamePanel.revalidate();
			gamePanel.repaint();
	}
}
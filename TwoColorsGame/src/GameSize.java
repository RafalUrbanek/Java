import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

class GameSize extends JComboBox {
	
	static String[] sizeLabels = {"2x3", "3x4", "4x5", "5x6", "6x7"};

	public GameSize () {
		this(sizeLabels);
	}
	
	public GameSize(String[] sizeLabels) {
		super(sizeLabels);
		JComboBox gameSizeBox = new JComboBox(sizeLabels);
		setSelectedIndex(1);
		addActionListener(this);
	}
	  public void actionPerformed(ActionEvent e) {
		  TwoColors.dePopulate();
		  String selection =(String) getSelectedItem();
		  switch(selection){
		  case "2x3":
			  TwoColors.gridSize=2;
			  break;
		  case "3x4":
			  TwoColors.gridSize=3;
			  break;
		  case "4x5":
			  TwoColors.gridSize=4;
			  break;
		  case "5x6":
			  TwoColors.gridSize=5;
			  break;
		  case "6x7":
			  TwoColors.gridSize=6;
			  break;
		  }
		  if (TwoColors.winPopup != null) {
				TwoColors.winPopup.hide();
			}
		  TwoColors.populate();
		  TwoColors.moves = 0;
		  TwoColors.gameFinished = false;
	  }
}
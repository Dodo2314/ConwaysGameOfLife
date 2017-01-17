package window;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.JButton;

public class GameWindow extends JPanel{
	
	private JButton[][] cellArray = new JButton[50][50];
	
	
	public GameWindow() {
		setupCellArray();
		setPreferredSize (new Dimension (1200, 1000));
		setLayout(null);
	}
	
	public void setupCellArray(){
		int scale = 20;
		int width = 20;
		int height = 20;
		for(int i = 0; i<cellArray.length; i++){
			for(int r = 0; r<cellArray[i].length; r++){
				
				cellArray[i][r] = new JButton("");
				cellArray[i][r].setBounds(i*scale, r*scale, width, height);
				add(cellArray[i][r]);
			}
		}
	}
}

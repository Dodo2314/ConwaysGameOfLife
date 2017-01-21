package window;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameWindow extends JPanel implements ActionListener{
	
	private JButton[][] bCellArray = new JButton[50][50];
	private JTextField tfPercentageLifeCells;
	private JTextField tfDelayPerStage;
	private JButton bRandomCells;
	private JLabel lblOfLive;
	private JButton bEvolveOneStage;
	private JButton bStart;
	private JButton bStop;
	private JButton bClear;
	private JButton bShowNeigbours;
	private JLabel lblStageDelaySeconds;
	private JLabel lblStage;
	private JLabel lblStageNr;
	
	private boolean[][] cellArrayStatus = new boolean[50][50]; 
	private int stage = 0;
	private int perLifeCells;
	private int delayPerStage;
	private boolean evolving = false;
	private boolean showNeigbourText = false;
	private Color defaultButtonColor = new JButton().getBackground();
	
	public GameWindow() {
		setupCellArray();
		setupOptions();
		setPreferredSize (new Dimension (1200, 1000));
		setLayout(null);
	}
	
	public void setupCellArray(){
		int scale = 20;
		int width = 20;
		int height = 20;
		for(int i = 0; i<bCellArray.length; i++){
			for(int r = 0; r<bCellArray[i].length; r++){
				
				bCellArray[i][r] = new JButton("");
				bCellArray[i][r].setBounds(i*scale, r*scale, width, height);
				bCellArray[i][r].setContentAreaFilled(false);
				bCellArray[i][r].setOpaque(true);
				bCellArray[i][r].setMargin(new Insets(0, 0, 0, 0));
				bCellArray[i][r].addActionListener(this);
				add(bCellArray[i][r]);
			}
		}
	}
	
	public void setupOptions(){
		bRandomCells = new JButton("Random Cells");
		bRandomCells.setBounds(1070, 11, 120, 23);
		bRandomCells.addActionListener(this);
		add(bRandomCells);
		
		tfPercentageLifeCells = new JTextField("33");
		tfPercentageLifeCells.setBounds(1070, 69, 120, 20);
		add(tfPercentageLifeCells);
		tfPercentageLifeCells.setColumns(10);
		
		lblOfLive = new JLabel("% of live Cells");
		lblOfLive.setBounds(1070, 45, 120, 14);
		add(lblOfLive);
		
		bEvolveOneStage = new JButton("Evolve 1 Stage");
		bEvolveOneStage.setBounds(1069, 100, 121, 23);
		bEvolveOneStage.addActionListener(this);
		add(bEvolveOneStage);
		
		bStart = new JButton("Start");
		bStart.setBounds(1069, 134, 57, 23);
		bStart.addActionListener(this);
		bStart.setMargin(new Insets(0, 0, 0, 0));
		add(bStart);
		
		bStop = new JButton("Stop");
		bStop.setBounds(1136, 134, 54, 23);
		bStop.addActionListener(this);
		bStop.setMargin(new Insets(0, 0, 0, 0));
		add(bStop);
		
		bClear = new JButton("Clear");
		bClear.setBounds(1069, 250, 121, 23);
		bClear.addActionListener(this);
		bClear.setMargin(new Insets(0,0,0,0));
		add(bClear);
		
		bShowNeigbours = new JButton("Show Neighbours");
		bShowNeigbours.setBounds(1069, 300, 121, 23);
		bShowNeigbours.addActionListener(this);
		bShowNeigbours.setMargin(new Insets(0,0,0,0));
		add(bShowNeigbours);
		
		lblStageDelaySeconds = new JLabel("Stage delay seconds");
		lblStageDelaySeconds.setBounds(1070, 168, 120, 14);
		add(lblStageDelaySeconds);
		
		tfDelayPerStage = new JTextField();
		tfDelayPerStage.setText("2");
		tfDelayPerStage.setBounds(1070, 193, 120, 20);
		add(tfDelayPerStage);
		tfDelayPerStage.setColumns(10);
		
		lblStage = new JLabel("Stage:");
		lblStage.setBounds(1070, 224, 46, 14);
		add(lblStage);
		
		lblStageNr = new JLabel("0");
		lblStageNr.setBounds(1126, 224, 46, 14);
		add(lblStageNr);
	}
	
	public void evolveOneStage(){
		evolving = true;
		int neigboursAm = 0;
		boolean[][] tempArray = cellArrayStatus;
		for(int i = 0; i<cellArrayStatus.length; i++){
			for(int r = 0; r<cellArrayStatus[i].length; r++){
				neigboursAm = getNeigbours(i, r);
				if(cellArrayStatus[i][r] == false && neigboursAm == 3){
					tempArray[i][r] = true;
				}else if(cellArrayStatus[i][r] && neigboursAm < 2){
					tempArray[i][r] = false;
				}else if(cellArrayStatus[i][r] && neigboursAm > 3){
					tempArray[i][r] = false;
				}
			}
		}
		cellArrayStatus = tempArray;
		updateBoard();
	}
	
	public int getNeigbours(int i, int r){
		int am = 0;
		int le = cellArrayStatus.length;
		if(i-1 >= 0 && r-1 >= 0){
			if(cellArrayStatus[i-1][r-1]){
				am++;
			}
		}
		if(i-1 >= 0){
			if(cellArrayStatus[i-1][r]){
				am++;
			}
		}
		if(i-1 >= 0 && r+1 < le){
			if(cellArrayStatus[i-1][r+1]){
				am++;
			}
		}
		if(r+1 < le){
			if(cellArrayStatus[i][r+1]){
				am++;
			}
		}
		if(i+1 < le && r+1 < le){
			if(cellArrayStatus[i+1][r+1]){
				am++;
			}
		}
		if(i+1 < le){
			if(cellArrayStatus[i+1][r]){
				am++;
			}
		}
		if(i+1 < le && r-1 >= 0){
			if(cellArrayStatus[i+1][r-1]){
				am++;
			}
		}
		if(r-1 >= 0){
			if(cellArrayStatus[i][r-1]){
				am++;
			}
		}
		return am;
	}
	
	public void start(){
	
	}
	
	public void createRandomCells(){
		perLifeCells = Integer.parseInt(tfPercentageLifeCells.getText());
		System.out.println(perLifeCells);
		for(int i = 0; i<cellArrayStatus.length; i++){
			for(int r = 0; r<cellArrayStatus[i].length; r++){
				if(randomInteger(1, 100) <= perLifeCells){
					cellArrayStatus[i][r] = true;
				}else{
					cellArrayStatus[i][r] = false;
				}
			}
		}
		updateBoard();
	}
	
	public void clearBoard(){
		for(int i = 0; i<cellArrayStatus.length; i++){
			for(int r = 0; r<cellArrayStatus[i].length; r++){
				cellArrayStatus[i][r] = false;
			}
		}
		evolving = false;
		updateBoard();
	}
	
	public void showNeigbours(){
		showNeigbourText = !showNeigbourText;
		if(showNeigbourText){
			updateNeigbourText();
		}else{
			for(int i = 0; i<bCellArray.length; i++){
				for(int r = 0; r<bCellArray[i].length; r++){
					bCellArray[i][r].setText("");
				}
			}
		}
	}
	
	public void updateNeigbourText(){
		for(int i = 0; i<bCellArray.length; i++){
			for(int r = 0; r<bCellArray[i].length; r++){
				if(cellArrayStatus[i][r]){
					bCellArray[i][r].setForeground(Color.WHITE);
					bCellArray[i][r].setText(String.valueOf(getNeigbours(i, r)));
				}else{
					bCellArray[i][r].setForeground(Color.BLACK);
					bCellArray[i][r].setText(String.valueOf(getNeigbours(i, r)));
				}
			}
		}
	}
	
	public void updateBoard(){
		for(int i = 0; i<bCellArray.length; i++){
			for(int r = 0; r<bCellArray[i].length; r++){
				if(cellArrayStatus[i][r]){
					bCellArray[i][r].setBackground(Color.BLACK);
				}else{
					bCellArray[i][r].setBackground(defaultButtonColor);
				}
			}
		}
		if(showNeigbourText){
			updateNeigbourText();
		}
	}

	public int randomInteger(int min, int max){						//Random integer between min max
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bStart){
			start();
		}else if(e.getSource() == bStop){
			System.out.println("test2");
		}else if(e.getSource() == bEvolveOneStage){
			evolveOneStage();
		}else if(e.getSource() == bRandomCells){
			createRandomCells();
		}else if(e.getSource() == bClear){
			clearBoard();
		}else if(e.getSource() == bShowNeigbours){
			showNeigbours();
		}else{
			if(!evolving){
				for(int i = 0; i<bCellArray.length; i++){
					for(int r = 0; r<bCellArray[i].length; r++){
						if(e.getSource() == bCellArray[i][r]){
							cellArrayStatus[i][r] = !cellArrayStatus[i][r];
							updateBoard();
						}
					}
				}
			}
			
		}
	}
}

package window;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.Timer;

import presets.PresetHelper;
import presets.PresetHelper.Direction;
import window.GameWindow.Blinker;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameWindow extends JPanel implements ActionListener{
	
	private JButton[][] bCellArray = new JButton[50][50];
	private JTextField tfPercentageLifeCells;
	private JTextField tfDelayPerStage;
	private JTextField tfSaveName;
	private JButton bRandomCells;
	private JLabel lblOfLive;
	private JButton bEvolveOneStage;
	private JButton bStart;
	private JButton bStop;
	private JButton bClear;
	private JButton bShowNeigbours;
	private JButton bSave;
	private JButton bLoad;
	private JButton bRight;
	private JButton bLeft;
	private JButton bUp;
	private JButton bDown;
	private JLabel lblStageDelaySeconds;
	private JLabel lblStage;
	private JLabel lblStageNr;
	private JLabel lblSaveName;
	private JLabel lblShiftCellArray;
	private JComboBox<String> cbPresets;
	
	private boolean[][] cellArrayStatus = new boolean[50][50]; 
	private int stage = 0;
	private int perLifeCells;
	private int delayPerStage;
	private boolean evolving = false;
	private boolean showNeigbourText = false;
	private Color defaultButtonColor = new JButton().getBackground();
	private int timeDelay = 0;
	private Timer evolveTimer = new Timer(timeDelay, new Blinker());
	private final String[] comboContent = {"No Preset","Glider","Light spaceship","Acorn","Pulsar","Glider Canon","One Wide Pattern"};
	private String defPresetPath = "/src/presetsTxtFiles/";
	
	private PresetHelper ph = new PresetHelper();
	
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
		bShowNeigbours.setBounds(1069, 284, 121, 23);
		bShowNeigbours.addActionListener(this);
		bShowNeigbours.setMargin(new Insets(0,0,0,0));
		add(bShowNeigbours);
		
		lblStageDelaySeconds = new JLabel("Stage delay ms");
		lblStageDelaySeconds.setBounds(1070, 168, 120, 14);
		add(lblStageDelaySeconds);
		
		tfDelayPerStage = new JTextField();
		tfDelayPerStage.setText("1000");
		tfDelayPerStage.setBounds(1070, 193, 120, 20);
		add(tfDelayPerStage);
		tfDelayPerStage.setColumns(10);
		
		lblStage = new JLabel("Stage:");
		lblStage.setBounds(1070, 224, 46, 14);
		add(lblStage);
		
		lblStageNr = new JLabel("0");
		lblStageNr.setBounds(1126, 224, 46, 14);
		add(lblStageNr);
		
		cbPresets = new JComboBox<String> (comboContent);
		cbPresets.setToolTipText ("Choose preset");
		cbPresets.setBounds(1069, 314, 121, 23);
		add(cbPresets);
		
		cbPresets.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
            	clearBoard();
            	if(cbPresets.getItemAt(1) == cbPresets.getSelectedItem()){
            		cellArrayStatus = ph.loadPreset(defPresetPath+"glider.txt");
            	}else if(cbPresets.getItemAt(2) == cbPresets.getSelectedItem()){
            		cellArrayStatus = ph.loadPreset(defPresetPath+"lightSpaceShip.txt");
            	}else if(cbPresets.getItemAt(3) == cbPresets.getSelectedItem()){
            		cellArrayStatus = ph.loadPreset(defPresetPath+"acorn.txt");
            	}else if(cbPresets.getItemAt(4) == cbPresets.getSelectedItem()){
            		cellArrayStatus = ph.loadPreset(defPresetPath+"pulsar.txt");
            	}else if(cbPresets.getItemAt(5) == cbPresets.getSelectedItem()){
            		cellArrayStatus = ph.loadPreset(defPresetPath+"gliderCanon.txt");
            	}else if(cbPresets.getItemAt(6) == cbPresets.getSelectedItem()){
            		cellArrayStatus = ph.loadPreset(defPresetPath+"oneWidePattern.txt");
            	}
            	updateBoard();
            }
		});
		
		bSave = new JButton("Save");
		bSave.setBounds(1069, 344, 57, 23);
		bSave.addActionListener(this);
		bSave.setMargin(new Insets(0, 0, 0, 0));
		add(bSave);
		
		bLoad = new JButton("Load");
		bLoad.setBounds(1136, 344, 54, 23);
		bLoad.addActionListener(this);
		bLoad.setMargin(new Insets(0, 0, 0, 0));
		add(bLoad);
	
		lblSaveName = new JLabel("Save/Load Name: ");
		lblSaveName.setBounds(1070, 374, 120, 14);
		add(lblSaveName);
		
		tfSaveName = new JTextField();
		tfSaveName.setBounds(1070, 394, 120, 20);
		add(tfSaveName);
		tfSaveName.setColumns(10);
		
		lblSaveName = new JLabel("Shift Cell Array: ");
		lblSaveName.setBounds(1070, 424, 120, 14);
		add(lblSaveName);
		
		bLeft = new JButton("Left");
		bLeft.setBounds(1069, 444, 57, 23);
		bLeft.addActionListener(this);
		bLeft.setMargin(new Insets(0, 0, 0, 0));
		add(bLeft);
		
		bRight = new JButton("Right");
		bRight.setBounds(1136, 444, 54, 23);
		bRight.addActionListener(this);
		bRight.setMargin(new Insets(0, 0, 0, 0));
		add(bRight);
		
		bUp = new JButton("Up");
		bUp.setBounds(1069, 474, 57, 23);
		bUp.addActionListener(this);
		bUp.setMargin(new Insets(0, 0, 0, 0));
		add(bUp);
		
		bDown = new JButton("Down");
		bDown.setBounds(1136, 474, 54, 23);
		bDown.addActionListener(this);
		bDown.setMargin(new Insets(0, 0, 0, 0));
		add(bDown);
	}
	
	public void evolveOneStage(){
		evolving = true;
		int neigboursAm = 0;
		boolean[][] tempArray = new boolean[50][50];
		for(int i = 0; i<cellArrayStatus.length; i++){
			for(int r = 0; r<cellArrayStatus[i].length; r++){
				neigboursAm = getNeigbours(i, r);
				if(cellArrayStatus[i][r] == false && neigboursAm == 3){
					tempArray[i][r] = true;
				}else if(cellArrayStatus[i][r] && neigboursAm < 2){
					tempArray[i][r] = false;
				}else if(cellArrayStatus[i][r] && neigboursAm > 3){
					tempArray[i][r] = false;
				}else if(cellArrayStatus[i][r] && (neigboursAm == 2 || neigboursAm == 3)){
					tempArray[i][r] = true;
				}
			}
		}
		cellArrayStatus = tempArray;
		stage++;
		lblStageNr.setText(String.valueOf(stage));
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
		enDisButtons(false);
		evolveTimer.setDelay(Integer.parseInt(tfDelayPerStage.getText()));
		evolveTimer.start();
	}
	
	public void stop(){
		enDisButtons(true);
		evolveTimer.stop();
	}
	
	public void enDisButtons(boolean b){
		bRandomCells.setEnabled(b);
		bEvolveOneStage.setEnabled(b);
		bClear.setEnabled(b);
		bSave.setEnabled(b);
		bLoad.setEnabled(b);
		cbPresets.setEnabled(b);
	}
	
	private void loadFromTxt() {
		cellArrayStatus = ph.loadPreset(defPresetPath+tfSaveName.getText()+".txt");
		updateBoard();
	}

	private void saveToTxt() {
		ph.savePreset(tfSaveName.getText(), cellArrayStatus);
	}
	
	private void shiftArray(Direction di){
		cellArrayStatus = ph.shiftArray(di, cellArrayStatus);
		updateBoard();
	}
	
	public void createRandomCells(){
		clearBoard();
		perLifeCells = Integer.parseInt(tfPercentageLifeCells.getText());
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
		stage = 0;
		lblStageNr.setText(String.valueOf(stage));
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
			for(int r = 0; r<bCellArray[0].length; r++){
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
			for(int r = 0; r<bCellArray[0].length; r++){
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
			stop();
		}else if(e.getSource() == bEvolveOneStage){
			evolveOneStage();
		}else if(e.getSource() == bRandomCells){
			createRandomCells();
		}else if(e.getSource() == bClear){
			clearBoard();
		}else if(e.getSource() == bShowNeigbours){
			showNeigbours();
		}else if(e.getSource() == bSave){
			saveToTxt();
		}else if(e.getSource() == bLoad){
			loadFromTxt();
		}else if(e.getSource() == bLeft){
			shiftArray(Direction.LEFT);
		}else if(e.getSource() == bRight){
			shiftArray(Direction.RIGHT);
		}else if(e.getSource() == bUp){
			shiftArray(Direction.UP);
		}else if(e.getSource() == bDown){
			shiftArray(Direction.DOWN);
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

	class Blinker implements ActionListener{
		
        public void actionPerformed(ActionEvent e) {
        	evolveOneStage();
        }
    }
}

package window;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import presets.PresetHelper;
import presets.PresetHelper.Direction;
import utilities.NumberChecker;
import utilities.NumberChecker.TextFieldNumberErrors;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
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
	private JTextArea taConsoleOut;
	private JScrollPane scConsoleScroll;
	private JTextField tfConsoleIn;
	
	private boolean[][] cellArrayStatus = new boolean[bCellArray.length][bCellArray[0].length]; 
	private int stage = 0;
	private int perLifeCells;
	private int delayPerStage;
	private boolean evolving = false;
	private boolean showNeigbourText = false;
	private Color defaultButtonColor = new JButton().getBackground();
	private int timeDelay = 0;
	private Timer evolveTimer = new Timer(timeDelay, new EvolveTimer());
	private final String[] comboContent = {"No Preset","Glider","Light spaceship","Acorn","Pulsar","Glider Canon","One Wide Pattern"};
	private String defPresetPath = "/src/presetsTxtFiles/";
	
	private PresetHelper ph = new PresetHelper();
	private NumberChecker nc = new NumberChecker();
	
	private Action leftArrowAction;
	private Action rightArrowAction;
	private Action upArrowAction;
	private Action downArrowAction;
	
	private Action enterConsoleInAction;
	
	public GameWindow() {
		setupCellArray();
		setupOptions();
		setupConsole();
		setupKeyBindings();
		setPreferredSize (new Dimension (1200, 1000));
		setLayout(null);
		setFocusable(true);
        requestFocusInWindow();
	}
	
	private void setupCellArray(){
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
	
	private void setupOptions(){
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
            	cbPresets.transferFocus();
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
	
	private void setupConsole(){
		taConsoleOut = new JTextArea(0, 0);
		taConsoleOut.setEditable(false);
		scConsoleScroll = new JScrollPane(taConsoleOut);
		scConsoleScroll.setBounds(1010, 800, 180, 150);
		add(scConsoleScroll);
		
		tfConsoleIn = new JTextField();
		tfConsoleIn.setBounds(1010, 955, 180, 20);
		add(tfConsoleIn);
	}
	
	private void setupKeyBindings(){
		leftArrowAction = new ArrowAction("LEFT");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("LEFT"), "doLeftArrowAction" );
		getActionMap().put( "doLeftArrowAction", leftArrowAction );
		
		rightArrowAction = new ArrowAction("RIGHT");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("RIGHT"), "doRightArrowAction" );
		getActionMap().put( "doRightArrowAction", rightArrowAction );

		upArrowAction = new ArrowAction("UP");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("UP"), "doUpArrowAction" );
		getActionMap().put( "doUpArrowAction", upArrowAction );
		
		downArrowAction = new ArrowAction("DOWN");
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke("DOWN"), "doDownArrowAction" );
		getActionMap().put( "doDownArrowAction", downArrowAction );
		
		enterConsoleInAction = new ConsoleAction("ENTER_CONSOLE");
		tfConsoleIn.getInputMap().put( KeyStroke.getKeyStroke("ENTER"), "doEnterConsoleInAction" );
		tfConsoleIn.getActionMap().put( "doEnterConsoleInAction", enterConsoleInAction );
	}
	
	private void evolveOneStage(){
		int neigboursAm = 0;
		boolean[][] tempArray = new boolean[cellArrayStatus.length][cellArrayStatus[0].length];
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
	
	private void evolveStages(int num){
		for(int i = 0; i<num; i++){
			evolveOneStage();
		}
	}
	
	private void evolveStages(String command){
		System.out.println(Arrays.toString(command.split(" ")));
		TextFieldNumberErrors check = nc.isIntegerInRange(command.split(" ")[0], 1);
		if(check == TextFieldNumberErrors.IS_NUMBER_IN_RANGE){
			int num = Integer.parseInt(command.split(" ")[0]);
			for(int i = 0; i<num; i++){
				evolveOneStage();
			}
		}else{									;
			if(check == TextFieldNumberErrors.NOT_A_NUMBER){
				addTa("Command is evolve num num \nhas to be an Integer");
			}else if(check == TextFieldNumberErrors.NUMBER_NOT_IN_RANGE){
				addTa("Number in evolve num has \nto be greater than 0");
			}
		}
		
	}
	
	private int getNeigbours(int i, int r){
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
	
	private void start(){
		TextFieldNumberErrors check = nc.isIntegerInRange(tfDelayPerStage.getText(), 1);
		if(check == TextFieldNumberErrors.IS_NUMBER_IN_RANGE){
			evolving = true;
			enDisButtons(false);
			evolveTimer.setDelay(Integer.parseInt(tfDelayPerStage.getText()));
			evolveTimer.start();
		}else{									;
			if(check == TextFieldNumberErrors.NOT_A_NUMBER){
				addTa("Text in delay text field \nhas to be an number.");
			}else if(check == TextFieldNumberErrors.NUMBER_NOT_IN_RANGE){
				addTa("Number in delay text field \nhas to be greater than 0");
			}
		}
	}
	
	private void stop(){
		evolving = false;
		enDisButtons(true);
		evolveTimer.stop();
	}
	
	private void enDisButtons(boolean b){
		bRandomCells.setEnabled(b);
		bEvolveOneStage.setEnabled(b);
		bClear.setEnabled(b);
		bSave.setEnabled(b);
		bLoad.setEnabled(b);
		cbPresets.setEnabled(b);
		bLeft.setEnabled(b);
		bRight.setEnabled(b);
		bUp.setEnabled(b);
		bDown.setEnabled(b);
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
	
	private void createRandomCells(){
		TextFieldNumberErrors check = nc.isIntegerInRange(tfDelayPerStage.getText(), 0, 100);
		if(check == TextFieldNumberErrors.IS_NUMBER_IN_RANGE){
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
		}else{									;
			if(check == TextFieldNumberErrors.NOT_A_NUMBER){
				addTa("Text in delay text field \nhas to be an number.");
			}else if(check == TextFieldNumberErrors.NUMBER_NOT_IN_RANGE){
				addTa("Number in delay text field \nhas to be between 0 and 100");
			}
		}
	}
	
	private void clearBoard(){
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
	
	private void showNeigbours(){
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
	
	private void updateNeigbourText(){
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
	
	private void updateBoard(){
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

	private int randomInteger(int min, int max){						//Random integer between min max
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	private void processConsoleCommand(String command){
		System.out.println(command);
		if(command.equals("help")){
			System.out.println("im");
			showAllConsoleCommands();
		}else if(command.equals("clear")){
			taConsoleOut.setText("");
		}else if(command.contains("evolve")){
			evolveStages(command.split(" ")[1]);
		}else{
			addTa("Unknown Command");
		}
	}
	
	private void showAllConsoleCommands(){
		addTa("Commands: ");
		addTa("clear (Clears console)");
		addTa("evolve num (evolves num stages num > 0)");
	}
	
	private void addTa(String line){
		taConsoleOut.append(line+"\n");
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

	private class EvolveTimer implements ActionListener{
		
        public void actionPerformed(ActionEvent e) {
        	evolveOneStage();
        }
    }
	
	private class ArrowAction extends AbstractAction
    {
		String name;
		
		public ArrowAction(String name){
			this.name = name;
		}
		
        public void actionPerformed( ActionEvent tf )
        {
          if(!evolving){
        	  if(name == "LEFT"){
            	  shiftArray(Direction.LEFT);
              }else if(name == "RIGHT"){
            	  shiftArray(Direction.RIGHT);
              }else if(name == "UP"){
            	  shiftArray(Direction.UP);
              }else if(name == "DOWN"){
            	  shiftArray(Direction.DOWN);
              }
          }
            
        }
        
    }
	
	private class ConsoleAction extends AbstractAction
    {
		String name;
		
		public ConsoleAction(String name){
			this.name = name;
		}
		
        public void actionPerformed( ActionEvent tf )
        {
            processConsoleCommand(tfConsoleIn.getText());
            tfConsoleIn.setText("");
        }
        
    }
}

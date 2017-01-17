package window;

import javax.swing.JPanel;

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
	private JLabel lblStageDelaySeconds;
	private JLabel lblStage;
	private JLabel lblStageNr;
	
	private int stage = 0;
	private boolean evolving = false;
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}

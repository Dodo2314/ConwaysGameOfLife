package main;

import javax.swing.JFrame;

import window.GameWindow;

public class ConwaysStarter {

	static GameWindow gw = new GameWindow();
	
	public static void main(String[] args) {
		JFrame frame = new JFrame ("Led");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add (gw);
        frame.pack();
        frame.setVisible (true);
	}

}

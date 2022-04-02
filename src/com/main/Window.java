package com.main;

import javax.swing.JFrame;

public class Window {

	public Window(String tittle, Game game) {
		JFrame frame = new JFrame(tittle);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}

}

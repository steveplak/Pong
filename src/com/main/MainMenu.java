package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends MouseAdapter {

	public boolean active;

	private Rectangle playBtn;
	private String PlayTxt = "Play";
	private boolean pHighLight = false;

	private Rectangle quitBtn;
	private String quitTxt = "Play";
	private boolean qHighLight = false;

	private Font font;
	private Game game;

	public MainMenu(Game g) {
		active = true;
		game = g;
		game.start();

		int w, h, x, y;

		w = 300;
		h = 150;

		y = Game.HEIGHT / 2 - h / 2;
		x = Game.WIDTH / 4 - w / 2;
		playBtn = new Rectangle(x, y, w, h);

		x = Game.WIDTH * 3 / 4 - w / 2;
		quitBtn = new Rectangle(x, y, w, h);

		font = new Font("Roboto", Font.PLAIN, 100);

	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(font);

		g.setColor(Color.black);
		if (pHighLight)
			g.setColor(Color.white);
		g2d.fill(playBtn);

		g.setColor(Color.black);
		if (qHighLight)
			g.setColor(Color.white);
		g2d.fill(quitBtn);

		g.setColor(Color.white);
		g2d.draw(playBtn);
		g2d.draw(quitBtn);

		int strWidth;
		int strHeight;

		strWidth = g.getFontMetrics(font).stringWidth(PlayTxt);
		strHeight = g.getFontMetrics(font).getHeight();

		g.setColor(Color.green);
		g.drawString("Play", (int) (playBtn.getX() + playBtn.getWidth() / 2 - strWidth / 2),
				(int) (playBtn.getY() + playBtn.getHeight() / 2 + strHeight / 4));

		strWidth = g.getFontMetrics(font).stringWidth(quitTxt);
		strHeight = g.getFontMetrics(font).getHeight();

		g.setColor(Color.green);
		g.drawString("Quit", (int) (quitBtn.getX() + quitBtn.getWidth() / 2 - strWidth / 2),
				(int) (quitBtn.getY() + quitBtn.getHeight() / 2 + strHeight / 4));

	}

	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();

		if (playBtn.contains(p)) {
			active = false;
			game.playMenu = new PlayMenu(game);
			game.playMenu.active = true;
			game.addMouseListener(game.playMenu);
			game.addMouseMotionListener(game.playMenu);
			
			game.removeMouseListener(this);
			game.removeMouseMotionListener(this);
		} else if (quitBtn.contains(p)) {
			
			System.exit(0);
		}
	}

	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();

		pHighLight = playBtn.contains(p);
		qHighLight = quitBtn.contains(p);

	}

}

package com.main;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.main.Game.Difficulty;

public class DifficultyMenu extends MouseAdapter {
	public boolean active;

	private Rectangle eBtn;
	private String eTxt = "Easy";
	private boolean eHighLight = false;

	private Rectangle mBtn;
	private String mTxt = "Medium";
	private boolean mHighLight = false;

	private Rectangle hBtn;
	private String hTxt = "Hard";
	private boolean hHighLight = false;

	private Font font;

	Game game;

	public DifficultyMenu(Game g) {
		game = g;
		game.start();
		active = false;

		int w, h, x, y;

		w = 250;
		h = 150;

		y = Game.HEIGHT / 2 - h / 2;
		x = Game.WIDTH / 4 - w + 30;
		eBtn = new Rectangle(x, y, w, h);

		x = Game.WIDTH * 2 / 4 - w / 2 - 65;
		mBtn = new Rectangle(x, y, w + 130, h);

		x = Game.WIDTH * 3 / 4 - 30;
		hBtn = new Rectangle(x, y, w, h);

		font = new Font("Roboto", Font.PLAIN, 100);

	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(font);

		g.setColor(Color.black);
		if (eHighLight)
			g.setColor(Color.white);
		g2d.fill(eBtn);

		g.setColor(Color.black);
		if (mHighLight)
			g.setColor(Color.white);
		g2d.fill(mBtn);

		g.setColor(Color.black);
		if (hHighLight)
			g.setColor(Color.white);
		g2d.fill(hBtn);

		g.setColor(Color.white);
		g2d.draw(eBtn);
		g2d.draw(mBtn);
		g2d.draw(hBtn);

		int strWidth;
		int strHeight;

		strWidth = g.getFontMetrics(font).stringWidth(eTxt);
		strHeight = g.getFontMetrics(font).getHeight();

		g.setColor(Color.green);
		g.drawString("Easy", (int) (eBtn.getX() + eBtn.getWidth() / 2 - strWidth / 2),
				(int) (eBtn.getY() + eBtn.getHeight() / 2 + strHeight / 4));

		strWidth = g.getFontMetrics(font).stringWidth(mTxt);
		strHeight = g.getFontMetrics(font).getHeight();

		g.setColor(Color.green);
		g.drawString("Medium", (int) (mBtn.getX() + mBtn.getWidth() / 2 - strWidth / 2),
				(int) (mBtn.getY() + mBtn.getHeight() / 2 + strHeight / 4));

		strWidth = g.getFontMetrics(font).stringWidth(hTxt);
		strHeight = g.getFontMetrics(font).getHeight();

		g.setColor(Color.green);
		g.drawString("Hard", (int) (hBtn.getX() + hBtn.getWidth() / 2 - strWidth / 2),
				(int) (hBtn.getY() + hBtn.getHeight() / 2 + strHeight / 4));

	}

	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();

		if (eBtn.contains(p))
			game.difficulty = Difficulty.Easy;

		else if (mBtn.contains(p))
			game.difficulty = Difficulty.Medium;

		else if (hBtn.contains(p))
			game.difficulty = Difficulty.Hard;
	}

	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();

		eHighLight = eBtn.contains(p);
		mHighLight = mBtn.contains(p);
		hHighLight = hBtn.contains(p);

	}

}
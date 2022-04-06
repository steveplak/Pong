package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayMenu extends MouseAdapter {

	public boolean active = false;

	private Rectangle pvpBtn;
	private String pvpTxt = "PVP";
	private boolean pvpHighLight = false;

	private Rectangle pvcBtn;
	private String pvcTxt = "PVP";
	private boolean pvcHighLight = false;

	private Font font;
	Game game;

	public PlayMenu(Game g) {
		game = g;
		game.start();

		var w = 300;
		var h = 150;

		var y = Game.HEIGHT / 2 - h / 2;
		var x = Game.WIDTH / 4 - w / 2;
		pvpBtn = new Rectangle(x, y, w, h);

		x = Game.WIDTH * 3 / 4 - w / 2;
		pvcBtn = new Rectangle(x, y, w, h);

		font = new Font("Roboto", Font.PLAIN, 100);

	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(font);

		g.setColor(Color.black);
		if (pvpHighLight)
			g.setColor(Color.white);
		g2d.fill(pvpBtn);

		g.setColor(Color.black);
		if (pvcHighLight)
			g.setColor(Color.white);
		g2d.fill(pvcBtn);

		g.setColor(Color.white);
		g2d.draw(pvpBtn);
		g2d.draw(pvcBtn);

		int strWidth;
		int strHeight;

		strWidth = g.getFontMetrics(font).stringWidth(pvpTxt);
		strHeight = g.getFontMetrics(font).getHeight();

		g.setColor(Color.green);
		g.drawString("PVP", (int) (pvpBtn.getX() + pvpBtn.getWidth() / 2 - strWidth / 2),
				(int) (pvpBtn.getY() + pvpBtn.getHeight() / 2 + strHeight / 4));

		strWidth = g.getFontMetrics(font).stringWidth(pvcTxt);
		strHeight = g.getFontMetrics(font).getHeight();

		g.setColor(Color.green);
		g.drawString("PVC", (int) (pvcBtn.getX() + pvcBtn.getWidth() / 2 - strWidth / 2),
				(int) (pvcBtn.getY() + pvcBtn.getHeight() / 2 + strHeight / 4));

	}

	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();

		if (pvcBtn.contains(p)) {
			active = false;
			
			game.difficultyMenu = new DifficultyMenu(game);
			game.difficultyMenu.active = true;
			game.addMouseListener(game.difficultyMenu);
			game.addMouseMotionListener(game.difficultyMenu);
			
			game.removeMouseListener(this);
			game.removeMouseMotionListener(this);
		} else if (pvpBtn.contains(p)) {
			active = false;
		}
	}

	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();

		pvpHighLight = pvpBtn.contains(p);
		pvcHighLight = pvcBtn.contains(p);
		
	}

}

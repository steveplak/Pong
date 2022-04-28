package com.main;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;

public class Ball {
	public static final int SIZE = 16;

	public double x, y;
	public double xVel = 0.3, yVel = 0.3;
	private int speed = 5;

	public Ball() {
		reset();
	}

	private void reset() {
		x = Game.WIDTH / 2 - SIZE / 2;
		y = Game.HEIGHT / 2 - SIZE / 2;

		// xVel = Math.sin(Math.random() * 2.0 - 1.0);
		// xVel = Game.clampAbs(xVel, 0.2, 1.0);
		xVel = -xVel;
		yVel = Math.sin(Math.random() * 2.0 - 1.0) % 0.4;
	}

	public void changeYDir() {
		yVel *= -1;
	}

	public void changeXDir() {
		xVel *= -1;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int) x, (int) y, SIZE, SIZE);
	}

	public void update(Paddle p1, Paddle p2) {
		x += xVel * speed;
		y += yVel * speed;

		if (y + SIZE >= Game.HEIGHT || y <= 0)
			changeYDir();

		if (x + SIZE >= Game.WIDTH) {
			p1.addPoint();
			reset();
		}

		if (x <= 0) {
			p2.addPoint();
			reset();
		}
	}
}

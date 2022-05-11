package com.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Paddle {

	private double x, y;
	private double vel = 0;
	private double speed = 2;
	private int width = 22, height = 85;
	private int score = 0;
	private Color color;
	private boolean left;
	
	Game game;
	
	public Paddle(Color c, boolean left) {
		color = c;

		this.left = left;
		x = left ? 0 : Game.WIDTH - width;
		y = Game.HEIGHT / 2 - height / 2;

	}

	public void addPoint() {
		score++;
		if(score > 3){
			System.out.print("first statement. ");  
			GameOver(game.ball);
			
		}
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int) x, (int) y, width, height);

		int sx;
		String scoreText = Integer.toString(score);
		Font font = new Font("Roboto", Font.PLAIN, 50);

		int strWidth = g.getFontMetrics(font).stringWidth(scoreText) + 1;
		int padding = 25;

		if (left)
			sx = Game.WIDTH / 2 - padding - strWidth;
		else
			sx = Game.WIDTH / 2 + padding;

		g.setFont(font);
		g.drawString(scoreText, sx, 50);
	}

	public void update(Ball b) {
		y = Game.clamp(y + vel, 0, Game.HEIGHT - height);

		if (left) {
			if (b.x <= width && b.y + b.SIZE >= y && b.y <= y + height) {
				b.changeXDir();
				b.yVel = Math.sin(Math.random() * 2.0 - 1.0) % 0.4;
			}
		} else {
			if (b.x + b.SIZE >= Game.WIDTH - width && b.y + b.SIZE >= y && b.y <= y + height) {
				b.changeXDir();
				b.yVel = Math.sin(Math.random() * 2.0 - 1.0) % 0.4;
			}
		}
	}
	
	public void updateCPU(Ball b, Game g) {
		switch (g.difficulty) {
		case Easy: vel = 0.3; break;
		case Medium: vel = 1; break;
		case Hard: vel = 2; break;
		}

		if (b.y < y) vel = -Math.abs(vel);
		else if (b.y > y) vel = Math.abs(vel);
		y = Game.clamp(y + vel, 0, Game.HEIGHT - height);
		
		if (left) {
			if (b.x <= width && b.y + b.SIZE >= y && b.y <= y + height) {
				b.changeXDir();
				
			}
		} else {
			if (b.x + b.SIZE >= Game.WIDTH - width && b.y + b.SIZE >= y && b.y <= y + height) {
				b.changeXDir();
			
			}
		}
	}

	public void switchDirection(int direction) {
		vel = speed * direction;
		
	}
	
	public void stop() {
		vel = 0;
		
	}
	public void GameOver(Ball b) {
		b.xVel = 0;
		b.yVel = 0;
	}
}

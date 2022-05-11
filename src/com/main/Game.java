package com.main;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -668240625892092763L;

	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH * 9 / 16;

	enum Difficulty {
		Easy, Medium, Hard
	}

	enum Mode {
		Inactive, PVP, PVC
	}

	public boolean running = false;
	private Thread gameThread;

	public Ball ball;
	private Paddle paddle1;
	private Paddle paddle2;

	public MainMenu mainMenu;
	public PlayMenu playMenu;
	public DifficultyMenu difficultyMenu;
	public Difficulty difficulty;
	public Mode mode = Mode.Inactive;

	public Game() {

		canvasSetup();

		new Window("SimplePong", this);

		initialize();

		this.addKeyListener(new KeyInput(paddle1, paddle2));
		this.addMouseListener(mainMenu);
		this.addMouseMotionListener(mainMenu);
		this.setFocusable(true);

	}

	private void initialize() {

		ball = new Ball();
		paddle1 = new Paddle(Color.green, true);
		paddle2 = new Paddle(Color.red, false);

		mainMenu = new MainMenu(this);
		playMenu = new PlayMenu(this);
		difficultyMenu = new DifficultyMenu(this);

	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

	}

	@Override
	public void run() {
		this.requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
				draw();
				frames++;
			}

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}

		stop();
	}

	private void draw() {
		BufferStrategy buffer = this.getBufferStrategy();

		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();

		drawBackground(g);
		if (mainMenu.active)
			mainMenu.draw(g);
		if (playMenu.active)
			playMenu.draw(g);
		if (difficultyMenu.active)
			difficultyMenu.draw(g);
		ball.draw(g);

		paddle1.draw(g);
		paddle2.draw(g);

		g.dispose();
		buffer.show();
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
	}
	
	public void runPVP() {
		ball.update(paddle1, paddle2);

		paddle1.update(ball);
		paddle2.update(ball);
	}
	
	public void runPVC() {
		ball.update(paddle1, paddle2);

		paddle1.updateCPU(ball, this);
		paddle2.updateCPU(ball, this);
	}

	private void update() {
		switch (mode) {
		case Inactive:
			break;
		case PVP:
			runPVP();
			break;
		case PVC:
			runPVC();
			break;
		}
	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running = true;

	}

	public void stop() {

		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static int sign(double d) {
		if (d <= 0)
			return -1;
		return 1;
	}

	public static double clamp(double value, double min, double max) {
		if (value > max)
			return max;
		if (value < min)
			return min;
		return value;
	}

	// clamp(-1, 2, 3) -> 2
	public static double clampAbs(double value, double min, double max) {
		var isNegative = value < 0;
		var abs = Math.abs(value);

		if (abs < min) {
			return isNegative ? -min : min;
		} else if (abs > max) {
			return isNegative ? -max : max;
		}

		return value;
	}

	public static void main(String[] args) {
		new Game();
	}

}

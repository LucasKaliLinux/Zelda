package com.djstudio.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.djstudio.entites.Enemy;
import com.djstudio.entites.Entity;
import com.djstudio.entites.Player;
import com.djstudio.graphics.Spritesheet;
import com.djstudio.graphics.UI;
import com.djstudio.world.World;
import com.djstudio.world.dinamic_light;

public class Game extends Canvas implements Runnable,KeyListener{

	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private Thread t;
	private JFrame frame = new JFrame();
	
	private BufferedImage image;
	
	private boolean showMessageGameOver = true;
	private int FramesGameOver = 0;
	
	private boolean restartGame = false;

	public final static int WIDTH = 240;//240
	public final static int HEIGHT = WIDTH * 3 / 4;
	public final static int SCALE = 3;//3
	
	public static UI ui;
	public static Spritesheet spritesheet;
	public static Player player;
	public static List<Entity> entities;
	public static List<Enemy> enemys;
	public static Random rand;
	
	public static String gameState = "MENU";//MENU
	
	public static World world;
	public static Menu menu;
	public static dinamic_light light;
	
	public static boolean saveGame = false;
	
	public static int[] pixels;
	
	
	public Game() {
		rand = new Random();
		this.addKeyListener(this);
		init("Zelda");
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		//new Objects
		light = new dinamic_light("/lightMap.png");
		spritesheet = new Spritesheet("/Sprites.png");
		entities = new ArrayList<Entity>();
		enemys = new ArrayList<Enemy>();
		player = new Player(0,0,16,16,spritesheet.getSprite(0, 16, 16, 16));
		world = new World("/level1.png");
		menu = new Menu();
		ui = new UI();
		entities.add(player);
	}
	
	private void init(String title) {
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));	
		
		frame.add(this);
		frame.pack();
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	private synchronized void start() {
		if(isRunning) {
			return;
		}
		
		isRunning = true;
		t = new Thread(this);
		t.start();
		frame.requestFocus();
	} 
	
	private synchronized void stop() {
		isRunning = false;
		System.exit(0);
	} 
	
	public void update() {
		if(gameState == "NORMAL") {
			if(var_global.CUR_LEVEL < 3) {
				Sound.musicArea1.loop();
				Sound.musicArea2.stop();
			}else {
				Sound.musicArea2.loop();
				Sound.musicArea1.stop();
			}
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"vida", "isSword", "maxLife", "level"};
				int[] opt2 = {var_global.player_life,var_global.booleanToInt(var_global.player_isSword), var_global.player_max_life, var_global.CUR_LEVEL};
				Menu.saveGame(opt1,opt2,15);
				System.out.println("Jogo salvo!!!");
			}
			this.restartGame = false;
			for(int i = 0;i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.update();
			}
		}else if(gameState == "COMEMORATION") {
			player.framesC++;
			if(player.framesC == player.maxFramesC) {
				player.framesC = 0;
				player.getSword = false;
				player.getHeartContainer = false;
				gameState = "NORMAL";
			}
		}else if(gameState == "GAME OVER") {
			FramesGameOver++;
			if(FramesGameOver == 30) {
				FramesGameOver = 0;
				if(this.showMessageGameOver) {
					this.showMessageGameOver = false;
				}else {
					this.showMessageGameOver = true;
				}
			}
			
			if(restartGame) {
				World.restartGame("level1.png", true);
				gameState = "NORMAL";
			}
		}else if(gameState == "MENU") {
			menu.update();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Render 
		
		world.render(g);
		Collections.sort(entities, Entity.entitySorter);
		for(int i = 0;i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
			
		if(var_global.CUR_LEVEL >= 3) 
			light.applyLight();
		ui.render(g);
		//
		
		g.dispose();
		g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		if(gameState == "GAME OVER") {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(0, 0, 0, 100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.setColor(Color.red);
			g.drawString("GAME OVER", ((WIDTH*SCALE)/2) - 100, ((HEIGHT*SCALE)/2) - 20);
			
			g.setFont(new Font("arial", Font.BOLD, 32));
			g.setColor(Color.white);
			if(showMessageGameOver) {
				
				g.drawString(">Pressione Enter para reiniciar<", (WIDTH*SCALE)/2 - 250, ((HEIGHT*SCALE)/2) + 50);
			}
		}else if(gameState == "MENU") {
			menu.render(g);
		}
		bs.show();
	}
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		requestFocus();
		
		while (isRunning) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				update();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if(gameState == "MENU") {
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.attack = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if(gameState == "MENU")
				menu.enter = true;	
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "MENU";
			menu.pause = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
	}

}
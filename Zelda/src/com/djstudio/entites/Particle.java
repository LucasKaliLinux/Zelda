package com.djstudio.entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.world.Camera;

public class Particle extends Entity{

	public int lifeTime = 15;
	public int curLife = 0;
	
	public int spd = 1;
	
	private double dx = 0;
	private double dy = 0; 
	
	public Particle(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		dx = Game.rand.nextGaussian();
		dy = Game.rand.nextGaussian();
	}

	public void update() {
		x+=dx*spd;
		y+=dy*spd;
		curLife++;
		if(lifeTime == curLife) {
			Game.entities.remove(this);
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
	
}

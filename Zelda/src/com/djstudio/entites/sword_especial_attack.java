package com.djstudio.entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.main.Sound;
import com.djstudio.world.Camera;
import com.djstudio.world.World;

public class sword_especial_attack extends Entity{

	private int dx;
	private int dy;
	private int speed = 3;
	
	private int life = 72;
	private int curLife = 0;
	
	public sword_especial_attack(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x+=dx*speed;
		y+=dy*speed;
		curLife++;
		if(!World.isFree((int)(x+dx*speed), this.getY()) || !World.isFree(this.getX(), (int)(y+dy*speed))) {
			Game.entities.remove(this);
			World.generateParticle(40, this.getX(), this.getY());
			return;
		}
		if(curLife == life) {
			Game.entities.remove(this);
			return;
		}
	}

	public void render(Graphics g) {
		//g.setColor(Color.red);
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
		if(dx == 1) {
			g.drawImage(WEAPON_SWORD_SIMPLE, this.getX() - Camera.x, this.getY() - Camera.y - 3, 16, 16, null);
		}else if(dx == -1) {
			g.drawImage(WEAPON_SWORD_SIMPLE_LEFT, this.getX() - Camera.x, this.getY() - Camera.y - 3, 16, 16, null);
		}else if(dy == 1) {
			g.drawImage(WEAPON_SWORD_SIMPLE_DOWN, this.getX() - Camera.x - 4, this.getY() - Camera.y, 16, 16, null);
		}else if(dy == -1) {
			g.drawImage(WEAPON_SWORD_SIMPLE_UP, this.getX() - Camera.x - 4, this.getY() - Camera.y, 16, 16, null);
		}
	}
}

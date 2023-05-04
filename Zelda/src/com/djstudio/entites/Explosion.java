package com.djstudio.entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.world.Camera;

public class Explosion extends Entity{

	private int frames = 0;
	private int targetFrames = 5;
	private int maxAnimation = 2;
	private int curAnimation = 0;
	
	public BufferedImage[] explosionSprites = new BufferedImage[3];
	
	public Explosion(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		explosionSprites[0] = Game.spritesheet.getSprite(32, 0, 16, 16);
		explosionSprites[1] = Game.spritesheet.getSprite(48, 0, 16, 16);
		explosionSprites[2] = Game.spritesheet.getSprite(64, 0, 16, 16);
	}
	
	public void update() {
		frames++;
		if(frames == targetFrames) {
			frames = 0;
			curAnimation++;
			if(curAnimation > maxAnimation) {
				Game.entities.remove(this);
				return;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(explosionSprites[curAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}

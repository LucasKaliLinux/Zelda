package com.djstudio.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;

public class UI {
	
	public static BufferedImage HEART_MAX = Game.spritesheet.getSprite(0, 80, 8, 8);
	public static BufferedImage HEART = Game.spritesheet.getSprite(8, 80, 8, 8);
	
	public void render(Graphics g) {
		for(int i = 0; i < Game.player.maxLife; i++) {
			g.drawImage(HEART_MAX, 5+(i*15), 5, 16, 16, null);
		}
		for(int i = 0; i < Game.player.life; i++) {
			g.drawImage(HEART, 5+(i*15), 5, 16, 16, null);
		}
	}
}

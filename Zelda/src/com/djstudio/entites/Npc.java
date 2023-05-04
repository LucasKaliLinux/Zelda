package com.djstudio.entites;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.world.Camera;

public class Npc extends Entity{

	public static BufferedImage[] sprites;
	public String[] frases = new String[9];
	
	public boolean showMessage = false;
	
	private int frames = 0,maxFrames = 30,index = 0,maxIndex = 1;
	private int frasesIndex = 0;
	private int curIndexMsg = 0;
	
	private int time = 0;
	private int maxTime = 5;
	
	public Npc(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(80, 0, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(80+16, 0, 16, 16);
	
		frases[0] = "Hello, You shouldn't be here right!";
		frases[1] = "There are many monsters around here!";
		frases[2] = "Be careful!!";
		frases[3] = "There's a sword in a temple nearby...";
		frases[4] = "many have failed to wield this sword...";
		frases[5] = "try to pick up the sword and move on...";
		frases[6] = "Ah, I almost forgot, be careful with...";
		frases[7] = "Slime, skeleton, bat and guardians!";
		frases[8] = "Good lucky!!!";
		
		depth = 2;
	}

	public void update() {
		
		if(this.calculoDeDistancia(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 20) {
			showMessage = true;
		}else {
			showMessage = false;
		}
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
		if(showMessage) {
			this.time++;
			if(time >= maxTime) {
				time = 0;
				if(curIndexMsg < frases[frasesIndex].length())
					curIndexMsg++;
				else {
					if(frasesIndex < frases.length-1) {
						frasesIndex++;
						curIndexMsg = 0;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		if(showMessage) {
			g.setColor(Color.black);
			g.fillRect(5, (Game.HEIGHT - Game.HEIGHT/3) - 5, Game.WIDTH - 20, Game.HEIGHT/3 - 2);
			g.setColor(Color.blue);
			g.fillRect(10, Game.HEIGHT - Game.HEIGHT/3, Game.WIDTH - 20, Game.HEIGHT/3 - 5);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.setColor(Color.black);
			g.drawString(frases[frasesIndex].substring(0, curIndexMsg), 20, (Game.HEIGHT - Game.HEIGHT/3)+30);
			g.drawString("NPC:", 15, (Game.HEIGHT - Game.HEIGHT/3)+15);
		}
	}
	
}

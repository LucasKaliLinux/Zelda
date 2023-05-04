package com.djstudio.entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.world.Camera;

public class sword_simple_attack extends Entity{

	int desc_x = 10;
	int desc_y = 10;
	int widthVar = 16;
	int heightVar = 16;
	
	public sword_simple_attack(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		this.setWidth(widthVar);
		this.setHeight(heightVar);
		
		if(Player.dir == Player.left_dir) {
			this.setX(this.getX() - desc_x);
		}else if(Player.dir == Player.right_dir) {
			this.setX(this.getX() + desc_x);
		}else if(Player.dir == Player.up_dir) {
			this.setY(this.getY() - desc_y);
		}else if(Player.dir == Player.down_dir) {
			this.setY(this.getY() + desc_y);
		}
	}
	
	/*public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}*/

}

package com.djstudio.entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.main.var_global;
import com.djstudio.world.Camera;
import com.djstudio.world.World;

public class backLevel extends Entity{
	
	public boolean back_level = false;
	
	public static int origim_pointX;
	public static int origim_pointY;
	
	public backLevel(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		origim_pointX = this.getX();
		origim_pointY = this.getY();
	}
	
	public void update() {
		if(back_level) {
			back_level = false;
			Game.saveGame = true;
			var_global.CUR_LEVEL--;
			if(var_global.CUR_LEVEL < 0) {
				var_global.CUR_LEVEL = 1;
			}
			String newWorld = "level"+var_global.CUR_LEVEL+".png";
			var_global.dir_player = Game.player.dir;
			var_global.back_Level_Class = true;
			
			World.restartGame(newWorld, false);
		}
		
		if(var_global.next_Level_class == true) {
			var_global.dirPlayerLevel(var_global.dir_player, this.getX(), this.getY());
			var_global.next_Level_class = false;
		}
	}

	public void render(Graphics g) {
		super.render(g);
		//g.setColor(Color.yellow);
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}

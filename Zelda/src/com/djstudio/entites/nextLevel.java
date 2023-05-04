package com.djstudio.entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.main.var_global;
import com.djstudio.world.Camera;
import com.djstudio.world.World;

public class nextLevel extends Entity{
	
	public boolean next_level = false;
	
	public nextLevel(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		if(next_level) {
			next_level = false;
			Game.saveGame = true;
			var_global.CUR_LEVEL++;
			if(var_global.CUR_LEVEL > var_global.MAX_LEVEL) {
				var_global.CUR_LEVEL = 1;
			}
			String newWorld = "level"+var_global.CUR_LEVEL+".png";
			var_global.dir_player = Game.player.dir;
			var_global.next_Level_class = true;
			World.restartGame(newWorld, true);
		}
		
		if(var_global.back_Level_Class == true) {
			var_global.dirPlayerLevel(var_global.dir_player, this.getX(), this.getY());
			var_global.back_Level_Class = false;
		}
	}
	
	

	public void render(Graphics g) {
		super.render(g);
		//g.setColor(Color.red);
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
	}
}

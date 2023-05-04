package com.djstudio.main;

import com.djstudio.entites.Entity;

public class var_global {
	
	public static boolean player_isSword = false;
	public static int player_max_life = 3;
	public static int player_life = player_max_life;
	public static int dir_player = 0;
	
	public static boolean next_Level_class = false;
	public static boolean back_Level_Class = false;
	
	public static int CUR_LEVEL = 1;
	public static int MAX_LEVEL = 4;
	
	
	public static int booleanToInt(boolean foo) {
	    return (foo) ? 1 : 0;
	}
	
	public static boolean intToBoolean(int foo) {
		int bar = Entity.sign(foo);
		if(bar == 1) {
			return true;
		}
		return false;
	}
	
	public static void dirPlayerLevel(int dir, int getX, int getY) {
		if(dir == Game.player.right_dir) {
			
			Game.player.setX(getX + 18);
			Game.player.setY(getY);
			Game.player.dir = Game.player.right_dir;
			
		}else if(dir == Game.player.left_dir) {
			
			Game.player.setX(getX - 18);
			Game.player.setY(getY);
			Game.player.dir = Game.player.left_dir;
			
		}else if(dir == Game.player.up_dir) {
			
			Game.player.setX(getX);
			Game.player.setY(getY - 18);
			Game.player.dir = Game.player.up_dir;
			
		}else if(dir == Game.player.down_dir) {
			Game.player.dir = Game.player.down_dir;
			Game.player.setX(getX);
			Game.player.setY(getY + 18);
		}
	}
}

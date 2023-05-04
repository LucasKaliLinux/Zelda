package com.djstudio.entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import com.djstudio.main.Game;
import com.djstudio.world.Camera;
import com.djstudio.world.Node;
import com.djstudio.world.Vector2i;

public class Entity {
	
	public static BufferedImage LIFE_POTION = Game.spritesheet.getSprite(16, 64, 16, 16);
	public static BufferedImage WEAPON_SWORD_SIMPLE = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage WEAPON_SWORD_SIMPLE_LEFT = Game.spritesheet.getSprite(16, 48, 16, 16);
	public static BufferedImage WEAPON_SWORD_SIMPLE_DOWN = Game.spritesheet.getSprite(32, 48, 16, 16);
	public static BufferedImage WEAPON_SWORD_SIMPLE_UP = Game.spritesheet.getSprite(48, 48, 16, 16);
	public static BufferedImage WEAPON_SWORD = Game.spritesheet.getSprite(64, 48, 16, 16);
	public static BufferedImage HEART_CONTAINER = Game.spritesheet.getSprite(0, 64, 16, 16);
	public static BufferedImage ENEMY_ENTITY = Game.spritesheet.getSprite(128, 16, 16, 16);
	
	public int depth;
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	protected List<Node> path;
	
	private BufferedImage sprite;
	
	protected int maskx, masky, mwidth, mheight;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.maskx, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.maskx, e2.mwidth, e2.mheight);
	
		return e1Mask.intersects(e2Mask);
	}
	
	public static int sign(int value) {
		if(value > 0) {
			return 1;
		}
		if(value < 0) {
			return -1;
		}
		
		return 0;
	}
	
	public static Comparator<Entity> entitySorter = new Comparator<Entity>() {
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
	};
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) {
					x++;
				}else if(x > target.x * 16) {
					x--;
				}
				if(y < target.y * 16) {
					y++;
				}else if(y > target.y * 16) {
					y--;
				}
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	public double calculoDeDistancia(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2 - x1)*(x2 - x1)+(y2 - y1)*(y2 - y1));
	}
	
	public void update() {}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		
	}
}

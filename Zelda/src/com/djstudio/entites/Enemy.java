package com.djstudio.entites;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.main.var_global;
import com.djstudio.world.AStar;
import com.djstudio.world.Camera;
import com.djstudio.world.Vector2i;
import com.djstudio.world.World;

public class Enemy extends Entity{
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		depth = 0;
	}
	
	public boolean isCollidingEnemy(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext, yNext, width, height);
		for(int i = 0; i < Game.enemys.size(); i++) {
			Enemy e = Game.enemys.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), width, height);
			if(enemyCurrent.intersects(targetEnemy))
				return true;
		}
		
		return false;
	}
	
	public boolean collidingSwordSpecial() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof sword_especial_attack) {
				if(isColliding(this, e)) {
					Game.entities.remove(e);
					//System.out.println("test, levou attack");
					//System.out.println("Vida: "+life);
					return true;
				}
			}
		}
		
		return false;
	}
	protected void destroySelf() {
		Explosion explosion = new Explosion(this.getX(), this.getY(), 16, 16, null);
		Game.entities.add(explosion);
		Game.enemys.remove(this);
		Game.entities.remove(this);
		return;
	}
	
	public boolean collidingSword() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof sword_simple_attack) {
				if(isColliding(this, e)) {
					if(Game.player.isAttacking) {
						Game.entities.remove(e);
						//System.out.println("test, levou attack");
						//System.out.println("Vida: "+life);
						return true;
					}
				}
			}
		}
		
		return false;
	}
}

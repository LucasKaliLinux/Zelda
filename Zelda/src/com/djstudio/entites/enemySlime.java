package com.djstudio.entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.main.var_global;
import com.djstudio.world.Camera;
import com.djstudio.world.World;

public class enemySlime extends Enemy{
	
	private double speed = 0.5;	
	private int down_dir = 4, up_dir = -4, left_dir = -1, right_dir = 1;
	private int dir = down_dir;
	
	private int damageFrames = 10, damageCurrent = 0;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	
	private int knockback_timer = 10;//10
	private int knockback_frames = 0;
	private int knockback_dir;
	private boolean knockback_acti = false;
	
	private BufferedImage[] sprites;
	private BufferedImage[] FEEDBACK_ENEMY;
	
	public int life = 2; // 2 life max
	public int dano = 1;
	
	public boolean isDamaged = false;
	
	public enemySlime(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[2];
		FEEDBACK_ENEMY = new BufferedImage[2];
		
		sprites[0] = Game.spritesheet.getSprite(128, 16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(144, 16, 16, 16);
		FEEDBACK_ENEMY[0] = Game.spritesheet.getSprite(128, 32, 16, 16);
		FEEDBACK_ENEMY[1] = Game.spritesheet.getSprite(144, 32, 16, 16);
	}
public void update() {
		
		move();
		if(this.life <= 0) {
			destroySelf();
		}
		systemAnimation();
		systemDamaged();		
		knockback();
		systemCollidingSword();
		//collidingSword();
		
	}
	
	private void systemCollidingSword() {
		if(this.collidingSword()){
			life--;
			isDamaged = true;
		}
		
		if(this.collidingSwordSpecial()) {
			life-=3;
			isDamaged = true;
		}
	}
	
	private void move() {
		if(this.calculoDeDistancia(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 112) { //208
			if(isColliding(this, Game.player) == false) {
				if(isDamaged == false) {
					if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) && !isCollidingEnemy((int)(x+speed), this.getY()) && !knockback_acti) {
						x+=speed;
						dir = right_dir;
					}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())  && !isCollidingEnemy((int)(x-speed), this.getY()) && !knockback_acti) {
						x-=speed;
						dir = left_dir;
					}
					if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed)) && !isCollidingEnemy(this.getX(), (int)(y+speed)) && !knockback_acti) {
						y+=speed;
						dir = down_dir;
					}else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed)) && !isCollidingEnemy(this.getX(), (int)(y-speed)) && !knockback_acti) {
						y-=speed;
						dir = up_dir;
					}
				}
			}else {
				//Collinder Player
				if(Game.rand.nextInt(100) < 10 && Game.player.invecible == false) {
					Game.player.life-=dano;
					var_global.player_life -= dano;
					Game.player.isDamaged = true;
				}
			}
		}
	}
	
	private void systemAnimation() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex) {
				index = 0;
			}
		}
	}
	
	private void systemDamaged() {
		if(isDamaged) {
			knockback_acti = true;
			this.damageCurrent++;
			if(damageCurrent >= this.damageFrames) {
				damageCurrent = 0;
				isDamaged = false;
			}
		}
	}
	
	private void knockback() {
		if(knockback_acti) {
			knockback_dir = dir;
			knockback_frames++;
			if(knockback_frames >= knockback_timer) {
				knockback_frames = 0;
				knockback_acti = false;
			}else {
				if(knockback_dir == right_dir || knockback_dir == left_dir) {
					if(World.isFree((int)(x+(-sign(knockback_dir) * 5)), this.getY()) && !isCollidingEnemy((int)(x+(-sign(knockback_dir) * 5)), this.getY())) {
						x += -sign(knockback_dir) * 4;
					}
				}else if(knockback_dir == up_dir || knockback_dir == down_dir) {
					if(World.isFree(this.getX(), (int)(y+(-sign(knockback_dir) * 4))) && !isCollidingEnemy(this.getX(), (int)(y+(-sign(knockback_dir) * 4))))
						y += -sign(knockback_dir) * 4;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged)
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else
			g.drawImage(FEEDBACK_ENEMY[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, this.mwidth, this.mheight);
		
	}
}
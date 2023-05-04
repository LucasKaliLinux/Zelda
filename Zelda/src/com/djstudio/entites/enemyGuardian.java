package com.djstudio.entites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;
import com.djstudio.main.var_global;
import com.djstudio.world.Camera;
import com.djstudio.world.World;

public class enemyGuardian extends Enemy{
	
	private boolean moved;
	private boolean defender;
	
	private double speed = 0.6;	
	private int down_dir = 4, up_dir = -4, left_dir = -1, right_dir = 1;
	private int dir = down_dir;
	
	private int damageFrames = 10, damageCurrent = 0;
	private int frames = 0, maxFrames = 16, index = 0, maxIndex = 1;
	
	private int knockback_timer = 10;//10
	private int knockback_frames = 0;
	private int knockback_dir;
	private boolean knockback_acti = false;
	
	private BufferedImage[] rightGuardian;
	private BufferedImage[] leftGuardian;
	private BufferedImage[] upGuardian;
	private BufferedImage[] downGuardian;
	
	private static BufferedImage feedbackUp;
	private static BufferedImage feedbackDown;
	private static BufferedImage feedbackRight;
	private static BufferedImage feedbackLeft;
	
	public int life = 6;
	public int dano = 3;
	
	public boolean isDamaged = false;
	
	public enemyGuardian(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightGuardian = new BufferedImage[2];
		leftGuardian = new BufferedImage[2];
		upGuardian = new BufferedImage[2];
		downGuardian = new BufferedImage[2];
		
		//FEEDBACK_ENEMY = new BufferedImage[2];
		rightGuardian[0] = Game.spritesheet.getSprite(272, 16, 16, 16);
		rightGuardian[1] = Game.spritesheet.getSprite(272, 32, 16, 16);
		upGuardian[0] = Game.spritesheet.getSprite(256, 16, 16, 16);
		upGuardian[1] = Game.spritesheet.getSprite(256, 32, 16, 16);
		leftGuardian[0] = Game.spritesheet.getSprite(240, 16, 16, 16);
		leftGuardian[1] = Game.spritesheet.getSprite(240, 32, 16, 16);
		downGuardian[0] = Game.spritesheet.getSprite(224, 16, 16, 16);
		downGuardian[1] = Game.spritesheet.getSprite(224, 32, 16, 16);

		feedbackUp = Game.spritesheet.getSprite(256, 48, 16, 16);
		feedbackDown = Game.spritesheet.getSprite(224, 48, 16, 16);
		feedbackRight = Game.spritesheet.getSprite(272, 48, 16, 16);
		feedbackLeft = Game.spritesheet.getSprite(240, 48, 16, 16);
		
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
		defesa();
		
	}
	
	private void systemCollidingSword() {
		if(this.collidingSword()){
			if(Game.rand.nextInt(100) > 35) {
				life--;
				isDamaged = true;
			}else {
				//defendeu
				System.out.println("Denfendeu");
				defender = true;
			}
		}
		
		if(this.collidingSwordSpecial()) {
			if(Game.rand.nextInt(100) > 15) {
				life-=3;
				isDamaged = true;
			}else {
				//defendeu
				System.out.println("Denfendeu");
				defender = true;
			}
		}
	}
	
	private void defesa() {
		if(defender == true) {
			defender = false;
			knockback_acti = true;
			World.generateParticle(80, this.getX(), this.getY());
		}
	}
	
	private void move() {
		moved = false;
		if(this.calculoDeDistancia(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 112) { //208
			if(isColliding(this, Game.player) == false) {
				if(isDamaged == false && knockback_acti == false) {
					if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) && !isCollidingEnemy((int)(x+speed), this.getY()) && !knockback_acti) {
						moved = true;
						x+=speed;
						dir = right_dir;
					}else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY())  && !isCollidingEnemy((int)(x-speed), this.getY()) && !knockback_acti) {
						moved = true;
						x-=speed;
						dir = left_dir;
					}
					if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed)) && !isCollidingEnemy(this.getX(), (int)(y+speed)) && !knockback_acti) {
						moved = true;
						y+=speed;
						dir = down_dir;
					}else if((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed)) && !isCollidingEnemy(this.getX(), (int)(y-speed)) && !knockback_acti) {
						moved = true;
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
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
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
		if(!isDamaged) {
			if(dir == up_dir) {
				g.drawImage(upGuardian[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == down_dir) {
				g.drawImage(downGuardian[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == right_dir) {
				g.drawImage(rightGuardian[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == left_dir) {
				g.drawImage(leftGuardian[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}else {
			if(dir == up_dir) {
				g.drawImage(feedbackUp, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == down_dir) {
				g.drawImage(feedbackDown, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == right_dir) {
				g.drawImage(feedbackRight, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == left_dir) {
				g.drawImage(feedbackLeft, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, this.mwidth, this.mheight);
		
	}
}

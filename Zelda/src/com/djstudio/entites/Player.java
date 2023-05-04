package com.djstudio.entites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import com.djstudio.graphics.Spritesheet;
import com.djstudio.main.Game;
import com.djstudio.main.Sound;
import com.djstudio.main.var_global;
import com.djstudio.world.Camera;
import com.djstudio.world.World;

public class Player extends Entity{

	public double speed = 1;
	public static int down_dir = 4, up_dir = -4, left_dir = -1, right_dir = 1;
	public static int dir = up_dir;
	public boolean right, up, left, down;
	
	public int maxLife = var_global.player_max_life;
	public int life = var_global.player_life;//maxLife;
	public int framesC = 0, maxFramesC = 120;
	
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 1;
	private int attackFrames = 0, maxAttackFrames = 5, attackIndex = 0, maxAttackIndex = 3; 
	private int framesD = 0, framesI = 0;
	
	private int framesCoolAttackEsp = 0,maxFramesCoolAttackEsp = 80;
	private boolean cooldawnAttackEsp = false;
	
	private int knockback_timer = 10;
	private int knockback_frames = 0;
	private int knockback_dir;
	private boolean knockback_acti = false;
	
	private boolean moved;
	private boolean isSword = var_global.player_isSword;
	
	public static boolean attack = false;
	public static boolean isAttacking = false;
	public static boolean invecible = false;
	
	public boolean getSword = false;
	public boolean getHeartContainer = false;
	public boolean isDamaged = false;
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	
	private BufferedImage[] downAttackPlayer;
	private BufferedImage[] upAttackPlayer;
	private BufferedImage[] rightAttackPlayer;
	private BufferedImage[] leftAttackPlayer;
	
	private BufferedImage Comemoration;
	private BufferedImage playerDamager;
	
	private sword_simple_attack ssw;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		rightPlayer = new BufferedImage[2];
		leftPlayer = new BufferedImage[2];
		upPlayer = new BufferedImage[2];
		downPlayer = new BufferedImage[2];
		downAttackPlayer = new BufferedImage[4];
		upAttackPlayer = new BufferedImage[4];
		rightAttackPlayer = new BufferedImage[4];
		leftAttackPlayer = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++){
			downAttackPlayer[i] = Game.spritesheet.getSprite(96+(i*16), 64, 16, 27);
		}
		for(int i = 0; i < 4; i++) {
			upAttackPlayer[i] = Game.spritesheet.getSprite(96+(i*16), 91, 16, 28);
		}
		for(int i = 0; i < 4; i++) {
			rightAttackPlayer[i] = Game.spritesheet.getSprite(52+(i*27), 119, 27, 16);
		}
		for(int i = 0; i < 4; i++) {
			leftAttackPlayer[i] = Game.spritesheet.getSprite(52+(i*27), 135, 27, 16);
		}
		Comemoration = Game.spritesheet.getSprite(16, 112, width, height);
		playerDamager = Game.spritesheet.getSprite(0, 32, width, height);
		downPlayer[0] = Game.spritesheet.getSprite(0, 16, width, height);
		downPlayer[1] = Game.spritesheet.getSprite(16, 16, width, height);
		upPlayer[0] = Game.spritesheet.getSprite(96, 16, width, height);
		upPlayer[1] = Game.spritesheet.getSprite(112, 16, width, height);
		rightPlayer[0] = Game.spritesheet.getSprite(32, 16, width, height);
		rightPlayer[1] = Game.spritesheet.getSprite(48, 16, width, height);
		leftPlayer[0] = Game.spritesheet.getSprite(64, 16, width, height);
		leftPlayer[1] = Game.spritesheet.getSprite(80, 16, width, height);		
		
		depth = 1;
	}

	public void update() {
		moved = false;
		if(right && World.isFree((int)(x+speed), this.getY()) && !isAttacking && !knockback_acti) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed), this.getY()) && !isAttacking && !knockback_acti) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		else if(up && World.isFree(this.getX(), (int)(y-speed)) && !isAttacking && !knockback_acti) {
			moved = true;
			dir = up_dir;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(), (int)(y+speed)) && !isAttacking && !knockback_acti) {
			moved = true;
			dir = down_dir;
			y+=speed;
		}
		
		
		MoveCamera();
		attackNormal();
		cooldawnAttackEspecial();
		checkCollisionObjects();
		//checkCollisionSwordSimple();
		//ckeckCollisionNextLevel();
		damagedAnimation();
		gameOver();
		knockback();
		
		if(getHeartContainer) {
			Game.gameState = "COMEMORATION";
			var_global.player_max_life += 1;
			maxLife = var_global.player_max_life;
			life = maxLife;
			var_global.player_life = var_global.player_max_life;
			return;
		}
		if(getSword) {
			Game.gameState = "COMEMORATION";
			var_global.player_isSword = true;
			isSword = var_global.player_isSword;
			attack = false;
			return;
		}
		
		
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
	
	public void knockback() {
		if(knockback_acti == true) {
			knockback_dir = dir;
			knockback_frames++;
			if(knockback_frames >= knockback_timer) {
				knockback_frames = 0;
				knockback_acti = false;
			}else {
				if(knockback_dir == right_dir || knockback_dir == left_dir) {
					if(World.isFree((int)(x+(-sign(knockback_dir) * 5)), this.getY()))
						x += -sign(knockback_dir) * 4;
				}else if(knockback_dir == up_dir || knockback_dir == down_dir) {
					if(World.isFree(this.getX(), (int)(y+(-sign(knockback_dir) * 4))))
						y += -sign(knockback_dir) * 4;
				}
			}
		}
	}
	
	public void cooldawnAttackEspecial() {
		if(cooldawnAttackEsp) {
			framesCoolAttackEsp++;
			if(framesCoolAttackEsp >= maxFramesCoolAttackEsp) {
				framesCoolAttackEsp = 0;
				cooldawnAttackEsp = false;
			}
		}
	}
	
	public void attackSpecial() {
		if(life == maxLife && cooldawnAttackEsp == false) {
			cooldawnAttackEsp = true;
			int dx = 0;
			int dy = 0;
			
			int ox = 0;
			int oy = 0;
			
			int width_swd = 16;
			int height_swd = 16;
			
			if(dir == right_dir || dir == left_dir) {
				dx = sign(dir);
				
				ox = sign(dir)*13;
				oy = 4;
				
				width_swd = 16;
				height_swd = 8;
			}else if(dir == up_dir || dir == down_dir) {
				dy = sign(dir);
				
				ox = 4;
				oy = sign(dir)*13;
				
				width_swd = 8;
				height_swd = 16;
			}
			
			sword_especial_attack esp_attack = new sword_especial_attack(this.getX()+ox, this.getY()+oy, width_swd, height_swd, null, dx, dy);
			Game.entities.add(esp_attack);
		}
	}
	
	public void attackNormal() {
		
		if(attack && var_global.player_isSword) {
			attackSpecial();
			if(isAttacking == false) {
				attack = false;
				isAttacking = true;
				ssw = new sword_simple_attack(this.getX(), this.getY(), 16, 16,null);
				Game.entities.add(ssw);
			}
		}
		
		if(isAttacking) {
			attackFrames++;
			if(attackFrames == maxAttackFrames) {
				attackFrames = 0;
				attackIndex++;
				if(attackIndex > this.maxAttackIndex) {
					attackIndex = 0;
					isAttacking = false;
					Game.entities.remove(ssw);
				}
			}
		}
	}
	
	public void gameOver() {
		if(life <= 0) {
			//Game over
			var_global.player_life = var_global.player_max_life;
			Game.gameState = "GAME OVER";
			var_global.CUR_LEVEL = 1;
		}
	}
	
	/*public void ckeckCollisionNextLevel() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof nextLevel) {
				if(isColliding(this, e)) {
					System.out.println("Proximo level!");
				}
			}
		}
	}*/
	
	public void checkCollisionObjects() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof nextLevel) {
				//Colisão com next Level
				if(isColliding(this, e)) {
					if(knockback_acti) {
						knockback_acti = false;
						dir = -knockback_dir;
					}
					((nextLevel) e).next_level = true;
				}
			}else if(e instanceof backLevel){
				if(isColliding(this, e)) {
					if(knockback_acti) {
						knockback_acti = false;
						dir = -knockback_dir;
					}
					((backLevel) e).back_level = true;
				}
			} if(e instanceof Life_Potion) {
				//Colisão com poção de vida
				if(isColliding(this, e)) {
					life = maxLife;
					var_global.player_life = life;
					if(life >= maxLife) {
						life = maxLife;
						var_global.player_life = life;
					}
					Game.entities.remove(i);
					return;
				}
			}else if(e instanceof Weapon_Sword_Simple) {
				//Colisão com a espada simplis
				if(isColliding(this, e)) {
					isSword = true;
					var_global.player_isSword = true;
					getSword = true;
					Game.entities.remove(i);
					return;
				}
			}else if(e instanceof heart_Container) {
				//Colisão com o heart Container
				if(isColliding(this, e)) {
					this.getHeartContainer = true;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}
	
	/*public void checkCollisionSwordSimple() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Weapon_Sword_Simple) {
				if(isColliding(this, e)) {
					isSword = true;
					getSword = true;
					Game.entities.remove(i);
					return;
				}
			}
		}
	}*/
	
	private void damagedAnimation() {
		if(isDamaged) {
			invecible = true;
			framesD++;
			knockback_acti = true;
			if(framesD == 8) {
				framesD = 0;
				isDamaged = false;
			}
		}
		
		if(invecible) {
			framesI++;
			if(framesI == 100) {
				framesI = 0;
				invecible = false;
			}
		}
	}
	
	public void MoveCamera() {
		Camera.x = Camera.Clamp(Game.player.getX() - (Game.WIDTH/2), 0, (World.WIDTH*16) - Game.WIDTH);
		Camera.y = Camera.Clamp(Game.player.getY() - (Game.HEIGHT/2), 0, (World.HEIGHT*16) - Game.HEIGHT);
	}
	
	
	public void render(Graphics g) {
		if(Game.gameState == "NORMAL") {
			if(!isDamaged && !getSword && !isAttacking) {
				if(dir == up_dir) {
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == down_dir) {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == right_dir) {
					g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == left_dir) {
					g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}else if(isDamaged) {
				g.drawImage(playerDamager, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(isAttacking && var_global.player_isSword && isAttacking) {
				if(dir == up_dir) {
					g.drawImage(upAttackPlayer[attackIndex], this.getX() - Camera.x, this.getY() - Camera.y - 12, null);
				}else if(dir == down_dir) {
					g.drawImage(downAttackPlayer[attackIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == right_dir) {
					g.drawImage(rightAttackPlayer[attackIndex], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else if(dir == left_dir) { 
					g.drawImage(leftAttackPlayer[attackIndex], this.getX() - Camera.x - 12, this.getY() - Camera.y, null);
				}
			}
		}else if(Game.gameState == "COMEMORATION") {
			if(getHeartContainer) {
				g.drawImage(Comemoration, this.getX() - Camera.x, this.getY() - Camera.y, null);
				g.drawImage(Entity.HEART_CONTAINER, this.getX() - Camera.x, this.getY() - Camera.y - 16, null);
			}
			if(getSword) {
				g.drawImage(Comemoration, this.getX() - Camera.x, this.getY() - Camera.y, null);
				g.drawImage(Entity.WEAPON_SWORD_SIMPLE, this.getX() - Camera.x, this.getY() - Camera.y - 12, null);
			}
		}
	}
}

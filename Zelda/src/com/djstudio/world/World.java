package com.djstudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.djstudio.entites.Enemy;
import com.djstudio.entites.Entity;
import com.djstudio.entites.Life_Potion;
import com.djstudio.entites.Npc;
import com.djstudio.entites.Particle;
import com.djstudio.entites.Player;
import com.djstudio.entites.Weapon_Sword_Simple;
import com.djstudio.entites.backLevel;
import com.djstudio.entites.enemyBat;
import com.djstudio.entites.enemyGuardian;
import com.djstudio.entites.enemySkeleton;
import com.djstudio.entites.enemySlime;
import com.djstudio.entites.heart_Container;
import com.djstudio.entites.nextLevel;
import com.djstudio.graphics.Spritesheet;
import com.djstudio.main.Game;
import com.djstudio.main.var_global;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixel = pixels[xx+yy*map.getWidth()];
					
					if(var_global.CUR_LEVEL < 3) {
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR);
					}else {
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_2_FLOOR);
					}
					
					if(pixel == 0xFF000000) {
						//floor
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR);
					}else if(pixel == 0xFF5F5F77) {
						//floor 2
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_2_FLOOR);
					}else if(pixel == 0xFF261919) {
						//floor variant
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR_VARIANT);
					}else if(pixel == 0xFF3D3D4C) {
						//floor variant 2
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_2_FLOOR_VARIANT);
					}else if(pixel == 0xFFFFFFFF) {
						//wall
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_WALL);
					}else if(pixel == 0xFFC84C0C) {
						//base da montanha central
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BASE_MONTANHA);
					}else if(pixel == 0xFFA03B09) {
						//base da montanha direita
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BASE_MONTANHA_DIREITA);
					}else if(pixel == 0xFF823007) {
						//base da montanha esquerda
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BASE_MONTANHA_ESQUERDA);
					}else if(pixel == 0xFF6B2300) {
						//Topo da montanha central
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TOPO_MONTANHA);
					}else if(pixel == 0xFF872D00) {
						//Topo da montanha direita
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TOPO_MONTANHA_DIREITA);
					}else if(pixel == 0xFF381200) {
						//Topo da montanha esquerda
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TOPO_MONTANHA_ESQUERDA);
					}else if(pixel == 0xFF244C3A) {
						//base da montanha central a partir daqui
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BASE_2_MONTANHA);
					}else if(pixel == 0xFF65D3A2) {
						//base da montanha direita
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BASE_2_MONTANHA_DIREITA);
					}else if(pixel == 0xFF45916F) {
						//base da montanha esquerda
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_BASE_2_MONTANHA_ESQUERDA);
					}else if(pixel == 0xFF17774C) {
						//Topo da montanha central
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TOPO_2_MONTANHA);
					}else if(pixel == 0xFF0A3320) {
						//Topo da montanha direita
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TOPO_2_MONTANHA_DIREITA);
					}else if(pixel == 0xFF2AD387) {
						//Topo da montanha esquerda
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TOPO_2_MONTANHA_ESQUERDA);
					}else if(pixel == 0xFFA56D6D) {
						//Estatua
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_ESTATUA);
					}else if(pixel == 0xFFC4A93A) {
						//Estatua 2
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_2_ESTATUA);
					}else if(pixel == 0xFFA0542E) {
						//arbusto?
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_ARBUSTO_VARIANT);
					}else if(pixel == 0xFFC67517) {
						//pequeno arbusto
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_ARBUSTO);
					}else if(pixel == 0xFFC4A98B) {
						//arbusto nivel 2
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_2_ARBUSTO_VARIANT);
					}else if(pixel == 0xFF8E7B65) {
						//pequeno arbusto nivel2
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_2_ARBUSTO);
					}else if(pixel == 0xFFEABA1C) {
						//lapide
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_LAPIDE);
					}else if(pixel == 0xFFC17C32) {
						//lapide 2
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_2_LAPIDE);
					}else if(pixel == 0xFF2038EC) {
						//RIO MEIO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_MEIO);
					}else if(pixel == 0xFFA4B0F2) {
						//RIO MEIO DIREITO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_MEIO_DIREITO);
					}else if(pixel == 0xFF6076F2) {
						//RIO MEIO ESQUERDO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_MEIO_ESQUERDO);
					}else if(pixel == 0xFF8797F2) {
						//RIO BASE
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_BASE);
					}else if(pixel == 0xFF101E77) {
						//RIO BASE DIREITO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_BASE_DIREITO);
					}else if(pixel == 0xFF070F38) {
						//RIO BASE ESQUERDO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_BASE_ESQUERDO);
					}else if(pixel == 0xFFB097F2) {
						//RIO TOPO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_TOPO);
					}else if(pixel == 0xFF0C1756) {
						//RIO TOPO DIREITO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_TOPO_DIREITO);
					}else if(pixel == 0xFF16289E) {
						//RIO TOPO ESQUERDO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_RIO_TOPO_ESQUERDO);
					}else if(pixel == 0xFF75C67D) {
						//TEMPLO MEIO
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TEMPLO_MEIO);
						if(!var_global.player_isSword) {
							Weapon_Sword_Simple wss = new Weapon_Sword_Simple(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.WEAPON_SWORD_SIMPLE);
							Game.entities.add(wss);
						}
					}else if(pixel == 0xFF05C62F) {
						//TEMPLO MEIO DIREITO
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TEMPLO_MEIO_DIREITO);
					}else if(pixel == 0xFFA2C667) {
						//TEMPLO MEIO ESQUERDO
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TEMPLO_MEIO_ESQUERDO);
					}else if(pixel == 0xFF4BC670) {
						//TEMPLO TOPO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TEMPLO_TOPO);
					}else if(pixel == 0xFF5FC65B) {
						//TEMPLO TOPO DIREITO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TEMPLO_TOPO_DIREITO);
					}else if(pixel == 0xFF00C472) {
						//TEMPLO TOPO ESQUERDO
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_TEMPLO_TOPO_ESQUERDO);
					}else if(pixel == 0xFF58B6B7) {
						//PLATAFORMA CIMA DIREITA
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_PLATAFORMA_CIMA_DIREITA);
					}else if(pixel == 0xFF7AFCFF) {
						//PLATAFORMA CIMA ESQUERDA
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_PLATAFORMA_CIMA_ESQUERDO);
					}else if(pixel == 0xFF275051) {
						//PLATAFORMA BAIXO DIREITO
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_PLATAFORMA_BAIXO_DIREITA);
					}else if(pixel == 0xFF3E8082) {
						//PLATAFORMA BAIXO ESQUERDO
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_PLATAFORMA_BAIXO_ESQUERDO);
					}else if(pixel == 0xFF262B44) {
						//PLATAFORMA DE RIO
						tiles[xx+(yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_PLATFORM_RIO);
					}else if(pixel == 0xFF00FF00){
						//Next Level
						nextLevel next_level = new nextLevel(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, null);
						Game.entities.add(next_level);
					}else if(pixel == 0xFF00FFA3) {
						backLevel back_level = new backLevel(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, null);
						Game.entities.add(back_level);
					}
					else if(pixel == 0xFF0000FF) {
						//Player
						Game.player.setX(xx*TILE_SIZE);
						Game.player.setY(yy*TILE_SIZE);
					}else if(pixel == 0xFFF1F9B8){
						//NPC
						Npc npc = new Npc(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, null);
						Game.entities.add(npc);
					}else if(pixel == 0xFFFF0000) {
						//Enemy
						enemySlime enemyslime = new enemySlime(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.ENEMY_ENTITY);
						Game.entities.add(enemyslime);
						Game.enemys.add(enemyslime);
					}else if(pixel == 0xFFCE0000) {
						//Enemy Skeleton
						enemySkeleton enemySkeletons = new enemySkeleton(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.ENEMY_ENTITY);
						Game.entities.add(enemySkeletons);
						Game.enemys.add(enemySkeletons);
					}else if(pixel == 0xFF722727) {
						//Enemy Bat
						enemyBat enemyBats = new enemyBat(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.ENEMY_ENTITY);
						Game.entities.add(enemyBats);
						Game.enemys.add(enemyBats);
					}else if(pixel == 0xFFFF5959) {
						//Enemy Bat
						enemyGuardian enemyGuardian = new enemyGuardian(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.ENEMY_ENTITY);
						Game.entities.add(enemyGuardian);
						Game.enemys.add(enemyGuardian);
					}else if(pixel == 0xFFFF9300) {
						//Espada Simples
						Weapon_Sword_Simple wss = new Weapon_Sword_Simple(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.WEAPON_SWORD_SIMPLE);
						Game.entities.add(wss);
					}else if(pixel == 0xFFFF6051) {
						//Life Potion
						Life_Potion life_potion = new Life_Potion(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.LIFE_POTION);
						Game.entities.add(life_potion);
					}else if(pixel == 0xFFFF0AFF) {
						heart_Container heart_container = new heart_Container(xx*TILE_SIZE, yy*TILE_SIZE, 16, 16, Entity.HEART_CONTAINER);
						Game.entities.add(heart_container);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;

		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xNext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (yNext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
	public static void generateParticle(int amount, int x, int y) {
		for(int i = 0; i < amount; i++) {
			Game.entities.add(new Particle(x, y, 1, 1, null));
		}
	}
	
	public static void restartGame(String Level, boolean returnWorld) {
		if(returnWorld) {
			Game.entities = new ArrayList<Entity>();
			Game.enemys = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/Sprites.png");
			Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(0, 16, 16, 16));
			Game.entities.add(Game.player);
			Game.world = new World("/"+Level);
			return;
		}else {
			Game.entities = new ArrayList<Entity>();
			Game.enemys = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/Sprites.png");
			Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(0, 16, 16, 16));
			Game.entities.add(Game.player);
			Game.world = new World("/"+Level);
		}
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4)+1;
		
		for(int xx = xstart;xx <= xfinal; xx++) {
			for(int yy = ystart;yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}

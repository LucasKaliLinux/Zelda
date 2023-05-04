package com.djstudio.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.djstudio.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(48, 304, 16, 16);
	public static BufferedImage TILE_FLOOR_VARIANT = Game.spritesheet.getSprite(48, 288, 16, 16);
	
	public static BufferedImage TILE_2_FLOOR = Game.spritesheet.getSprite(48, 304-32, 16, 16);
	public static BufferedImage TILE_2_FLOOR_VARIANT = Game.spritesheet.getSprite(48, 288-32, 16, 16);
	
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	
	public static BufferedImage TILE_BASE_MONTANHA = Game.spritesheet.getSprite(16, 304, 16, 16);
	public static BufferedImage TILE_BASE_MONTANHA_DIREITA = Game.spritesheet.getSprite(32, 304, 16, 16);
	public static BufferedImage TILE_BASE_MONTANHA_ESQUERDA = Game.spritesheet.getSprite(0, 304, 16, 16);
	public static BufferedImage TILE_TOPO_MONTANHA = Game.spritesheet.getSprite(16, 288, 16, 16);
	public static BufferedImage TILE_TOPO_MONTANHA_DIREITA = Game.spritesheet.getSprite(32, 288, 16, 16);
	public static BufferedImage TILE_TOPO_MONTANHA_ESQUERDA = Game.spritesheet.getSprite(0, 288, 16, 16);
	
	public static BufferedImage TILE_BASE_2_MONTANHA = Game.spritesheet.getSprite(16, 304-32, 16, 16);
	public static BufferedImage TILE_BASE_2_MONTANHA_DIREITA = Game.spritesheet.getSprite(32, 304-32, 16, 16);
	public static BufferedImage TILE_BASE_2_MONTANHA_ESQUERDA = Game.spritesheet.getSprite(0, 304-32, 16, 16);
	public static BufferedImage TILE_TOPO_2_MONTANHA = Game.spritesheet.getSprite(16, 288-32, 16, 16);
	public static BufferedImage TILE_TOPO_2_MONTANHA_DIREITA = Game.spritesheet.getSprite(32, 288-32, 16, 16);
	public static BufferedImage TILE_TOPO_2_MONTANHA_ESQUERDA = Game.spritesheet.getSprite(0, 288-32, 16, 16);
	
	public static BufferedImage TILE_ESTATUA = Game.spritesheet.getSprite(64, 304, 16, 16);
	public static BufferedImage TILE_2_ESTATUA = Game.spritesheet.getSprite(64, 304-48, 16, 16);
	
	public static BufferedImage TILE_ARBUSTO_VARIANT = Game.spritesheet.getSprite(64, 288, 16, 16);
	public static BufferedImage TILE_ARBUSTO = Game.spritesheet.getSprite(80, 288, 16, 16);
	
	public static BufferedImage TILE_2_ARBUSTO_VARIANT = Game.spritesheet.getSprite(64, 288-48, 16, 16);
	public static BufferedImage TILE_2_ARBUSTO = Game.spritesheet.getSprite(80, 288-48, 16, 16);
	
	public static BufferedImage TILE_LAPIDE = Game.spritesheet.getSprite(80, 304, 16, 16);
	public static BufferedImage TILE_2_LAPIDE = Game.spritesheet.getSprite(80, 304-48, 16, 16);
	
	public static BufferedImage TILE_RIO_MEIO = Game.spritesheet.getSprite(112, 288, 16, 16);
	public static BufferedImage TILE_RIO_MEIO_DIREITO = Game.spritesheet.getSprite(128, 288, 16, 16);
	public static BufferedImage TILE_RIO_MEIO_ESQUERDO = Game.spritesheet.getSprite(96, 288, 16, 16);
	public static BufferedImage TILE_RIO_BASE = Game.spritesheet.getSprite(112, 304, 16, 16);
	public static BufferedImage TILE_RIO_BASE_DIREITO = Game.spritesheet.getSprite(128, 304, 16, 16);
	public static BufferedImage TILE_RIO_BASE_ESQUERDO = Game.spritesheet.getSprite(96, 304, 16, 16);
	public static BufferedImage TILE_RIO_TOPO = Game.spritesheet.getSprite(112, 272, 16, 16);
	public static BufferedImage TILE_RIO_TOPO_DIREITO = Game.spritesheet.getSprite(128, 272, 16, 16);
	public static BufferedImage TILE_RIO_TOPO_ESQUERDO = Game.spritesheet.getSprite(96, 272, 16, 16);
	
	public static BufferedImage TILE_TEMPLO_MEIO = Game.spritesheet.getSprite(160, 304, 16, 16);
	public static BufferedImage TILE_TEMPLO_MEIO_DIREITO = Game.spritesheet.getSprite(176, 304, 16, 16);
	public static BufferedImage TILE_TEMPLO_MEIO_ESQUERDO = Game.spritesheet.getSprite(144, 304, 16, 16);
	public static BufferedImage TILE_TEMPLO_TOPO = Game.spritesheet.getSprite(160, 288, 16, 16);
	public static BufferedImage TILE_TEMPLO_TOPO_DIREITO = Game.spritesheet.getSprite(176, 288, 16, 16);
	public static BufferedImage TILE_TEMPLO_TOPO_ESQUERDO = Game.spritesheet.getSprite(144, 288, 16, 16);
	
	public static BufferedImage TILE_PLATAFORMA_CIMA_DIREITA = Game.spritesheet.getSprite(208, 288, 16, 16);
	public static BufferedImage TILE_PLATAFORMA_CIMA_ESQUERDO = Game.spritesheet.getSprite(192, 288, 16, 16);
	public static BufferedImage TILE_PLATAFORMA_BAIXO_DIREITA = Game.spritesheet.getSprite(208, 304, 16, 16);
	public static BufferedImage TILE_PLATAFORMA_BAIXO_ESQUERDO = Game.spritesheet.getSprite(192, 304, 16, 16);
	
	public static BufferedImage TILE_PLATFORM_RIO = Game.spritesheet.getSprite(64, 272, 16, 16);
	
	private BufferedImage sprite;
	private int x;
	private int y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x-Camera.x, y-Camera.y, null);
	}
}

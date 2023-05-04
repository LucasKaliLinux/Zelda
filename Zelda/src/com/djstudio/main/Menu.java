package com.djstudio.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.djstudio.world.World;

public class Menu {
	
	int x,y;
	
	private BufferedImage Sword_menu;
	
	private int FramesTransparency = 255;
	private int Frames = 0;
	private int FramesLight = 0;
	private boolean animation = true;
	private boolean flash = false;

	public String[] options = {"New Game","Load Game","Exit"};
	
	public int currentOptions = 0;
	public int maxOption = options.length - 1; 
	
	public boolean up,down,enter;
	
	public static boolean pause;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
	
	public Menu() {
		
		x = Game.WIDTH*Game.SCALE;
		y = -70;
		
		Sword_menu = Game.spritesheet.getSprite(288, 160, 32, 32);
	}
	
	public void update() {
		Sound.Menu.loop();
		File file = new File("save.zelda");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		if(up) {
			up = false;
			currentOptions--;
			if(currentOptions < 0) {
				currentOptions = maxOption;
			}
		}
		if(down) {
			down = false;
			currentOptions++;
			if(currentOptions > maxOption) {
				currentOptions = 0;
			}
		}
		
		if(enter && animation == false) {
			enter = false;
			if(options[currentOptions] == "New Game") {
				Game.gameState = "NORMAL";
				Sound.Menu.stop();
				if(pause == false) {
					file = new File("save.zelda");
					file.delete();
				}
				pause = false;
			}else if(options[currentOptions] == "Load Game"){
				Sound.Menu.stop();
				file = new File("save.zelda");
				if(file.exists()) {
					String saver = loadGame(15);
					applySave(saver);
				}
			} if(options[currentOptions] == "Exit") {
				System.exit(1);
			}
		}else if(enter && animation == true) {
			enter = false;
			animation = false;
			flash = false;
			x = 220;
			FramesTransparency = 100;
		}
		
		
		if(animation == true) {
			Frames++;
			if(Frames == 5) {
				Frames = 0;
				FramesTransparency--;
				
				if(FramesTransparency <= 100) {
					FramesTransparency = 100;
				}
			}
			x-=2;
			if(x <= 220) {
				flash = true;
				//animation = false;
			}
		}
	}
	
	public static void applySave(String str) {
		try {
			String[] spl = str.split("/");
			for(int i = 0;i < spl.length; i++) {
				String[] spl2 = spl[i].split(":");
				switch(spl2[0]) {
					case "vida":
						Game.player.life = Integer.parseInt(spl2[1]);
						var_global.player_life = Integer.parseInt(spl2[1]);
						break;
					case "isSword":
						var_global.player_isSword = var_global.intToBoolean(Integer.parseInt(spl2[1]));
						break;
					case "maxLife":
						var_global.player_max_life = Integer.parseInt(spl2[1]);
						break;
					case "level":
						var_global.CUR_LEVEL = Integer.parseInt(spl2[1]);
						World.restartGame("level"+spl2[1]+".png", true);
						Game.gameState = "NORMAL";
						pause = false;
						break;
				}
			}
		}catch(Exception e) {}
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		try {
			write = new BufferedWriter(new FileWriter("save.zelda"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int n = 0; n < value.length; n++) {
				value[n]+=encode;
				current+=value[n];
			}
			try {
				write.write(current);
				if(i < val1.length - 1) {
					write.newLine();
				}
			}catch(IOException e) {}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.zelda");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.zelda"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}					
				}catch(IOException e) {}
			}catch(FileNotFoundException e) {}
		}
		
		return line;
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(new Color(0, 0, 0, FramesTransparency));
		g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		
		g.drawImage(Sword_menu, x, y, 320, 320, null); //220, -70
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 60));
		g.drawString("Zelda Fake", (Game.WIDTH*Game.SCALE)/2 - 150, (Game.HEIGHT*Game.SCALE)/2 - 150);
		
		if(flash == true) {
			g.setColor(Color.white);
			g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			FramesLight++;
			if(FramesLight >= 5) {
				animation = false;
				flash = false;
				FramesLight = 0;
			}
		}
		
		if(animation == false) {
			
			g.setFont(new Font("arial", Font.BOLD, 36));
			
			if(pause == false) {
				g.drawString("New Game", (Game.WIDTH*Game.SCALE)/2 - 100, 220);
			}else {
				g.drawString("Continue", (Game.WIDTH*Game.SCALE)/2 - 100, 220);
			}
			g.drawString("Load Game", (Game.WIDTH*Game.SCALE)/2 - 100, 280);
			g.drawString("Exit", (Game.WIDTH*Game.SCALE)/2 - 100, 340);
			
			if(options[currentOptions] == "New Game") {
				//g.drawString("=>", (Game.WIDTH*Game.SCALE)/2 - 190, 220);
				g.drawImage(Game.spritesheet.getSprite(79, 119,27,16), (Game.WIDTH*Game.SCALE)/2 - 190, 220 - 32, 32, 32, null);
				g.drawImage(Game.spritesheet.getSprite(106, 136,27,15), (Game.WIDTH*Game.SCALE)/2 + 140, 220 - 32, 32, 32, null);
			}else if(options[currentOptions] == "Load Game") {
				//g.drawString("=>", (Game.WIDTH*Game.SCALE)/2 - 190, 280);
				g.drawImage(Game.spritesheet.getSprite(79, 119,27,16), (Game.WIDTH*Game.SCALE)/2 - 190, 280 - 32, 32, 32, null);
				g.drawImage(Game.spritesheet.getSprite(106, 136,27,15), (Game.WIDTH*Game.SCALE)/2 + 140, 280 - 32, 32, 32, null);
			}else if(options[currentOptions] == "Exit") {
				//g.drawString("=>", (Game.WIDTH*Game.SCALE)/2 - 190, 340);
				g.drawImage(Game.spritesheet.getSprite(79, 119,27,16), (Game.WIDTH*Game.SCALE)/2 - 190, 340 - 32, 32, 32, null);
				g.drawImage(Game.spritesheet.getSprite(106, 136,27,15), (Game.WIDTH*Game.SCALE)/2 + 140, 340 - 32, 32, 32, null);
			}
		
			g.setFont(new Font("arial", Font.BOLD, 12));
			g.drawString("Nintendo Please don't sue me!!!", (Game.WIDTH*Game.SCALE)/2 - 300, 500);
			
			g.setFont(new Font("arial", Font.BOLD, 12));
			g.drawString("Sprites created by Mister Mike.", (Game.WIDTH*Game.SCALE)/2 +150, 500);
		}
	}
}

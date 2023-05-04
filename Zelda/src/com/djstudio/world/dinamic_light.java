package com.djstudio.world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.djstudio.main.Game;
import com.djstudio.main.Pixel;

public class dinamic_light {
	
	public BufferedImage lightmap;
	public int[] lightMapPixels;
	
	public dinamic_light(String path) {
		try {
			lightmap = ImageIO.read(getClass().getResource(path));
			lightMapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
			lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightMapPixels, 0, lightmap.getWidth());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void applyLight() {
		for(int xx = 0; xx < lightmap.getWidth(); xx++) {
			for(int yy = 0; yy < lightmap.getHeight(); yy++) {
				if(lightMapPixels[xx+(yy*lightmap.getWidth())] == 0xFF000000) {
					int pixel = Pixel.getLightBlend(Game.pixels[xx+(yy*lightmap.getWidth())], 0x212121, 0);
					Game.pixels[xx+(yy*lightmap.getWidth())] = pixel;
				}
			}
		}
	}
}

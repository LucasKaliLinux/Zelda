package com.djstudio.entites;

import java.awt.image.BufferedImage;

public class heart_Container extends Entity{

	private int frames = 0; 
	private int hight_max = 0;
	private int t;
	private int ox,oy;
	
	public heart_Container(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		//Da para fazer uma brincadeira legal aqui emm, esqueci disso não(Circulo)!!!!
		/*t++;
		int ox = (int)(Math.cos(t/100.0) * 2);
		int oy = (int)(Math.sin(t/100.0) * 2);
		
		System.out.println("ox: "+ox+", OY: "+oy);
		
		x -= ox;
		y -= oy;*/
		
		//Mas uma coisa legal emm, nao esqeci disso, "rotaciona" em forma de quadrado!!!
		/*t++;
		int ox = (int)(Math.cos(t/100.0) * 1.2);
		int oy = (int)(Math.sin(t/100.0) * 1.2);
		
		System.out.println("ox: "+ox+", OY: "+oy);
		
		x -= ox;
		y -= oy;*/
		
	}

}

import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.JComponent;

public class Enemy{
	
	public int xPos = 855;
	public int yPos = 425;
	public int width = 0;
	public int height = 0;
	public int health = 20;
	public boolean idle = true;
	public boolean alive = true;
	public boolean contact = false;

	public BufferedImage image;
	public URL resource = getClass().getResource("monster/idle0.png");

	public Enemy(Draw comp){
		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		animate(comp);
	}

	public Enemy(int xPass, int yPass, Draw comp){
		xPos = xPass;
		yPos = yPass;

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}

		height = image.getHeight();
		width = image.getWidth();

		animate(comp);
	}

	public void animate(Draw compPass){
		Thread monThread = new Thread(new Runnable(){
			public void run(){
				while(idle){
					for(int ctr = 0; ctr < 18; ctr++){
						try {
							if(ctr==17){
								resource = getClass().getResource("monster/idle0.png");
							}
							else{
								resource = getClass().getResource("monster/idle"+ctr+".png");
							}
							
							try{
								image = ImageIO.read(resource);
							}
							catch(IOException e){
								e.printStackTrace();
							}

					        compPass.repaint();
					        Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					if(health<=0){
						die(compPass);
					}
				}
			}
		});
		monThread.start();
	}

	public void moveTo(int toX, int toY){
		if(xPos<toX){
			xPos++;
		}
		else if(xPos>toX){
			xPos--;
		}

		if(yPos<toY){
			yPos++;
		}
		else if(yPos>toY){
			yPos--;
		}
	}

	public void die(Draw compPass){
		idle = false;
		if(alive){
			Thread monThread = new Thread(new Runnable(){
				public void run(){
					for(int ctr = 0; ctr < 15; ctr++){
						try {					
							resource = getClass().getResource("monster/hurt"+ctr+".png");
							
							try{
								image = ImageIO.read(resource);
							}
							catch(IOException e){
								e.printStackTrace();
							}
					        compPass.repaint();
					        Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			monThread.start();
		}
		alive = false;
	}

	public Rectangle getBounds(){
		return (new Rectangle(xPos, yPos, image.getWidth(), image.getHeight()));
	}
}
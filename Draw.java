import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;

public class Draw extends JComponent{

	private BufferedImage image;
	private BufferedImage backgroundPic;
	private URL resource = getClass().getResource("idle0.png");

	//circle's position
	public int x = 190;
	public int y = 500;
	public int height = 0;
	public int width = 0;
	public int health = 500;
	int backX = 0;
	int backXx = 3;

	//animation states
	public int state = 0;

	// enemy
	LinkedList<Enemy> monsterList = new LinkedList<Enemy>();

	Enemy monster1;
	Enemy monster2;
	Enemy monster3;

	public Draw(){
		spawnEnemy();

		monster1 = new Enemy(500, 300, this);
		monster2 = new Enemy(500, 800, this);
		monster3 = new Enemy(500, 400, this);

		monsterList.add(monster1);
		monsterList.add(monster2);
		monsterList.add(monster3);
		
		try{
			image = ImageIO.read(resource);
			backgroundPic = ImageIO.read(getClass().getResource("background.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		}

		height = image.getHeight();
		width = image.getWidth();

		startGame();
	}

	public void startGame(){
		Thread gameThread = new Thread(new Runnable(){
			public void run(){
				while(true){
					if(backX > getWidth() * -1){
						backX -= backXx;
					}else{
						backX = 0;
					}	
					try{
						for(int c = 0; c < monsterList.size(); c++){
							if(monsterList!=null){
								monsterList.get(c).moveTo(x,y);
								repaint();
							}
						}
						Thread.sleep(100);
					} catch (InterruptedException e) {
							e.printStackTrace();
					}
				}
			}
		});
		gameThread.start();
	}

	public void spawnEnemy(){
		Enemy monsterCreated = new Enemy(500, 500, this);
		monsterList.add(monsterCreated);
		this.repaint();

	}

	public void reloadImage(){
		if(state == 0){
			resource = getClass().getResource("run0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("run1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("run2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("run3.png");
			state = 0;
		}
		else if(state == 4){
			resource = getClass().getResource("run4.png");
		}
		else if(state == 5){
			resource = getClass().getResource("run5.png");
			state = 0;
		}
		else if(state == 6){
			resource = getClass().getResource("run6.png");
			state = 0;
		}
		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void attackAnimation(){
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 4; ctr++){
					try {
						if(ctr==3){
							resource = getClass().getResource("run0.png");
						}
						else{
							resource = getClass().getResource("attack"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				checkCollision();
						for(Enemy monster : monsterList){
							if(monster.contact){
								monster.health = monster.health - 10;
							}
						}
			}
		});
		thread1.start();
	}

	public void jumpAnimation(){
		Thread thread2 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 5; ctr++){
					try {
						if(ctr==4){
							resource = getClass().getResource("jump0.png");
						}
						else{
							resource = getClass().getResource("jump"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				checkCollision();
						for(Enemy monster : monsterList){
							if(monster.contact){
								monster.health = monster.health - 10;
							}
						}
			}
		});
		thread2.start();
	}

public void teleportAnimation(){
		Thread thread3 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 9; ctr++){
					try {
						if(ctr==8){
							resource = getClass().getResource("teleport0.png");
						}
						else{
							resource = getClass().getResource("teleport"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}
				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				checkCollision();
						for(Enemy monster : monsterList){
							if(monster.contact){
								monster.health = monster.health - 10;
							}
						}
			}
		});
		thread3.start();
	}

	public void teleport(){
		teleportAnimation();
	}

	public void jump(){
		jumpAnimation();
	}

	public void attack(){
		attackAnimation();
	}

	public void moveRight(){
		x = x + 5;
		state++;
		repaint();
		reloadImage();
		checkCollision();
	}

	public void moveLeft(){
		x = x - 5;
		state++;
		repaint();
		reloadImage();
		checkCollision();
	}

	public void checkCollision(){
		Rectangle playerBounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
    	for(Enemy monsters: monsterList){
    		if(playerBounds.intersects(monsters.getBounds())){
    			monsters.contact = true;
    		}
    	}

    	for(Enemy monsters: monsterList){
    	if(monsters.getBounds().intersects(playerBounds)){
    		health--;
    		}
    	}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backgroundPic, backX, 0, this);
		g.drawImage(backgroundPic, backX + getWidth(), 0, this);
		g.drawImage(image, x, y, this);
		g.setColor(Color.RED);
		g.fillRect(5, 5, health*5, 30);

		g.drawImage(monster1.image, monster1.xPos, monster1.yPos, this);
		g.drawImage(monster2.image, monster2.xPos, monster2.yPos, this);
		g.drawImage(monster3.image, monster3.xPos, monster3.yPos, this);

		for(Enemy monster:monsterList){
			g.drawImage(monster.image, monster.xPos, monster.yPos, this);
			g.setColor(Color.GREEN);
			g.fillRect(monster.xPos, monster.yPos-5, monster.health, 5);
	}
}
}
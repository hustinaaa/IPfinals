import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener{

	Draw drawing;

	public MyFrame(){
		this.drawing = new Draw();
	}

	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W){
			drawing.moveUp();
		}
		else if(e.getKeyCode() == KeyEvent.VK_D){
			drawing.moveRight();
		}
		else if(e.getKeyCode() == KeyEvent.VK_S){
			drawing.moveDown();
		}
		else if(e.getKeyCode() == KeyEvent.VK_A){
			drawing.moveLeft();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			drawing.attack();
		}
	}

	public void keyReleased(KeyEvent e){

	}

	public void keyTyped(KeyEvent e){
		
	}

	public static void main(String args[]){
		MyFrame gameFrame = new MyFrame();
		gameFrame.setSize(1000,700);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.getContentPane().add(gameFrame.drawing);
		gameFrame.addKeyListener(gameFrame);
		System.out.println("GAME DEVELOPMENT");
	}
}
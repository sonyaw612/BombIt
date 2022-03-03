import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public abstract class GameStart extends Applet implements Runnable, KeyListener, MouseListener {
	
	boolean[] pressing = new boolean[1024];
	
	public final static int UP = KeyEvent.VK_UP;
	public final static int DN = KeyEvent.VK_DOWN;
	public final static int LT = KeyEvent.VK_LEFT;
	public final static int RT = KeyEvent.VK_RIGHT;

	public final static int CTRL   = KeyEvent.VK_CONTROL;
	public final static int SHIFT  = KeyEvent.VK_SHIFT;
	public final static int COMMA  = KeyEvent.VK_COMMA;
	public final static int PERIOD = KeyEvent.VK_PERIOD;
	public final static int SPACE = KeyEvent.VK_SPACE;

	public final static int A = KeyEvent.VK_A;
	public final static int B = KeyEvent.VK_B;
	public final static int C = KeyEvent.VK_C;
	public final static int D = KeyEvent.VK_D;
	public final static int E = KeyEvent.VK_E;
	public final static int F = KeyEvent.VK_F;
	public final static int G = KeyEvent.VK_G;
	public final static int H = KeyEvent.VK_H;
	public final static int I = KeyEvent.VK_I;
	public final static int J = KeyEvent.VK_J;
	public final static int K = KeyEvent.VK_K;
	public final static int L = KeyEvent.VK_L;
	public final static int M = KeyEvent.VK_M;
	public final static int N = KeyEvent.VK_N;
	public final static int O = KeyEvent.VK_O;
	public final static int P = KeyEvent.VK_P;
	public final static int Q = KeyEvent.VK_Q;
	public final static int R = KeyEvent.VK_R;
	public final static int S = KeyEvent.VK_S;
	public final static int T = KeyEvent.VK_T;
	public final static int U = KeyEvent.VK_U;
	public final static int V = KeyEvent.VK_V;
	public final static int W = KeyEvent.VK_W;
	public final static int X = KeyEvent.VK_X;
	public final static int Y = KeyEvent.VK_Y;
	public final static int Z = KeyEvent.VK_Z;

	public final static int _0 = KeyEvent.VK_0;
	public final static int _1 = KeyEvent.VK_1;
	public final static int _2 = KeyEvent.VK_2;
	public final static int _3 = KeyEvent.VK_3;
	public final static int _4 = KeyEvent.VK_4;
	public final static int _5 = KeyEvent.VK_5;
	public final static int _6 = KeyEvent.VK_6;
	public final static int _7 = KeyEvent.VK_7;
	public final static int _8 = KeyEvent.VK_8;
	public final static int _9 = KeyEvent.VK_9;
	
	int mx;
	int my;
	
	Thread t;
		
	private boolean endGame = false;

	public void init() {
		
		requestFocus();				// Accept keystrokes
		
		setSize(1200, 700);
		
		addKeyListener(this);
		
		addMouseListener(this);
		
		initialize();
		
		t = new Thread(this);
		
		t.start();
		
	}
		
	@Override
	public void run() { 

		while(true) { //GAME LOOP			
			if(!endGame) {
				
				//ACTION EVENTS
				respond_To_Keyboard_Input();
				move_Computer_Controlled_Entities();
				resolve_Collisions();
				checkGameStatus();
			}
		
				repaint();
				
				try { t.sleep(20); } 
				catch(Exception e) {};
		}
	}
	
	public void paint(Graphics pen) {
		pen.setColor(Color.BLACK);
	};
	
	public void initialize() {}

	public abstract void respond_To_Keyboard_Input();
	
	public abstract void move_Computer_Controlled_Entities();
	
	public abstract void resolve_Collisions();
	
	public abstract void checkGameStatus();


// ------------------- KEY_EVENT -------------------

	@Override
	public void keyPressed(KeyEvent e) {
		pressing[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressing[e.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent e) {} // When a key has been pressed and released.
	
// ------------------- MOUSE_EVENT -------------------

// ---------------------------------------------------
/*	RECTANGLE EDITOR
		int nx = 0;
		int ny = 0;
		Rect[] rect = new Rect[100];
		int count = -1;							*/	
// ---------------------------------------------------
	@Override
	public void mousePressed(MouseEvent e) {
    	// Get the mouse location
		mx = e.getX();
    	my = e.getY();
    	
// -----------------------------------------
/*	RECTANGLE EDITOR
    	count++;
    	rect[count] = new Rect(mx, my, 0, 0, Color.BLACK);
*/  
// -----------------------------------------
    }

	@Override
	public void mouseReleased(MouseEvent e) {
    	// Get the mouse location
    	mx = e.getX();
    	my = e.getY();
    	
// -----------------------------------------   	
/*	RECTANGLE EDITOR
    	int dx = nx - mx;
    	int dy = ny - my;
    	
    	rect[count].setSize(dx, dy);		*/
// ------------------------------------------
    }
	
	public void mouseClicked(MouseEvent e) {} // Button mouse pressed & released
	public void mouseEntered(MouseEvent e) {} // Mouse enters application windows
	public void mouseExited(MouseEvent e) {}  // Mouse exits application windows

	public void endGame() {
		this.endGame = true;
	}
	
	public boolean gameEnded() {
		return this.endGame;
	}

}

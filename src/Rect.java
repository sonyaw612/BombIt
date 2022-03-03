import java.awt.*;

public class Rect {
	double px;
	double py;
	
	// VELOCITY
	double vx = 0;
	double vy = 0;
	
	// ACCELERATION
	double ax = 0;
	double ay = 0;
	
	int w;
	int h;
	
	Color c;
	
	// overlaps
	boolean overlapTop = false;
	boolean overlapBot = false;
	boolean overlapLeft = false;
	boolean overlapRight = false;
	
	public Rect(int x, int y, int w, int h, Color c) {
		this.px = x;
		this.py = y;
		
		this.w = w;
		this.h = h;
		
		this.c = c;
	}
	
	public void draw(Graphics pen) {
		pen.setColor(c);
//		pen.drawRect((int)px, (int)py, w, h);
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}
	
	public void setAcceleration(double ax, double ay) {
		this.ax = ax;
		this.ay = ay;
	}
	
	public void setLocation(double px, double py) {
		this.px = px;
		this.py = py;
	}
	
	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public boolean contains(int mx, int my) {
//---------------------------------------------------------------------------------
// 		Returns if mouse is in object's box
//---------------------------------------------------------------------------------
		return (mx >= px) && (mx <= px + w) && (my >= py) && (my <= py+h);
	}
	
	public boolean overlaps(Rect r) {
		
		if(r.px + r.w >=   px) this.overlapLeft = true;
		if(  px +   w >= r.px) this.overlapRight = true;
		if(  py +   h >= r.py) this.overlapBot = true;
		if(r.py + r.h >=   py) this.overlapTop = true;
		
		return (r.px + r.w >=   px)  &&	// Overlapping from left   side
			   (  px +   w >= r.px)  &&	// Overlapping from right  side
			   (  py +   h >= r.py)  &&	// Overlapping from bottom side
			   (r.py + r.h >=   py);	// Overlapping from top    side
	}
	
	public void move() {
		px += vx;
		py += vy;
		
		vx += ax;
		vy += ay;
	}
	
	public void moveBy(int dx, int dy) {
		px += dx;
		py +=dy;
	}
	
	public void moveUp(int dy) {
		py -= dy;
	}
	
	public void moveDown(int dy) {
		py += dy;
	}
	
	public void moveLeft(int dx) {
		px -= dx;
	}
	
	public void moveRight(int dx) {
		px += dx;
	}
	
	// sqrts are very expensive to do. It involves using Newton's Iteration.
}

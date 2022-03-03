import java.awt.*;

public class Bombs extends Sprite {
	
	private int explodeCD = 160;
//	private final int EXPLODETIMER = 360;
	
	private int explosionCD = 80;
//	private final int EXPLOSIONTIMER = 60;
	
	private int damage = 0;
	
	private final int TICKING = 0;
	private final int EXPLODE = 1;
	
	private boolean exploded = false;
	
	Rect[] explosion = new Rect[4];

	public Bombs(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType, int damage) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		this.damage = damage;
	}
	
	public Bombs(int x, int y, int w, int h, String folderPath, String pose, int count, String fileType, int damage) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		this.damage = damage;
	}

	public void draw(Graphics pen) {
		if(alive) {
			if(exploded) {
				pen.drawImage(animation[movement].getCurrentImage(), (int) this.px, (int) this.py, this.w, this.h, null);
				explosionCD--;
				for(int i = 0; i < explosion.length; i++) {
					explosion[i].draw(pen);
				}
				if(explosionCD <= 0) this.die();
			}
			else {
				pen.drawImage(animation[movement].getCurrentImage(), (int) this.px, (int) this.py, this.w, this.h, null);
				explodeCD--;
				if(explodeCD == 0) {
					explode();
				}
			}
		}
		
	}
	
	@Override
	public void die() {
		for(int i = 0; i < explosion.length; i++) {
			explosion[i].px = -1000;
			explosion[i].py = -1000;
		}
		this.px = -1000;
		this.exploded = false;
		this.alive = false;
	}
	
	public void explode() {
		this.exploded = true;
		movement = EXPLODE;
		
		int changeX = this.w / 2;
		int changeY = this.h / 2; 
		
//		changeDimension(80, 80);
		changeDimension(144, 144);
		
		this.px = (this.px - (this.w / 2)) + changeX;
		this.py = (this.py - (this.h / 2)) + changeY + 5;
		
		int spaceDiff = 64;
		int explosionWidth = 16;
		
		Rect[] tempExplosion = { 
				new Rect( (int)this.px + spaceDiff, (int)this.py, explosionWidth, spaceDiff, Color.black), 
				new Rect( (int)this.px, (int)this.py + spaceDiff, spaceDiff, 16, Color.black),
				new Rect( (int)this.px + spaceDiff + explosionWidth, (int)this.py + spaceDiff, spaceDiff, explosionWidth, Color.black),					
				new Rect( (int)this.px + spaceDiff, (int)this.py + spaceDiff, explosionWidth, spaceDiff + explosionWidth, Color.black),
				};
		
		this.explosion = tempExplosion;
	}
	
	public void changeDimension(int w, int h) {
		this.w = w;
		this.h = h;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public boolean exploded() {
		return this.exploded;
	}
}

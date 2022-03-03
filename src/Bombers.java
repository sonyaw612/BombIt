import java.awt.*;

public class Bombers  extends Sprite {
	
	private int life = 5;
	protected int damage = 1;
	
	final int DIE = 4;
	protected boolean vulnerable = true;
	
	boolean moveUp = false;
	boolean moveDown = false;
	boolean moveLeft = false;
	boolean moveRight = false;
	
	int vulnerabilityCount = 100;
	int vulnerabilityCD = vulnerabilityCount;
	
	Bombs[] ammunition = new Bombs[10];
	int bombCount = 0;
	
	protected boolean bombPlanted = false;
	protected final int BOMBRECHARGE = 50;
	protected int bombingCD = BOMBRECHARGE;
	
	public Bombers(int x, int y, int w, int h, String folderPath, String[] pose, int count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		setSpeed(4);
	}
	
	public Bombers(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		setSpeed(4);
	}
	
	public void draw(Graphics pen) {
		if(alive) {
			if(!vulnerable) {
				if(vulnerabilityCD%10 < 6) {
					if(!moving) {
						pen.drawImage(animation[movement].getStillImage(), (int)px, (int)py, w, h, null);
					} else {
						pen.drawImage(animation[movement].getCurrentImage(), (int)px, (int)py, w, h, null);
					}
				}
				vulnerabilityCD--;
				if(vulnerabilityCD == 0) {
					vulnerable = true;
					vulnerabilityCD = vulnerabilityCount;
					if(life == 0) die();
				}
			}
			else if(moving) {
				pen.drawImage(animation[movement].getCurrentImage(), (int)px, (int)py, w, h, null);
			} else{
				pen.drawImage(animation[movement].getStillImage(), (int)px, (int)py, w, h, null);
			}
			moving = false;
		} else {
			// DIE COUNT
			if(animation[movement].current == animation[movement].image.length-1 && animation[movement].delayCount == 1) {
//				this.px = 0;
//				this.py = 0;
				pen.drawImage(animation[movement].getStillImage(animation[movement].image.length-1), (int)px, (int)py, w, h, null);
			}
			else pen.drawImage(animation[movement].getCurrentImage(), (int)px, (int)py, w, h, null);
		}
		
		if(this.bombPlanted) {
			bombingCD--;
			if(bombingCD == 0) {
				bombingCD = this.BOMBRECHARGE;
				bombPlanted = false;
			}
		}
		for(int i = 0; i < ammunition.length; i++) {
			if(ammunition[i] == null) {}
			else if(ammunition[i].alive ) {
				ammunition[i].draw(pen);
			}
		}
	}
	
	@Override
	public void die() {
		movement = DIE;
		alive = false;
		moving = false;
		changeDimension(85, 45);
		this.px -= 25;
	}
	
	public Bombs plantBomb() {	
		int[] bombImgCount = {16, 8};
		String[] bombAction = {"bomb", "explode"};
		
		int tempBombCount = bombCount%ammunition.length;
		if(ammunition[tempBombCount]== null || !ammunition[tempBombCount].alive) {
			ammunition[tempBombCount] = new Bombs((int) this.px, (int) this.py + h - 30, 24, 30, "Bomb/explode3/", bombAction, bombImgCount, "png", this.damage);
			bombCount++;
			bombPlanted = true;
		}
		return ammunition[tempBombCount];
	}
	
	public void hit(int damage) {
		life -= damage;
		vulnerable = false;
		if(life <= 0) {
			die();
		}
	}
	
	@Override
	public void moveDown(int dy) {
		py += dy;
		moving = true;
		moveDown = true;
		movement = DOWN;
	}
	
	@Override
	public void moveLeft(int dx) {
		px -= dx;
		moving = true;
		moveLeft = true;
		movement = LEFT;
	}
	
	@Override
	public void moveUp(int dy) {
		py -= dy;
		moving = true;
		moveUp = true;
		movement = UP;
	}
	
	@Override
	public void moveRight(int dx) {
		px += dx;
		moving = true;
		moveRight = true;
		movement = RIGHT;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
//	public void stop() {
//		this.moving = false;
//		moveUp = false;
//		moveDown = false;
//		moveLeft = false;
//		moveRight = false;
//		vulnerable = true;
//	}
	
	public int getLife() {
		return this.life;
	}
	
	public boolean isVulnerable() {
		return this.vulnerable;
	}
	
	public boolean isBombPlanted() {
		return this.bombPlanted;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public int getBombCount() {
		return this.bombCount;
	}
	
}

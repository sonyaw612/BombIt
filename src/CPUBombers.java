import java.util.*;

public class CPUBombers extends Bombers {
	
	private Bombers target = null;
	
	private int direction = 0;
	private int life = 3;
	
	private boolean wanderer = false;
	private int changeDirCount = 100;
	private int changeDirectionCD = changeDirCount;
	
	int stuckCD = 3;
	int stuckCount = 3;
	
	int[] temp_coord;
	
	public CPUBombers(int x, int y, int w, int h, String folderPath, String[] pose, int count, String fileType, Bombers target, int speed, int damage, boolean wanderer) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		this.target = target;
		setSpeed(speed);
		this.damage = damage;
		this.wanderer = wanderer;
		int[] temp_coord = {x, y};
		this.temp_coord = temp_coord;
	}
	
	public CPUBombers(int x, int y, int w, int h, String folderPath, String[] pose, int[] count, String fileType, Bombers target, int speed, int damage, boolean wanderer) {
		super(x, y, w, h, folderPath, pose, count, fileType);
		this.target = target;
		setSpeed(speed);
		this.damage = damage;
		this.wanderer = wanderer;
		int[] temp_coord = {x, y};
		this.temp_coord = temp_coord;
	}
	
	public double distanceTo(Bombers bomber) {
		double distance;
		distance = Math.sqrt(Math.pow(bomber.px - this.px, 2) + Math.pow(bomber.py - this.py, 2));
		return distance;
	}
	
	public int[] distancePoints(Bombers bomber) {
		int[] coordinateDistance = {(int) (this.px - bomber.px), (int) (this.py - bomber.py)};
		return coordinateDistance;
	}
	
//	public boolean canTurnLeft() {
//		// TO-DO 
//		return false;
//	}
//	
//	public boolean canTurnRight() {
//		// TO-DO 
//		return false;
//	}
//	
//	public boolean canTurnUp() {
//		// TO-DO 
//		return false;
//	}
//
//	public boolean canTurnDown() {
//		// TO-DO 
//		return false;
//	}
	
	public void chase() {
		if(!wanderer) {
			if(target.px < this.px) {
				this.moveLeft(this.getSpeed());
	//			direction = LEFT;
			}
			else if(target.px > this.px) {
				this.moveRight(this.getSpeed());
	//			direction = RIGHT;
			}
	
			if(target.py < this.py) {
				this.moveUp(this.getSpeed());
	//			direction = UP;
			}
			else if(target.py > this.py) {
				this.moveDown(this.getSpeed());
	//			direction = DOWN;
			}
		}
		else {
			if(this.changeDirectionCD == 0) {
				Random rand = new Random();
				this.direction = rand.nextInt(4);
				this.changeDirectionCD = this.changeDirCount;
			}
			if(direction == LEFT) {
				this.moveLeft(this.getSpeed());
				changeDirectionCD--;
	//			direction = LEFT;
			}
			else if(direction == RIGHT) {
				this.moveRight(this.getSpeed());
				changeDirectionCD--;
	//			direction = RIGHT;
			}
			if(direction == UP) {
				this.moveUp(this.getSpeed());
				changeDirectionCD--;
	//			direction = UP;
			}
			else if(direction == DOWN) {
				this.moveDown(this.getSpeed());
				changeDirectionCD--;
	//			direction = DOWN;
			}
		}
	}
	
	public void setTarget(Bombers bomber) {
		this.target = bomber;
	}
	
	public Bombers getTarget() {
		return this.target;
	}
	
	public void changeDirection() {
		this.direction = (direction + 1) % 4;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public void stuck() {
		this.stuckCD--;
		if(stuckCD == 0) {
			this.stuckCD = this.stuckCount;
			changeDirection();
		}
	}
	
	public boolean isWanderer() {
		return this.wanderer;
	}
	
	@Override
	public int getLife() {
		return this.life;
	}
	
}




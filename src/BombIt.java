import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BombIt extends GameStart {
	
//	Window size: 1440 x 1080/920
	
/*	-------------------------------------------------------------------------------------------
	Objective:
		User: Kill every bomber (1 user)
		CPUs: Live until time runs out (4 CPUs)
		
	TO-DO:
		Change user image and CPUBomber's target
	------------------------------------------------------------------------------------------- */		
		
//	30 x 50
	String[] actions = {"walk_up", "walk_left", "walk_down", "walk_right", "die"};
	int[] actionCounts = {4, 4, 4, 4, 6};
	
	Bombers userBomber = new Bombers(500, 350, 30, 50, "Officer/", actions, actionCounts, "png");
	
	CPUBombers[] cpuBombers = 
	{ 	new CPUBombers(146, 120, 30, 50, "BLUE/", actions, actionCounts, "png", this.userBomber, userBomber.getSpeed()/2, 1, false),
		new CPUBombers(146, 666, 30, 50, "RED/", actions, actionCounts, "png", this.userBomber, userBomber.getSpeed()/2, 1, true),
		new CPUBombers(1008, 120, 30, 50, "PURPLE/", actions, actionCounts, "png", this.userBomber, userBomber.getSpeed()/2, 1, true),
		new CPUBombers(1008, 666, 30, 50, "GREEN/", actions, actionCounts, "png", this.userBomber, userBomber.getSpeed()/2, 1, false),
	};
		
	Bombs[] bombTracker = new Bombs[64];
	private int numBombs = 0;
	
	private int deathCount = 0;
	private boolean winGame = false;
	Image victoryImage = Toolkit.getDefaultToolkit().getImage("../VICTORY.png");
	Image defeatImage = Toolkit.getDefaultToolkit().getImage("../DEFEAT.png");
	private final int REVEAL_GAME_STATUS_TIMER = 200;
	private int revealGameStatus = REVEAL_GAME_STATUS_TIMER;
	private boolean revealGameStat = false;
	
//	Image temp = Toolkit.getDefaultToolkit().getImage("../Bomb/bomb0.png");
	
//	-------------------------------------------------------------------------------------------
	Image backgroundImage;
	int scale = 32;
	Image[][] map;
	String[] mapString = {""};
	String[] tile_name = null;
	String letter_codes = "ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	
	String[] outline = {""};
	
	private int Timer = 3000; // 50 = 1 second
	private int seconds = 0;
//	-------------------------------------------------------------------------------------------
	
/*
	@Override
	public void initialize() {
		String background_name = null;
		File file = new File("BombIt.map.txt");
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
						
//		------------------------------------------------------------------------
//		LOAD MAP CODE
			int rows = Integer.parseInt(input.readLine());	// How many rows in the map?
			
			mapString = new String[rows];					// Set map's length to number of rows
			
			for(int row = 0; row < rows; row++) {			// Initialize each index of map according to the row number of the map
				mapString[row] = input.readLine();	
//				System.out.println(mapString[row]);
			}
			
			int cols = mapString[0].length();
			map = new Image[rows][cols];

//		------------------------------------------------------------------------	
//		LOAD TILE'S FILE NAMES
			int tiles = Integer.parseInt(input.readLine());		// How many tiles?
			tile_name = new String[tiles];					// Set tile_name's length to the number of available tiles
			for(int i = 0; i < tiles; i++) {				// Initialize names of tiles into the array tile_name
				tile_name[i] = input.readLine();
//				System.out.println(tile_name[i]);
			}

//		------------------------------------------------------------------------
//		LOAD BACKGROUND FILE NAME
			backgroundImage = Toolkit.getDefaultToolkit().getImage(input.readLine());	// file name of background
			
			input.close();

		}	catch(IOException e) {};
//		------------------------------------------------------------------------
		
		for(int row = 0; row < mapString.length; row++) {
			for(int col = 0; col < mapString[row].length(); col++) {
				char c = mapString[row].charAt(col);
				if(c != '.') {
					int tileIndex = letter_codes.indexOf(c);
//					try {
//						map[row][col] = ImageIO.read(new File(tile_name[tileIndex]));
//					} catch (IOException e) {}
					map[row][col] = Toolkit.getDefaultToolkit().getImage(tile_name[tileIndex]); // (col * 32) - Camera.x, (row * 32) - Camera.y, 32, 32, null);
				}
			}
		}
	}	
//*/

//*
/*
 * FIX THIS INITIALIZE SO THAT WE CAN PAINT THE BOULDER MAP
 */
	@Override
	public void initialize() {
		File file = new File("../boulderMap.txt");
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
						
//		------------------------------------------------------------------------
//		LOAD MAP CODE
			int rows = Integer.parseInt(input.readLine());	// How many rows in the map?
			
			outline = new String[rows];					// Set map's length to number of rows
			
			for(int row = 0; row < rows; row++) {			// Initialize each index of map according to the row number of the map
				outline[row] = input.readLine();	
			}
			
			input.close();
		}	catch(IOException e) {};
//		------------------------------------------------------------------------
	}
//*/
	
	@Override
	public void respond_To_Keyboard_Input() {
/*	---- CAMERA CONTROLS -----------------------------
		if(pressing[RT] == true) {			
			Camera.moveRight(10);
		}
		else if(pressing[LT] == true) {			
			Camera.moveLeft(10);
		}
		else if(pressing[DN]) {			
			Camera.moveDown(10);
		}
		else if(pressing[UP]) {				
			Camera.moveUp(10);
		}
//	------------------------------------------------ */
		
		if(userBomber.alive) {
			if(pressing[RT] == true) {
				userBomber.moveRight(userBomber.getSpeed());
			}
			if(pressing[LT] == true) {
				userBomber.moveLeft(userBomber.getSpeed());
			}
			if(pressing[UP] == true) {
				userBomber.moveUp(userBomber.getSpeed());
			}
			if(pressing[DN] == true) {
				userBomber.moveDown(userBomber.getSpeed());
			}
			if(pressing[SPACE] == true && !userBomber.isBombPlanted()) {
				bombTracker[numBombs] = userBomber.plantBomb();
				numBombs = (numBombs + 1) % 64;
//				System.out.println("User planted bomb.");
			}
		}
	}

	@Override
	public void move_Computer_Controlled_Entities() {
		
		for(int i = 0; i < cpuBombers.length; i++) {
			if(cpuBombers[i].alive) {
				int[] temp_coord = { (int) cpuBombers[i].px, (int) cpuBombers[i].py };
				cpuBombers[i].temp_coord = temp_coord;
				
				if(cpuBombers[i].isWanderer()) {
					cpuBombers[i].chase();
				}
				else if(cpuBombers[i].distanceTo(cpuBombers[i].getTarget()) > 20) {
					cpuBombers[i].chase();
				}			
	//			System.out.println("Temp: " + temp_coord[0] + ", " + temp_coord[1] + "; " + cpuBombers[i].px + ", " + cpuBombers[i].py + ": STUCK");
				
				if(cpuBombers[i].distanceTo(cpuBombers[i].getTarget()) < 150 && !cpuBombers[i].isBombPlanted()) {
					bombTracker[numBombs] = cpuBombers[i].plantBomb();
					numBombs = (numBombs + 1) % 64;
	//				System.out.println("CPU planted bomb.");
				}
			}
		}
		
	}

	@Override
	public void resolve_Collisions() {
		
		int user_top = (int) userBomber.py;
		int user_bottom = (int) userBomber.py + userBomber.h;
		int user_left = (int) userBomber.px;
		int user_right = (int) userBomber.px + userBomber.w;
		
		int user_mid_vertical = (int) userBomber.px + (userBomber.w/2);
		int user_mid_horizontal = (int) userBomber.py + (userBomber.h/2);
		
//	USER BOMBER
		// if can move up
		if(userBomber.moveUp && outline[user_top/scale].charAt(user_mid_vertical/scale) != '.') { // (moveUp && (outline[user_top/scale].charAt(user_left/scale) != '.' || outline[user_top/scale].charAt(user_right/scale) != '.')) {
			userBomber.py += (scale - (userBomber.py % scale));
		}
		// if can move down
		if(userBomber.moveDown && outline[user_bottom/scale].charAt(user_mid_vertical/scale) != '.') { // (moveDown && (outline[user_bottom/scale].charAt(user_left/scale) != '.' || outline[user_bottom/scale].charAt(user_right/scale) != '.')) {
			userBomber.py -= (userBomber.py + userBomber.h) % scale;
		}	
		// if can move left
		if(userBomber.moveLeft && outline[user_mid_horizontal/scale].charAt(user_left/scale) != '.') { // (moveLeft && (outline[user_top/scale].charAt(user_left/scale) != '.' || outline[user_bottom/scale].charAt(user_left/scale) != '.')) {
			userBomber.px += (scale - (userBomber.px % scale));
		}
		// if can move right
		if(userBomber.moveRight && outline[user_mid_horizontal/scale].charAt(user_right/scale) != '.') { // (moveRight && (outline[user_right/scale].charAt(user_top/scale) != '.' || outline[user_right/scale].charAt(user_bottom/scale) != '.')) {
			userBomber.px -= (userBomber.px + userBomber.w) % scale;
		}
		
//	CPU BOMBERS
		for(int i = 0; i < cpuBombers.length; i++) {
			if(cpuBombers[i].isAlive()) {
				int cpu_top = (int) cpuBombers[i].py;
				int cpu_bottom = (int) cpuBombers[i].py + cpuBombers[i].h;
				int cpu_left = (int) cpuBombers[i].px;
				int cpu_right = (int) cpuBombers[i].px + cpuBombers[i].w;
				
				int cpu_mid_vertical = (int) cpuBombers[i].px + (cpuBombers[i].w/2);
				int cpu_mid_horizontal = (int) cpuBombers[i].py + (cpuBombers[i].h/2);
				
				// if can move up
				if(cpuBombers[i].moveUp && outline[cpu_top/scale].charAt(cpu_mid_vertical/scale) != '.') { // (moveUp && (outline[user_top/scale].charAt(user_left/scale) != '.' || outline[user_top/scale].charAt(user_right/scale) != '.')) {
					cpuBombers[i].py += (scale - (cpuBombers[i].py % scale));
				}
				// if can move down
				if(cpuBombers[i].moveDown && outline[cpu_bottom/scale].charAt(cpu_mid_vertical/scale) != '.') { // (moveDown && (outline[user_bottom/scale].charAt(user_left/scale) != '.' || outline[user_bottom/scale].charAt(user_right/scale) != '.')) {
					cpuBombers[i].py -= (cpuBombers[i].py + cpuBombers[i].h) % scale;
				}	
				// if can move left
				if(cpuBombers[i].moveLeft && outline[cpu_mid_horizontal/scale].charAt(cpu_left/scale) != '.') { // (moveLeft && (outline[user_top/scale].charAt(user_left/scale) != '.' || outline[user_bottom/scale].charAt(user_left/scale) != '.')) {
					cpuBombers[i].px += (scale - (cpuBombers[i].px % scale));
				}
				// if can move right
				if(cpuBombers[i].moveRight && outline[cpu_mid_horizontal/scale].charAt(cpu_right/scale) != '.') { // (moveRight && (outline[user_right/scale].charAt(user_top/scale) != '.' || outline[user_right/scale].charAt(user_bottom/scale) != '.')) {
					cpuBombers[i].px -= (cpuBombers[i].px + cpuBombers[i].w) % scale;
				}
				if((int) cpuBombers[i].px == cpuBombers[i].temp_coord[0] && (int) cpuBombers[i].py == cpuBombers[i].temp_coord[1]) {
					cpuBombers[i].stuck();
				}
			}
		}
		
		// USER FOR LOOP FOR CPU BOMBERS COLLISION DETECTION
		
		for(int i = 0; i < bombTracker.length; i++) {
			boolean liveBomb = bombTracker[i] != null && bombTracker[i].alive && bombTracker[i].exploded();
			if(liveBomb) {
				for(int j = 0; j < bombTracker[i].explosion.length; j++) {
					if(userBomber.overlaps(bombTracker[i].explosion[j]) && userBomber.isVulnerable()) {
						userBomber.hit(bombTracker[i].getDamage());
						System.out.println("\tUser's life: " + userBomber.getLife());
					}
					for(int k = 0; k < this.cpuBombers.length; k++) {
						if(cpuBombers[k].isAlive() && cpuBombers[k].isVulnerable() && cpuBombers[k].overlaps(bombTracker[i].explosion[j])) {
							cpuBombers[k].hit(bombTracker[i].getDamage());
							if(!cpuBombers[k].isAlive()) this.deathCount++;
							
							System.out.println("\tCPU " + k + " hit with " + bombTracker[i].getDamage() + " damage.");
						}
					}
				}
			}
		}
	}
	
	public void paint(Graphics pen) {		
//		pen.drawImage(backgroundImage, 0, 0, 1920, 1080, null);
		
		if(this.revealGameStat) {
			if(this.winGame) {
				pen.drawImage(victoryImage, 0, 0, 1208, 790, null);
			}
			else {
				pen.drawImage(defeatImage, 0, 0, 1208, 790, null);
			}
		}
		else { 
			for(int row = 0; row < outline.length; row++) {
				for(int col = 0; col < outline[row].length(); col++) {
					if(outline[row].charAt(col) != '.') {
						pen.fillRect(col * scale, row * scale, scale, scale);
					} else {
						pen.drawRect(col * scale, row * scale, scale, scale);
					}
				}
			}
			
/*	---------------- DRAW MAP ---------------------------------------------
			for(int row = 0; row < mapString.length; row++) {
				for(int col = 0; col < mapString[row].length(); col++) {
					char c = mapString[row].charAt(col);
					if(c != '.') {
						int tileIndex = letter_codes.indexOf(c);
						pen.drawImage(map[row][col], (col * scale) - Camera.x, (row * scale) - Camera.y, scale, scale, null);
//					pen.drawRect(col * scale - Camera.x, row * scale - Camera.y, scale, scale);
					}
				}
			}
//*/
			
			for(int i = 0; i < cpuBombers.length; i++) {
				cpuBombers[i].draw(pen);
			}
			userBomber.draw(pen);
					
			pen.setFont(new Font("MONOSPACED", Font.PLAIN, 25));
			pen.setColor(Color.RED);
			pen.setFont(pen.getFont().deriveFont(50f));
			pen.drawString("TIME:", 30, 65);
			
			int temp_minutes = this.Timer % 3000;
			int temp_seconds = this.Timer / 50 - seconds/50;
			String placeholder = (this.seconds > this.Timer - 450) ? "0" : "";
			pen.drawString("0" + temp_minutes + ":" + placeholder + temp_seconds, 220, 65);		
			
			pen.setFont(pen.getFont().deriveFont(25f));
			
			pen.drawString("| BOMBS PLANTED : " + userBomber.getBombCount() + " | TERRORISTS LEFT : " + (cpuBombers.length - deathCount), 400, 45);				
			pen.drawString("| LIVES LEFT    : " + userBomber.getLife() + " | ", 400, 70);				
			
			if(this.gameEnded()) {
				this.revealGameStatus--;
				if(revealGameStatus <= 0) {
					this.revealGameStat = true;
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
//		Create Event for when user clicks what game mode
		
		mx = e.getX();
    	my = e.getY();
    	
    	System.out.println("my: " + my + ", mx: " + mx);
	}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void checkGameStatus() {

		if(deathCount == cpuBombers.length) {
			this.winGame = true;
			this.endGame();
		}
		if(!userBomber.isAlive()) {
			this.endGame();
		}
		seconds++;
		if(seconds <= Timer) {
			if(seconds%50 == 0) System.out.println(seconds/50 + " seconds.");
		} else {
//			for(int i = 0; i < cpuBombers.length; i++) {
//				if(cpuBombers[i].alive) cpuBombers[i].die();
//			}			
			this.endGame();
			
//			disply victory/defeat screen
		}
		
	}
	
}

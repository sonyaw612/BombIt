
public class Camera {
	
	static int x;
	static int y;
	
	public static void initialize(int x, int y) {
		Camera.x = x;
		Camera.y = y;
	}
	
	public static void moveRight(int dx) {
		x += dx;
	}
	
	public static void moveLeft(int dx) {
		x -= dx;
	}
	
	public static void moveUp(int dy) {
		y -= dy;
	}
	
	public static void moveDown(int dy) {
		y += dy;
	}

}

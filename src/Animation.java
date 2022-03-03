import java.awt.*;

public class Animation {

	Image[] image;
	
	int current = 0; // which image the animation will start from
	
	final int still = 0;
	
	final int delay = 10;
	int delayCount = 10;
	
	public Animation(String folderPath, String name, int count, String filetype) {
		image = new Image[count];
		
		for(int i = 0; i < count; i++) {
			image[i] = Toolkit.getDefaultToolkit().getImage("../" + folderPath + name + i + "." + filetype);		
		}
	}
	
	public void draw(Graphics pen) {
		pen.drawImage(getCurrentImage(), 30, 50, null);
	}
	
	public Image getCurrentImage() {
		Image temp = image[current];
		delayCount--;

		if(delayCount == 0) {
			current++;
			
			if(current == image.length) current = 0;
			
			delayCount = delay;
		}
		
		return temp;
	}
	
	public Image getStillImage(int imageIndex) {
		return image[imageIndex];
	}
	
	// DO NOT USE
	public Image getStillImage() {
		return image[still];
	}
	
}

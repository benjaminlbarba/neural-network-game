import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Ghost is the class inherited from BasicCharacter class that contains all relevant 
 * fields and methods related to ghosts moving on the map. The only specification that 
 * needs to make in Ghost class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Ghost {
	private Image[] whiteGhostLeftImages = new Image[2];
	private boolean ghostFrameToggle = true;
	
	private int x;
	private int y;
	private Directions dir;	
	private float elementPixelUnit;
	
	public Ghost(int x, int y, float elementPixelUnit) {
		this.x = x;
		this.y = y;
		this.elementPixelUnit = elementPixelUnit;
	}

	public void init() {
		try {
			this.whiteGhostLeftImages[0] = new Image("images/ghosts/white/white_left_1.png");
			this.whiteGhostLeftImages[1] = new Image("images/ghosts/white/white_left_2.png");
		} catch (SlickException e) {
			System.out.println("Cannot load ghost images.");
		}

	}
	
	/**
	 * Implemented from the abstract method in BasicCharacter class,
	 * update method is called every frame of the game by the governing update method in Game class.
	 * it updates the positions (x, y) and directions (dir) of the Ghost without needing to process
	 * any keyboard input. 
	 */
	public void update() {
		this.ghostFrameToggle = !this.ghostFrameToggle;
	}
	
	public void render() {
		int frameIndex = this.ghostFrameToggle ? 1 : 0;
		// TODO: all four inputs need to be recalculated
		this.whiteGhostLeftImages[frameIndex].draw(this.x, this.x, elementPixelUnit, elementPixelUnit);
	}
}

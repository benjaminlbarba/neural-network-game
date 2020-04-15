import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Ghost is the class inherited from BasicCharacter class that contains all relevant 
 * fields and methods related to ghosts moving on the map. The only specification that 
 * needs to make in Ghost class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Ghost {
	private Animation whiteGhostLeftAminition;
	
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
			SpriteSheet whiteGhostLeftSpriteSheet = new SpriteSheet("images/ghosts/white/white_up.png", 52, 52);
			this.whiteGhostLeftAminition = new Animation(whiteGhostLeftSpriteSheet, 200);
			
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
	public void update(int delta) {
		this.whiteGhostLeftAminition.update(delta);
	}
	
	public void render() {
		// TODO: all four inputs need to be recalculated
		this.whiteGhostLeftAminition.draw(this.x, this.x, elementPixelUnit, elementPixelUnit);
	}
}

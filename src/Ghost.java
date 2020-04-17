import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;


/**
 * Ghost is the class inherited from BasicCharacter class that contains all relevant 
 * fields and methods related to ghosts moving on the map. The only specification that 
 * needs to make in Ghost class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Ghost {
	private Animation ghostLeftAminition;
	private Circle ghostCircle;  
	private ArrayList<Shape> wallShapesAroundGhost;
	
	private float x;
	private float y;
	private Directions dir;	
	private float speed;
	private boolean isColliding = false;
	
	private float elementPixelUnit;
	
	public Ghost(float x, float y, float elementPixelUnit) {
		this.x = x;
		this.y = y;
		this.elementPixelUnit = elementPixelUnit;
	}

	public void init() {
		try {
			SpriteSheet whiteGhostLeftSpriteSheet = new SpriteSheet("images/ghosts/white/white_up.png", 52, 52);
			this.ghostLeftAminition = new Animation(whiteGhostLeftSpriteSheet, 200);
			
			this.ghostCircle = new Circle(this.x, this.y, this.elementPixelUnit);
			
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
		this.ghostLeftAminition.update(delta);
		this.setIsColliding();
	}
	
	public void render(Graphics g) {
		// TODO: all four inputs need to be recalculated
		this.ghostLeftAminition.draw(this.x, this.y, elementPixelUnit, elementPixelUnit);
		
		if (this.isColliding) {
			g.drawString("GhostCollision: true", 50, 50);
		}
		else {
			g.drawString("GhostCollision: false", 50, 50);
		}
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setX(float x) {
		this.x = x;
		this.ghostCircle.setX(x);
	}
	
	public void setY(float y) {
		this.y = y;
		this.ghostCircle.setY(y);
	}
	
	public void setIsColliding() {
		this.wallShapesAroundGhost.forEach(
				w -> {
					if (this.ghostCircle.intersects(w)) {
						this.isColliding = true;
					}
				});
	}
	
	public void setWallShapesAroundGhost(ArrayList<Shape> wallShapesAroundGhost) {
		this.wallShapesAroundGhost = wallShapesAroundGhost;
	}
}

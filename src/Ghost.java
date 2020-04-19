import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
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
	private boolean isDebug;

	private float ghostCircleRadius;

	private float elementPixelUnit;
	
	private Animation ghostLeftAminition;
	private Circle ghostCircle;
	private ArrayList<Shape> wallShapesAroundGhost;

	private float closestNonCollisionX;
	private float closestNonCollisionY;

	private float x;
	private float y;
	private Directions dir;	
	private float speed;
	private boolean isColliding = false;
		
	public Ghost(float x, float y, float elementPixelUnit, boolean isDebug) {
		this.isDebug = isDebug;

		this.x = x;
		this.y = y;
		this.elementPixelUnit = elementPixelUnit;

		// The radius of the ghost circle is slightly smaller than 1/2 of the path width to avoid triggering collision
		// when moving in the path.
		this.ghostCircleRadius = (float) ((elementPixelUnit / 2) * 0.90);
	}

	public void init() {
		try {
			this.speed = 1;
			this.dir = Directions.RIGHT;

			SpriteSheet whiteGhostLeftSpriteSheet = new SpriteSheet("images/ghosts/white/white_up.png", 52, 52);
			this.ghostLeftAminition = new Animation(whiteGhostLeftSpriteSheet, 200);
			
			// ghostCircle is center positioned while ghost animation is positioned based on top left corner
			// This is the conversion between animation coordinate and circle coordinate so that they fully overlap.
			this.ghostCircle = new Circle(
					this.x + this.elementPixelUnit / 2, 
					this.y + this.elementPixelUnit / 2, 
					this.ghostCircleRadius);
			
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
	public void update(
			int delta,
			ArrayList<Shape> closeByWallShapes,
			float closestNonCollisionX,
			float closestNonCollisionY
	) {
		this.setWallShapesAroundGhost(closeByWallShapes);
		this.closestNonCollisionX = closestNonCollisionX;
		this.closestNonCollisionY = closestNonCollisionY;

		this.ghostLeftAminition.update(delta);
		this.updateGhostCirclePosition();
		this.setIsColliding();
		this.smartMovePerFrame();
	}
	
	public void render(Graphics g) {
		// TODO: all four inputs need to be recalculated
		this.ghostLeftAminition.draw(this.x, this.y, elementPixelUnit, elementPixelUnit);
		if (isDebug) {
			g.draw(this.ghostCircle);
		}

		if (this.isColliding) {
			g.drawString("GhostCollision: true", 50, 50);
		}
		else {
			g.drawString("GhostCollision: false", 50, 50);
		}
	}
	
	/**
	 * Ghost animations are drawn based on x, y of its top left corner while the ghost circle
	 * needs to be positioned based on its center.
	 * This method updates the center x,y of the circle based on the x, y of the ghost animation.
	 */
	private void updateGhostCirclePosition() {
		this.ghostCircle.setCenterX(this.x + this.elementPixelUnit / 2);
		this.ghostCircle.setCenterY(this.y + this.elementPixelUnit / 2);
	}

	// TODO: This needs to be updated to be more intelligent
	private void smartMovePerFrame() {
		if (this.isColliding) {
			this.reverseDirection();
			this.replaceGhostToPathCenter();
		}
		else {
			this.movePerFrame();
		}
	}

	// When collision is detected, the ghost circle would already be slightly off the center of its path.
	// This method moves it back to the center, resets its position to be right before the collision so that the
	// collision state is clear and the ghost could change direction.
	private void replaceGhostToPathCenter() {
		this.x = this.closestNonCollisionX;
		this.y = this.closestNonCollisionY;
	}

	private void movePerFrame(){
		switch (this.dir) {
			case UP:
				this.y = this.y - this.speed;
				break;
			case DOWN:
				this.y = this.y + this.speed;
				break;
			case LEFT:
				this.x = this.x - this.speed;
				break;
			case RIGHT:
				this.x = this.x + this.speed;
				break;
		}
	}

	private void reverseDirection() {
		switch (this.dir) {
			case UP:
				this.dir = Directions.DOWN;
				break;
			case DOWN:
				this.dir = Directions.UP;
				break;
			case LEFT:
				this.dir = Directions.RIGHT;
				break;
			case RIGHT:
				this.dir = Directions.LEFT;
				break;
		}
	}

	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setIsColliding() {
		for (Shape wall : this.wallShapesAroundGhost) {
			if (this.ghostCircle.intersects(wall)) {
				this.isColliding = true;
				return;
			}
		}
		this.isColliding = false;
	}

	private void setWallShapesAroundGhost(ArrayList<Shape> wallShapesAroundGhost) {
		this.wallShapesAroundGhost = wallShapesAroundGhost;
	}
}

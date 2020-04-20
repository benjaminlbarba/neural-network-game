import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.Sys;
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

	private float pacmanCenterX;
	private float pacmanCenterY;
		
	public Ghost(float initialX, float initialY, float elementPixelUnit, boolean isDebug) {
		this.isDebug = isDebug;

		this.x = initialX;
		this.y = initialY;
		this.elementPixelUnit = elementPixelUnit;

		// The radius of the ghost circle is slightly smaller than 1/2 of the path width to avoid triggering collision
		// when moving in the path.
		this.ghostCircleRadius = (float) ((elementPixelUnit / 2) * 0.99);
	}

	public void init(float pacmanCenterX, float pacmanCenterY) {
		try {
			this.speed = 1;
			this.dir = Directions.RIGHT;

			this.pacmanCenterX = pacmanCenterX;
			this.pacmanCenterY = pacmanCenterY;

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
			float closestNonCollisionY,
			float pacmanCenterX,
			float pacmanCenterY
	) {
		this.pacmanCenterX = pacmanCenterX;
		this.pacmanCenterY = pacmanCenterY;

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
			this.replaceGhostToPathCenter();
			try {
				this.dir = this.getChosenNextDirection();
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	private void movePerFrame() {
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

	private Directions getChosenNextDirection() throws Exception {
		ArrayList<Directions> availableDirections = this.getAvailableDirections();

		if (availableDirections.size() == 0) {
			throw new Exception("availableDirections array list cannot be empty");
		}

		// Unless ghost and pacman are on the same path,
		// ghosts do not repeat the immediate path if possible to avoid ghosts being stuck back and forth on one path.
		//if (availableDirections.size() > 1) {
		//	availableDirections.removeIf(d -> d == (this.getReverseDirection(this.dir)));
		//}

		float ghostCircleX = this.ghostCircle.getCenterX();
		float ghostCircleY = this.ghostCircle.getCenterY();

		// get the maximum shortestResultingDistance value possible with one speed value on each of x and y.
		float shortestResultingDistance =
				this.getDistanceBetweenTwoPoints(ghostCircleX, ghostCircleY, this.pacmanCenterX, this.pacmanCenterY) +
				this.getDistanceBetweenTwoPoints(0, 0, this.speed, this.speed);
		Directions chosenDirection = availableDirections.get(0);

		float newDistance = 0;

		for (Directions currentDir : availableDirections) {
			System.out.println("CurrentDir: " + this.dir);
			switch (currentDir) {
				case UP:
					// Unless ghost and pacman are on the same path,
					// ghosts do not repeat the immediate path if possible to avoid ghosts being stuck back and forth on one path.
					if (availableDirections.size() > 1 && !this.getOnSameVerticalPathWithPacman() && this.getReverseDirection(this.dir) == Directions.UP) {
						System.out.println("Up removed");
						break;
					}
					newDistance = this.getDistanceBetweenTwoPoints(
							ghostCircleX,
							ghostCircleY - this.speed,
							this.pacmanCenterX,
							this.pacmanCenterY
					);
					if (newDistance < shortestResultingDistance) {
						shortestResultingDistance = newDistance;
						chosenDirection = currentDir;
					}
					break;
				case DOWN:
					// Unless ghost and pacman are on the same path,
					// ghosts do not repeat the immediate path if possible to avoid ghosts being stuck back and forth on one path.
					if (availableDirections.size() > 1 && !this.getOnSameVerticalPathWithPacman() && this.getReverseDirection(this.dir) == Directions.DOWN) {
						System.out.println("Down removed");
						break;
					}
					newDistance = this.getDistanceBetweenTwoPoints(
							ghostCircleX,
							ghostCircleY + this.speed,
							this.pacmanCenterX,
							this.pacmanCenterY
					);
					if (newDistance < shortestResultingDistance) {
						shortestResultingDistance = newDistance;
						chosenDirection = currentDir;
					}
					break;
				case LEFT:
					// Unless ghost and pacman are on the same path,
					// ghosts do not repeat the immediate path if possible to avoid ghosts being stuck back and forth on one path.
					if (availableDirections.size() > 1 && !this.getOnSameHorizontalPathWithPacman() && this.getReverseDirection(this.dir) == Directions.LEFT) {
						System.out.println("Left removed");
						break;
					}
					newDistance = this.getDistanceBetweenTwoPoints(
							ghostCircleX - this.speed,
							ghostCircleY,
							this.pacmanCenterX,
							this.pacmanCenterY
					);
					if (newDistance < shortestResultingDistance) {
						shortestResultingDistance = newDistance;
						chosenDirection = currentDir;
					}
					break;

				case RIGHT:
					// Unless ghost and pacman are on the same path,
					// ghosts do not repeat the immediate path if possible to avoid ghosts being stuck back and forth on one path.
					if (availableDirections.size() > 1 && !this.getOnSameHorizontalPathWithPacman() && this.getReverseDirection(this.dir) == Directions.RIGHT) {
						System.out.println("Right removed");
						break;
					}
					newDistance = this.getDistanceBetweenTwoPoints(
							ghostCircleX + this.speed,
							ghostCircleY,
							this.pacmanCenterX,
							this.pacmanCenterY
					);
					if (newDistance < shortestResultingDistance) {
						shortestResultingDistance = newDistance;
						chosenDirection = currentDir;
					}
					break;
			}
		}

		return chosenDirection;
	}

	private boolean getOnSameHorizontalPathWithPacman() {
		boolean onSameHorizontalPathWithPacman =
				Math.abs(this.pacmanCenterY - this.ghostCircle.getCenterY()) < this.elementPixelUnit / 3;
		System.out.println("this.pacmanCenterY: " + this.pacmanCenterY);
		System.out.println("this.ghostCircle.getCenterY(): " + this.ghostCircle.getCenterY());
		System.out.println("onSameHorizontalPathWithPacman: " + onSameHorizontalPathWithPacman);
		return onSameHorizontalPathWithPacman;
	}

	private boolean getOnSameVerticalPathWithPacman() {
		boolean onSameVerticalPathWithPacman =
				Math.abs(this.pacmanCenterX - this.ghostCircle.getCenterX()) < this.elementPixelUnit / 3;
		//System.out.println("onSameVerticalPathWithPacman: " + onSameVerticalPathWithPacman);
		return onSameVerticalPathWithPacman;
	}

	private Directions getReverseDirection(Directions dir) {
		switch (dir) {
			case UP:
				return Directions.DOWN;
			case DOWN:
				return Directions.UP;
			case LEFT:
				return Directions.RIGHT;
			case RIGHT:
				return Directions.LEFT;
			case STILL:
				return Directions.STILL;
		}

		// Should never get here with a valid direction
		return Directions.STILL;
	}

	private float getDistanceBetweenTwoPoints(float x1, float y1, float x2, float y2) {
		return (float) (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}

	// This method creates a temporary ghost circle that is placed one radius distance more than the
	// current actual ghost circle for each of the four directions. Then it populates available directions for those
	// that do not cause a collision between the temporary ghost circle and the wallShapesAroundGhost.
	private ArrayList<Directions> getAvailableDirections() {
		ArrayList<Directions> availableDirections = new ArrayList<>();

		float currentCircleCenterX = this.x + this.elementPixelUnit / 2;
		float currentCircleCenterY = this.y + this.elementPixelUnit / 2;

		Circle tempGhostCircle = new Circle(
				currentCircleCenterX,
				currentCircleCenterY,
				this.ghostCircleRadius);

		// LEFT
		tempGhostCircle.setCenterX(currentCircleCenterX - this.ghostCircleRadius);
		tempGhostCircle.setCenterY(currentCircleCenterY);
		if (!this.wallShapesAroundGhost.stream().anyMatch(w -> tempGhostCircle.intersects(w))) {
			availableDirections.add(Directions.LEFT);
		}

		// RIGHT
		tempGhostCircle.setCenterX(currentCircleCenterX + this.ghostCircleRadius);
		tempGhostCircle.setCenterY(currentCircleCenterY);
		if (!this.wallShapesAroundGhost.stream().anyMatch(w -> tempGhostCircle.intersects(w))) {
			availableDirections.add(Directions.RIGHT);
		}
		// UP
		tempGhostCircle.setCenterX(currentCircleCenterX);
		tempGhostCircle.setCenterY(currentCircleCenterY - this.ghostCircleRadius);
		if (!this.wallShapesAroundGhost.stream().anyMatch(w -> tempGhostCircle.intersects(w))) {
			availableDirections.add(Directions.UP);
		}
		// DOWN
		tempGhostCircle.setCenterX(currentCircleCenterX);
		tempGhostCircle.setCenterY(currentCircleCenterY + this.ghostCircleRadius);
		if (!this.wallShapesAroundGhost.stream().anyMatch(w -> tempGhostCircle.intersects(w))) {
			availableDirections.add(Directions.DOWN);
		}

		System.out.println(availableDirections);
		return availableDirections;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.sun.tools.javac.util.List;
import org.lwjgl.Sys;
import org.lwjgl.util.Timer;
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
	private Timer timer = new Timer();

	private GhostColors ghostColor;
	// Each ghost on the map to have slightly different time to be activated to avoid movement overlapping
	private float ghostStartDelay;
	private HashMap<Directions, Animation> ghostAnimations = new HashMap<>();
	private static final int ghostSpriteHeight = 52;
	private static final int ghostSpriteWidth = 52;
	private static final int ghostAnimationSpriteDuration = 200;

	// This is a difficulty setting. Ghost knows the best path at intersections
	// (we are not concerned about the end of a path when collision happens), by setting this chance lower, ghosts do
	// do not easily crowd up around the pacman to end the game.
	private static final float ghostChanceOfPickingCorrectPathAtIntersection = 0.5f;

	private float ghostCircleRadius;

	private float elementPixelUnit;
	
	private Circle ghostCircle;
	private ArrayList<Shape> wallShapesAroundGhost;

	private float closestNonCollisionX;
	private float closestNonCollisionY;

	private float x;
	private float y;
	private Directions dir;
	private final float speed = 1;
	private boolean isAtIntersection = false;
	private boolean isCollidingWithWall = false;

	private float pacmanCenterX;
	private float pacmanCenterY;
		
	public Ghost(float initialX, float initialY, float elementPixelUnit, boolean isDebug, int ghostIndex) {
		this.ghostColor = this.getGhostColorFromIndex(ghostIndex);
		this.setGhostStartDelay(ghostIndex);
		this.isDebug = isDebug;

		this.x = initialX;
		this.y = initialY;
		this.elementPixelUnit = elementPixelUnit;

		// The radius of the ghost circle is slightly smaller than 1/2 of the path width to avoid triggering collision
		// when moving in the path.
		this.ghostCircleRadius = (float) ((elementPixelUnit / 2) * 0.99);
	}

	private GhostColors getGhostColorFromIndex(int ghostIndex) {
		GhostColors[] availableGhostColors = GhostColors.values();
		return availableGhostColors[ghostIndex % (availableGhostColors.length)];
	}

	// Each ghost start one second after the last ghost
	private void setGhostStartDelay(int ghostIndex) {
		this.ghostStartDelay = (float) (ghostIndex * 2);
	}

	public void init(float pacmanCenterX, float pacmanCenterY) {
		this.dir = this.getRandomGhostDir();

		this.pacmanCenterX = pacmanCenterX;
		this.pacmanCenterY = pacmanCenterY;

		this.initializeGhostAnimations();

		// ghostCircle is center positioned while ghost animation is positioned based on top left corner
		// This is the conversion between animation coordinate and circle coordinate so that they fully overlap.
		this.ghostCircle = new Circle(
				this.x + this.elementPixelUnit / 2,
				this.y + this.elementPixelUnit / 2,
				this.ghostCircleRadius);
	}

	private void initializeGhostAnimations() {
		try {
			SpriteSheet upSpriteSheet = new SpriteSheet(
					this.getGhostSpriteFolderLink(this.ghostColor, Directions.UP),
					ghostSpriteWidth,
					ghostSpriteHeight
			);
			Animation upAnimation = new Animation(upSpriteSheet, ghostAnimationSpriteDuration);

			SpriteSheet downSpriteSheet = new SpriteSheet(
					this.getGhostSpriteFolderLink(this.ghostColor, Directions.DOWN),
					ghostSpriteWidth,
					ghostSpriteHeight
			);
			Animation downAnimation = new Animation(downSpriteSheet, ghostAnimationSpriteDuration);

			SpriteSheet leftSpriteSheet = new SpriteSheet(
					this.getGhostSpriteFolderLink(this.ghostColor, Directions.LEFT),
					ghostSpriteWidth,
					ghostSpriteHeight
			);
			Animation leftAnimation = new Animation(leftSpriteSheet, ghostAnimationSpriteDuration);

			SpriteSheet rightSpriteSheet = new SpriteSheet(
					this.getGhostSpriteFolderLink(this.ghostColor, Directions.RIGHT),
					ghostSpriteWidth,
					ghostSpriteHeight
			);
			Animation rightAnimation = new Animation(rightSpriteSheet, ghostAnimationSpriteDuration);

			this.ghostAnimations.put(Directions.UP, upAnimation);
			this.ghostAnimations.put(Directions.DOWN, downAnimation);
			this.ghostAnimations.put(Directions.LEFT, leftAnimation);
			this.ghostAnimations.put(Directions.RIGHT, rightAnimation);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private Directions getRandomGhostDir() {
		Random random = new Random();
		ArrayList<Directions> availableDirections = new ArrayList<>(Arrays.asList(Directions.values()));
		availableDirections.removeIf(d -> d.equals(Directions.STILL));

		return availableDirections.get(random.nextInt(availableDirections.size()));
	}

	private String getGhostSpriteFolderLink(GhostColors color, Directions direction) {
		String colorString = color.toString().toLowerCase();
		String directionString = direction.toString().toLowerCase();

		return "images/ghosts/" + colorString + "/" + colorString + "_" + directionString + ".png";
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
		this.ghostAnimations.values().forEach(animation -> animation.update(delta));

		// Do not start moving the ghost until its time is up.
		if (this.timer.getTime() < this.ghostStartDelay) {
			return;
		}

		this.pacmanCenterX = pacmanCenterX;
		this.pacmanCenterY = pacmanCenterY;

		this.setWallShapesAroundGhost(closeByWallShapes);
		this.closestNonCollisionX = closestNonCollisionX;
		this.closestNonCollisionY = closestNonCollisionY;

		this.updateGhostCirclePosition();
		this.setIsAtIntersectionAndCollidingWithWall();
		this.smartMovePerFrame();

		this.timer.tick();
	}
	
	public void render(Graphics g) {
		// TODO: all four inputs need to be recalculated
		this.ghostAnimations.get(this.dir).draw(this.x, this.y, elementPixelUnit, elementPixelUnit);
		if (isDebug) {
			g.draw(this.ghostCircle);
		}

		if (this.isAtIntersection) {
			g.drawString("GhostAtIntersection: true", 50, 50);
		}
		else {
			g.drawString("GhostAtIntersection: false", 50, 50);
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
		if (this.isAtIntersection) {
			this.replaceGhostToPathCenter();
			try {
				this.dir = this.getChosenNextDirection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.movePerFrame();
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
		ArrayList<Directions> availableDirections = this.getAvailableDirections(this.x, this.y);

		if (availableDirections.size() == 0) {
			throw new Exception("availableDirections array list cannot be empty");
		}

		// Unless ghost and pacman are on the same path,
		// ghosts do not repeat the immediate path if possible to avoid ghosts being stuck back and forth on one path.

		float ghostCircleX = this.ghostCircle.getCenterX();
		float ghostCircleY = this.ghostCircle.getCenterY();

		// get the maximum shortestResultingDistance value possible with one speed value on each of x and y.
		float shortestResultingDistance =
				this.getDistanceBetweenTwoPoints(ghostCircleX, ghostCircleY, this.pacmanCenterX, this.pacmanCenterY) +
				this.getDistanceBetweenTwoPoints(0, 0, this.speed, this.speed);
		Directions chosenDirection = availableDirections.get(0);

		float newDistance = 0;

		for (Directions currentDir : availableDirections) {
			switch (currentDir) {
				case UP:
					// Unless ghost and pacman are on the same path,
					// ghosts do not repeat the immediate path if possible to avoid ghosts being stuck back and forth on one path.
					if (availableDirections.size() > 1 && !this.getOnSameVerticalPathWithPacman() && this.getReverseDirection(this.dir) == Directions.UP) {
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

		return this.randomlyDecideKeepingCurrentDirectionAtIntersection(this.dir, chosenDirection);
	}

	// Generates 50% change of ghost ignoring the more optimal direction at intersections to avoid making the game
	// unplayable (because the ghosts are too smart to corner the pacman).
	private Directions randomlyDecideKeepingCurrentDirectionAtIntersection(Directions currentDir, Directions chosenNextDir) {
		if (!this.isCollidingWithWall && this.isAtIntersection) {
			Random random = new Random();
			return random.nextDouble() <= this.ghostChanceOfPickingCorrectPathAtIntersection ? chosenNextDir : currentDir;
		}

		return chosenNextDir;
	}

	private boolean getOnSameHorizontalPathWithPacman() {
		boolean onSameHorizontalPathWithPacman =
				Math.abs(this.pacmanCenterY - this.ghostCircle.getCenterY()) < this.elementPixelUnit / 3;
		return onSameHorizontalPathWithPacman;
	}

	private boolean getOnSameVerticalPathWithPacman() {
		boolean onSameVerticalPathWithPacman =
				Math.abs(this.pacmanCenterX - this.ghostCircle.getCenterX()) < this.elementPixelUnit / 3;
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
	private ArrayList<Directions> getAvailableDirections(float x, float y) {
		ArrayList<Directions> availableDirections = new ArrayList<>();

		float currentCircleCenterX = x + this.elementPixelUnit / 2;
		float currentCircleCenterY = y + this.elementPixelUnit / 2;

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

		return availableDirections;
	}

	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setIsAtIntersectionAndCollidingWithWall() {
		// if ghost circle is colliding with any wall, it definitely is at intersection.
		for (Shape wall : this.wallShapesAroundGhost) {
			if (this.ghostCircle.intersects(wall)) {
				this.isAtIntersection = true;
				this.isCollidingWithWall = true;
				return;
			}
		}

		// if the ghost is close enough to a nearest non collision location (path center) and the ghost temp circle
		// (placed at the nearest path center) has more than 2 available directions
		// (more than current direction and its reverse), it is also at intersection.
		if (Math.abs(this.closestNonCollisionX - this.x) < this.speed / 2 &&
				Math.abs(this.closestNonCollisionY - this.y) < this.speed / 2 &&
				this.getAvailableDirections(this.closestNonCollisionX, this.closestNonCollisionY).size() > 2) {
			this.isAtIntersection = true;
			return;
		}

		this.isAtIntersection = false;
		this.isCollidingWithWall = false;
	}

	private void setWallShapesAroundGhost(ArrayList<Shape> wallShapesAroundGhost) {
		this.wallShapesAroundGhost = wallShapesAroundGhost;
	}
}

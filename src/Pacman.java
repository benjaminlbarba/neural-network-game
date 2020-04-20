import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Pacman is the class inherited from BasicCharacter class that contains all relevant 
 * fields and methods related to the pacman moving on the map. The only specification that 
 * needs to make in Pacman class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Pacman {

	public static final float stepSize = 1; // must be factor of blockSize (length of row and col)
	private Map map;
	private boolean isAddScore;
	private Directions nextDir;

	private float x;
	private float y;

	private float centerX;
	private float centerY;

	private float elementPixelUnit;
	private Directions dir;
	private Circle pacmanCircle;
	private float pacmanCircleRadius;
	private boolean isDebug;
	private Animation pacmanRightAnimation;
	private HashMap<Directions, Integer> dirMapX;
	private HashMap<Directions, Integer> dirMapY;
	private boolean isColliding = false;
	private ArrayList<Shape> wallShapesAroundPacman;


	public Pacman(float x, float y, float elementPixelUnit, Map m, boolean isDebug) {
		this.x = x;
		//System.out.println("x="+x);
		this.y = y;
		//System.out.println("y="+y);
		this.map = m;
		this.dir = Directions.STILL;
		this.isDebug = isDebug;
		this.elementPixelUnit = elementPixelUnit;
		nextDir = Directions.STILL;
		this.pacmanCircleRadius = (float) ((this.elementPixelUnit / 2) * 0.90);
		initDirMap();
	}

	public void init() {
		try {
			SpriteSheet pacmanRightSpriteSheet = new SpriteSheet("images/pacman/pacman_right.jpg", 18, 18);
			this.pacmanRightAnimation = new Animation(pacmanRightSpriteSheet, 200);

			// ghostCircle is center positioned while ghost animation is positioned based on top left corner
			// This is the conversion between animation coordinate and circle coordinate so that they fully overlap.
			this.centerX = this.x + this.elementPixelUnit / 2;
			this.centerY = this.y + this.elementPixelUnit / 2;

			this.pacmanCircle = new Circle(
					this.centerX,
					this.centerY,
					this.pacmanCircleRadius);

		} catch (SlickException e) {
			System.out.println("Cannot load Pacman images.");
		}

	}

	protected void initDirMap() {
		dirMapX = new HashMap<Directions, Integer>();
		dirMapY = new HashMap<Directions, Integer>();
		Directions [] dindex = {
				Directions.STILL,
				Directions.LEFT,
				Directions.RIGHT,
				Directions.UP,
				Directions.DOWN
		};
		int [] dx = { 0, -1, 1, 0, 0 };
		int [] dy = { 0, 0, 0, -1, 1 };
		for (int i = 0; i < dindex.length; i++) {
			dirMapX.put(dindex[i], dx[i]);
			dirMapY.put(dindex[i], dy[i]);
		}
	}

	/**
	 * Implemented from the abstract method in BasicCharacter class,
	 * update method is called every frame of the game by the governing update method in Game class.
	 * it updates the positions (x, y) and directions (dir) of the pacman with processed key inputs.
	 * Update score based on whether location has dot.
	 */
	public void update(int delta, ArrayList<Shape> closeByWallShapes) {
		this.setWallShapesAroundPacman(closeByWallShapes);
		pacmanRightAnimation.update(delta);
		this.updatePacmanCirclePosition();
		if (isAtCellCenter(x, y)) {
			if(!dirMovable(nextDir)) {
				nextDir = Directions.STILL;
			}
			dir = nextDir;
		}
		updatePosition();
	}
	
	public void render(Graphics g) {
		this.pacmanRightAnimation.draw(this.x, this.y, elementPixelUnit, elementPixelUnit);
		if (isDebug) {
			g.draw(this.pacmanCircle);
		}

		if (this.isColliding) {
			g.drawString("PacmanCollision: true", 50, 60);
		}
		else {
			g.drawString("PacmanCollision: false", 50, 60);
		}
	}

	/**
	 * Return whether next position is accessible given dir.
	 * @param dir direction for next position
	 * @see Directions
	 * @return boolean whether next position is accessible given dir.
	 */
	private boolean dirMovable(Directions dir) {
		//System.out.println(dir);
		float nextX = x + elementPixelUnit * dirMapX.get(dir);
		float nextY = y + elementPixelUnit * dirMapY.get(dir);
		this.pacmanCircle.setCenterX(nextX + this.elementPixelUnit / 2);
		this.pacmanCircle.setCenterY(nextY + this.elementPixelUnit / 2);
		setIsColliding();
		updatePacmanCirclePosition();
		return !isColliding;
	}

	/**
	 * Return boolean whether current coordinate is at cell center.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean is at cell center
	 */
	public boolean isAtCellCenter(float x, float y) {
		//System.out.println(Math.round(x) + "," + Math.round(y));
		//System.out.println(Math.round(map.getClosestNonCollisionX(x))+","+Math.round(map.getClosestNonCollisionY(y)));
		boolean b = (Math.round(x) == Math.round(map.getClosestNonCollisionX(x))) &&
				(Math.round(y) == Math.round(map.getClosestNonCollisionY(y)));
		//System.out.println(b);
		return b;
	}

	/**
	 * This method updates the center x,y of the circle based on the x, y of the pacman animation.
	 */
	private void updatePacmanCirclePosition() {
		this.centerX = this.x + this.elementPixelUnit / 2;
		this.centerY = this.y + this.elementPixelUnit / 2;

		this.pacmanCircle.setCenterX(centerX);
		this.pacmanCircle.setCenterY(centerY);
	}

	/**
	 * Setter for nextDir
	 * @param nextDir nextDir to be set
	 */
	public void setNextDir(Directions nextDir) {
		this.nextDir = nextDir;
	}

	private void setWallShapesAroundPacman(ArrayList<Shape> wallShapesAroundPacman) {
		this.wallShapesAroundPacman = wallShapesAroundPacman;
	}

	public void setIsColliding() {
		for (Shape wall : this.wallShapesAroundPacman) {
			if (this.pacmanCircle.intersects(wall)) {
				this.isColliding = true;
				return;
			}
		}
		this.isColliding = false;
	}

	private void updatePosition() {
		//System.out.println("updatePosition"+dir);
		if (dir != Directions.STILL) {
			x += dirMapX.get(dir) * stepSize;
			y += dirMapY.get(dir) * stepSize;
		}
	}


	/**
	 * Getter for isAddScore
	 * @return boolean whether score should be incremented if dot is eaten.
	 */
	public boolean isAddScore() {
		return isAddScore;
	}

	/**
	 * Set isAddScore to true;
	 */
	private void addScore() {
		isAddScore = true;
	}

	/**
	 * Getter for x.
	 * @return x
	 */
	public float getX() {
		return x;
	}

	/**
	 * Getter for y.
	 * @return y
	 */
	public float getY() {
		return y;
	}

	public float getCenterX() {
		return centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	/**
	 * Getter for direction.
	 * @return dir
	 */
	public Directions getDir() {
		return dir;
	}

	/**
	 * Setter for direction.
	 * @param d direction to be set.
	 */
	public void setDir(Directions d) {
		dir = d;
	}

	/**
	 * Getter for nextDir.
	 * @return nextDir
	 */
	public Directions getNextDir() {
		return nextDir;
	}

	/**
	 * Setter for next direction.
	 * @param dir direction to be set.
	 */
	public void setNextDirection(Directions dir) {
		nextDir = dir;
	}
}

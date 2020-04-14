/**
 * Pacman is the class inherited from BasicCharacter class that contains all relevant 
 * fields and methods related to the pacman moving on the map. The only specification that 
 * needs to make in Pacman class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Pacman {

	public int stepSize = 0; // must be factor of blockSize (length of row and col)
	private Map map;
	private boolean isAddScore;
	private Directions nextDir;
	private int x;
	private int y;
	private Directions dir;	
	private String charImageLink;

	
	public Pacman(int x, int y, Directions dir, String charImageLink, Map m) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.charImageLink = charImageLink;
		map = m;
		nextDir = Directions.STILL;
	}

	/**
	 * Implemented from the abstract method in BasicCharacter class,
	 * update method is called every frame of the game by the governing update method in Game class.
	 * it updates the positions (x, y) and directions (dir) of the pacman with processed key inputs.
	 * Update score based on whether location has dot.
	 */
	public void update() {
		// TODO

		// Update position
		if (dir != Directions.STILL) {
			if(dir == Directions.UP) {
				y -= stepSize;
			}
			else if (dir == Directions.DOWN) {
				y += stepSize;
			}
			else {
				x = dir == Directions.LEFT ? x - stepSize : x + stepSize;
			}
		}
	}
	
	public void render() {
		
	}

	/**
	 * Return whether next position is accessible given dir.
	 * @param dir direction for next position
	 * @see Directions
	 * @return boolean whether next position is accessible given dir.
	 */
	private boolean dirMovable(Directions dir) {
		// TODO. Merge to BasicCharacter
		return false;
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
	public int getX() {
		return x;
	}

	/**
	 * Getter for y.
	 * @return y
	 */
	public int getY() {
		return y;
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
	 * Setter for nextDir
	 * @param nextDir nextDir to be set
	 */
	public void setNextDir(Directions nextDir) {
		this.nextDir = nextDir;
	}
}

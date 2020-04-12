/**
 * BasicCharacter is the base class for Ghost and Pacman classes to inherit from. It contains common fields for a character 
 * in the game such as x, y, and dir. It also contains common methods such as rendering the character from an image based on its 
 * x, y and dir.
 */
public abstract class BasicCharacter {
	// Fields are protected because we want to have direct access to them in both Ghost and Pacman
	// class while keeping them inaccessible for classes outside of the inheritence. 
	protected int x;
	protected int y;
	protected Directions dir;	
	protected String charImageLink;
	
	/**
	 * Initializes the positions and direction of the character, as well as the image of the character.
	 */
	public BasicCharacter(int x, int y, Directions dir, String charImageLink) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.charImageLink = charImageLink;
	}
	
	/**
	 * update method is called every frame of the game by the governing update method in Game class.
	 * it updates the positions (x, y) and directions (dir) of the character. Note that udpate method
	 * is an abstract method because pacman and ghosts have different implementation of this method due
	 * to their difference in controls (pacman is controlled by keyboard, ghosts are controlled
	 * by moving logic in code).
	 */
	public abstract void update();
	
	/**
	 * render method is called every frame of the game by the governing render method in Game class
	 * after calling the update method. It displays the character on the map based on the positions(x, y)
	 * and orientation(dir). The image of the character is provided by charImageLink field.
	 */
	public void render() {
		
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Directions getDirection() {
		return this.dir;
	}
	
}

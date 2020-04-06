/**
 * Pacman is the class inherited from BasicCharacter class that contains all relevant 
 * fields and methods related to the pacman moving on the map. The only specification that 
 * needs to make in Pacman class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Pacman extends BasicCharacter{
	
	public Pacman(int x, int y, Directions dir, String charImageLink) {
		super(x, y, dir, charImageLink);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Implemented from the abstract method in BasicCharacter class,
	 * update method is called every frame of the game by the governing update method in Game class.
	 * it updates the positions (x, y) and directions (dir) of the pacman with processed key inputs.
	 */
	public void update() {
	}
}

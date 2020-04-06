/**
 * Ghost is the class inherited from BasicCharacter class that contains all relevant 
 * fields and methods related to ghosts moving on the map. The only specification that 
 * needs to make in Ghost class against BasicCharacter class is to implement the abstract
 * method update();
 */
public class Ghost extends BasicCharacter {
	/**
	 * Implemented from the abstract method in BasicCharacter class,
	 * update method is called every frame of the game by the governing update method in Game class.
	 * it updates the positions (x, y) and directions (dir) of the Ghost without needing to process
	 * any keyboard input. 
	 */
	public void update() {
	}
}

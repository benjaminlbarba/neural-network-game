import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;

/**
 * Game is the class that combines all elements (map, ghost, pacman) to start and maintain the game using methods overridden from
 * the Java game library Slick2D. In addition, Game class handles connections between key pressed and game controls during game play.
 */
public class Game extends BasicGame{
	public int gameWindowHeight;
	public int gameWindowWidth;

	private Score score;
	private int[] controlKeys;
	
	private Map map;
	private Pacman pacman;
	// For the initial implementation, we are just implementing one ghost. After the basic game is created, we will set up more ghosts.
	private Ghost ghost;
	
	public Game(String title, int gameWindowWidth, int gameWindowHeight) {
		super(title);
		
		this.gameWindowWidth = gameWindowWidth;
		this.gameWindowHeight = gameWindowHeight;
		
		this.score = new Score();
		initializeControlKeys();
	}
	
	/**
	 * init method is overridden from BasicGame class, it initiates the display of the map, pacman, and ghosts
	 */
	@Override
	public void init(GameContainer gameContainer) {
		
	}
	
	/**
	 * update method is overridden from BasicGame class, it updates the positions (x, y) and directions (dir) of the pacman and ghosts, 
	 * as well as the dots on the map. Method update gets run every frame of the game.
	 */
	@Override
	public void update(GameContainer container, int delta) {
		
	}
	
	/**
	 * render method is overridden from BasicGame class, it gets executed after update method in every frame.
	 * It renders the updated map, ghosts, and pacman, based on the updated data.
	 */
	@Override
	public void render(GameCOntainer container, Graphics g) {
		
	}
	
	/**
	 * keyPressed method is overridden from BasicGame class, it gets called when a key is pressed on the keyboard. 
	 */
	@Override
	public void keyPressed(int key, char c) {
		
	}
	
	/**
	 * keyReleased method is overridden from BasicGame class, it gets called when a pressed key is released on the keyboard. 
	 */
	@Override
	public void keyReleased(int key, char c) {
		
	}
	
	/**
	 * Populates available key controls to controlKeys int array. Currently, we only allow up, down, left, right.
	 */
	private void initializeControlKeys() {
		
	}
	
}

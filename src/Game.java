import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;

/**
 * Game is the class that combines all elements (map, ghost, pacman) to start and maintain the game using methods overridden from
 * the Java game library Slick2D. In addition, Game class handles connections between key pressed and game controls during game play.
 */
public class Game extends BasicGame{
	private int gameWindowHeight;
	private int gameWindowWidth;
	
	// YQ: Based on the number of rows/columns on the provided map data
	// and the input window size, how many pixel (xy coordinate unit length)
	// is one row/column equal to.
	private float elementPixelUnit;
	
	// YQ: The ratio for how much width and height for map to be displayed on given window
	private float mapWindowRatio = (float) 0.7;

	private Score score;
	private int[] controlKeys;
	
	private char[][] mapData = MapCollections.map1;
	
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
		
		this.elementPixelUnit = this.getElementPixelUnit(
				mapData[0].length, 
				mapData.length, 
				gameWindowWidth, 
				gameWindowHeight);
		
		this.map = new Map(mapData, elementPixelUnit, this.getMapOriginX(), this.getMapOriginY());
		this.ghost = new Ghost(50, 50, elementPixelUnit);
	}
	
	// Fit the map fully to the window by returning the smaller convertionRatio between width and height
	private float getElementPixelUnit(int mapRowCount, int mapColumnCount, float gameWindowWidth,
			float gameWindowHeight) {
		float widthConversionRatio = (float) (gameWindowWidth * 1.00 / mapColumnCount);
		float heightConversionRatio = (float) (gameWindowHeight * 1.00 / mapRowCount);
		// when width restricts the size of the map on the game window
		return widthConversionRatio < heightConversionRatio 
				? widthConversionRatio * this.mapWindowRatio 
				: heightConversionRatio * this.mapWindowRatio;
	}
	
	// Find the x for origin of the map for it to be displayed at the center of the screen using 70% of the width 
	private float getMapOriginX() {
		return (this.gameWindowWidth - mapData[0].length * this.elementPixelUnit) / 2;
	}
	
	// Find the y for origin of the map for it to be displayed at the center of the screen using 70% of the height 
	private float getMapOriginY() {
		return (this.gameWindowHeight - mapData.length * this.elementPixelUnit) / 2;
	}

	/**
	 * init method is overridden from BasicGame class, it initiates the display of the map, pacman, and ghosts
	 */
	@Override
	public void init(GameContainer gameContainer) {
		this.map.init();
		this.ghost.init();
	}
	
	/**
	 * update method is overridden from BasicGame class, it updates the positions (x, y) and directions (dir) of the pacman and ghosts, 
	 * as well as the dots on the map. Method update gets run every frame of the game.
	 */
	@Override
	public void update(GameContainer container, int delta) {
		this.ghost.update();
		
	}
	
	/**
	 * render method is overridden from BasicGame class, it gets executed after update method in every frame.
	 * It renders the updated map, ghosts, and pacman, based on the updated data.
	 */
	@Override
	public void render(GameContainer container, Graphics g) {
		this.map.render();
		this.ghost.render();
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

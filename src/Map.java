import java.io.Console;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Map is in charge of the following functionalities: 
 * 1. Initialize the map in game play based on saved map data by drawing the walls and dots
 * 2. Maintain and update the displayed map with number of dots left.
 * 3. Outputs the count of total number of dots, and the number of dots left
 * TODO: Instead of saving the map data in this class, instead create a folder that holds multiple map files representing different maps to be used.
 * 
 */
public class Map {
	private int mapDataRowCount;
	private int mapDataColCount;
	
	private float elementPixelUnit;
	private float mapOriginX;
	private float mapOriginY;
	
	private char[][] mapData;
	
	private int initialDotCount;
	private int currentDotCount;
	
	private Image wallElementImage;
	private Image dotImage;
	
	public Map(char[][] mapData, float elementPixelUnit, float mapOriginX, float mapOriginY) {
		this.mapData = mapData;
		
		this.elementPixelUnit = elementPixelUnit;
		this.mapOriginX = mapOriginX;
		this.mapOriginY = mapOriginY;
		
		this.mapDataRowCount = mapData.length;
		this.mapDataColCount = mapData[0].length;
		this.initialDotCount = countMapDots();
	}
	
	/**
	 * init method here gets called in the init method in Game class. 
	 * It initiates the display of the map and dots.
	 */
	public void init() {
		try {
			this.wallElementImage = new Image("images/wallElement.jpg");
		} catch (SlickException e) {
			System.out.println("WallElement image cannot be found.");
		}
	}
	
	/**
	 * update method here gets called in the update method in Game class.
	 * It updates the positions of the dots on the map as well as the count of the remaining dots.
	 */
	public void update() {
		
	}
	
	/**
	 * render method here gets called in the render method in Game class, which gets 
	 * executed after update method in every frame.
	 * It renders the updated map based on the updated data (mainly updated location of dots).
	 */
	public void render() {
		for (int r = 0; r < this.mapDataRowCount; r++) {
			for (int c = 0; c < this.mapDataColCount; c++) {
				char elementSymbol = mapData[r][c];
				// wall
				if (elementSymbol == '#') {
					float x = getElementX(c);
					float y = getElementY(r);
					this.wallElementImage.draw(x, y, elementPixelUnit, elementPixelUnit);
				}
			}
		}
	}
	
	/**
	 * Counts the number of dots on the map to be used for calculating score.
	 */
	private int countMapDots() {
		return 0;
	}
	
	private float getElementX(int columnNumber) {
		return this.elementPixelUnit * columnNumber + this.mapOriginX;
	}
	
	private float getElementY(int rowNumber) {
		return this.elementPixelUnit * rowNumber + this.mapOriginY;
	}
}

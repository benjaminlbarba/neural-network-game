import java.io.Console;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

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
	
	private MapData mapData;
	private ArrayList<Shape> wallShapes = new ArrayList<Shape>();
	
	private int initialDotCount;
	private int currentDotCount;
	
	private Image wallElementImage;
	private Image dotImage;
	
	public Map(MapData mapData, float elementPixelUnit, float mapOriginX, float mapOriginY) {
		this.mapData = mapData;
		
		this.elementPixelUnit = elementPixelUnit;
		this.mapOriginX = mapOriginX;
		this.mapOriginY = mapOriginY;
		
		this.mapDataRowCount = mapData.mapArray.length;
		this.mapDataColCount = mapData.mapArray[0].length;
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
		this.drawWallsAndCreateWallShapes();
	}
	
	/**
	 * Counts the number of dots on the map to be used for calculating score.
	 */
	private int countMapDots() {
		return 0;
	}
	
	public float getXFromColNumber(int columnNumber) {
		return this.elementPixelUnit * columnNumber + this.mapOriginX;
	}
	
	public float getYFromRowNumber(int rowNumber) {
		return this.elementPixelUnit * rowNumber + this.mapOriginY;
	}
	
	private void drawWallsAndCreateWallShapes() {
		for (int r = 0; r < this.mapDataRowCount; r++) {
			for (int c = 0; c < this.mapDataColCount; c++) {
				char elementSymbol = this.mapData.mapArray[r][c];
				// wall
				if (elementSymbol == '#') {
					float x = this.getXFromColNumber(c);
					float y = this.getYFromRowNumber(r);
					this.wallElementImage.draw(x, y, this.elementPixelUnit, this.elementPixelUnit);
					this.wallShapes.add(new Rectangle(x, y, this.elementPixelUnit, this.elementPixelUnit));
				}
			}
		}
	}

	// Get wallShapes that are within 1.5 * elementPixelUnit for both x an dy because collision could only happen
	// with shapes nearby
	public ArrayList<Shape> getCloseByWallShapes(float x, float y) {
		ArrayList<Shape> closeByWallShapes = new ArrayList<Shape>(this.wallShapes);
		closeByWallShapes.removeIf(
			s -> 
				(Math.abs(s.getX() - x) > 1.5 * this.elementPixelUnit) || 
				(Math.abs(s.getY() - y) > 1.5 * this.elementPixelUnit)
		);
		
		return closeByWallShapes;
	}

}

import java.io.Console;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.newdawn.slick.Graphics;
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
	private boolean isDebug;

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
	private Image fruitImage;
	private Image dotImage;
	
	public Map(MapData mapData, float elementPixelUnit, float mapOriginX, float mapOriginY, boolean isDebug) {
		this.isDebug = isDebug;

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
			this.fruitImage = new Image("images/cherry.png");
			this.dotImage = new Image("images/dot.png");
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
	public void render(Graphics g) {
		this.drawWallsAndCreateWallShapes(g);
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
	
	private void drawWallsAndCreateWallShapes(Graphics g) {
		for (int r = 0; r < this.mapDataRowCount; r++) {
			for (int c = 0; c < this.mapDataColCount; c++) {
				char elementSymbol = this.mapData.mapArray[r][c];
				float x = this.getXFromColNumber(c);
				float y = this.getYFromRowNumber(r);
				if (elementSymbol == '#') { // wall
					Rectangle currentWallElementRec = new Rectangle(x, y, this.elementPixelUnit, this.elementPixelUnit);
					if (isDebug) {
						g.draw(currentWallElementRec);
					}
					else {
						this.wallElementImage.draw(x, y, this.elementPixelUnit, this.elementPixelUnit);
					}
					this.wallShapes.add(currentWallElementRec);
				} else if (elementSymbol == '.') { // dots
					this.dotImage.draw(x, y, this.elementPixelUnit, this.elementPixelUnit);
				} else { // bonus fruit
					this.fruitImage.draw(x, y, this.elementPixelUnit, this.elementPixelUnit);
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

	// This method provides x value for repositioning a character to its closest non-collision position on the map.
	public float getClosestNonCollisionX(float currentX) {
		int closestColNumber = this.getClosestCol(currentX);

		return this.getXFromColNumber(closestColNumber);
	}

	// This method provides y value for repositioning a character to its closest non-collision position on the map.
	public float getClosestNonCollisionY(float currentY) {
		int closestRowNumber = this.getClosestRow(currentY);

		return this.getYFromRowNumber(closestRowNumber);
	}

	private int getClosestCol(float currentX) {
		return Math.round((currentX - this.mapOriginX) / this.elementPixelUnit);
	}

	private int getClosestRow(float currentY) {
		return Math.round((currentY - this.mapOriginY) / this.elementPixelUnit);
	}

}

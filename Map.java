import java.util.Arrays;

/**
 * Map is in charge of the following functionalities: 
 * 1. Initialize the map in game play based on saved map data by drawing the walls and dots
 * 2. Maintain and update the displayed map with number of dots left.
 * 3. Outputs the count of total number of dots, and the number of dots left
 * TODO: Instead of saving the map data in this class, instead create a folder that holds multiple map files representing different maps to be used.
 * 
 */
public class Map {
	int rows = 10;
	int cols = 10;
	String[] map;
	
	public Map(int rows, int cols) {
		if (rows < 5 || rows > 30 || cols < 5 || cols > 30) {
			throw new IllegalArgumentException("Invalid rows or cols.");
		}
		this.rows = rows;
		this.cols = cols;
		this.map = initMap(rows, cols);
	}
	
	/**
	 * Generate a basic map
	 * @param rows
	 * @param cols
	 * @return String[]
	 */
	public String[] initMap(int rows, int cols) {
		String[] result = new String[rows];
		// Generate the String for the 1st row and the last row
		char[] charArr1 = new char[cols];
		Arrays.fill(charArr1, '#');
		// Generate the String for rows with label 1 to cols-2
		char[] charArr2 = new char[cols];
		Arrays.fill(charArr2, '.');
		charArr2[0] = '#';
		charArr2[cols - 1] = '#';
		// Fill the map with Strings
		Arrays.fill(result, new String(charArr2));
		result[0] = new String(charArr1);
		result[rows - 1] = new String(charArr1);
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("test");
	}
}

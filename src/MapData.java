
public class MapData {
	// Row and Column number (0-indexed) on a map where pacman start position is.
	// You can use getXFromColNumber and getYFromRowNumber in Map class to convert
	// row and column numbers to x, y coordinate on the rendered map.
	public int pacmanStartPointRowNumber;
	public int pacmanStartPointColNumber;
	
	// TODO support multiple ghosts.
	public int ghostStartPointRowNumber;
	public int ghostStartPointColNumber;

	public char[][] mapArray;
	
	public MapData(
			int pacmanStartPointRowNumber,
			int pacmanStartPointColNumber,
			int ghostStartPointRowNumber,
			int ghostStartPointColNumber,
			char[][] mapArray
			) {
		this.pacmanStartPointRowNumber = pacmanStartPointRowNumber;
		this.pacmanStartPointColNumber = pacmanStartPointColNumber;
		this.ghostStartPointRowNumber = ghostStartPointRowNumber;
		this.ghostStartPointColNumber = ghostStartPointColNumber;
		this.mapArray = mapArray;
	}
	
	
}

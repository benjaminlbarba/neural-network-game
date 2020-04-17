
public class MapCollections {
	private static char[][] map1Array = {
			{'#','#','#','#','#','#','#','#','#','#'},
			{'#','.','#','.','.','.','.','#','#','#'},
			{'#','.','#','.','#','#','.','#','#','#'},
			{'#','.','#','.','#','#','.','#','#','#'},
			{'#','.','#','.','#','#','.','#','#','#'},
			{'#','.','#','.','#','#','.','.','.','#'},
			{'#','.','#','.','#','#','.','#','.','#'},
			{'#','.','.','.','#','#','.','#','.','#'},
			{'#','#','#','#','#','#','.','.','.','#'},
			{'#','#','#','#','#','#','#','#','#','#'}
		};
	private static int pacmanStartPointRowNumber = 7;
	private static int pacmanStartPointColNumber = 1;
	private static int ghostStartPointRowNumber = 1;
	private static int ghostStartPointColNumber = 4;
	public static final MapData map1 = new MapData(
			pacmanStartPointRowNumber,
			pacmanStartPointColNumber,
			ghostStartPointRowNumber,
			ghostStartPointColNumber, 
			map1Array);
}

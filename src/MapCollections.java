
public class MapCollections {
	private static char[][][] mapsArray = {
			{
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
			},
			{
				{'#','#','#','#','#','#','#','#','#','#'},
				{'#','.','.','.','.','.','.','#','#','#'},
				{'#','.','.','.','.','#','.','#','#','#'},
				{'#','.','#','.','#','#','.','#','#','#'},
				{'#','.','#','.','#','#','.','#','#','#'},
				{'#','.','#','.','#','#','.','.','.','#'},
				{'#','.','#','.','#','#','.','#','.','#'},
				{'#','.','.','.','#','#','.','#','.','#'},
				{'#','#','#','#','#','#','.','.','.','#'},
				{'#','#','#','#','#','#','#','#','#','#'}
			},
			{
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#','*','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','*','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.','#'},
				{'#','*','.','.','.','#','.','.','*','#','*','.','.','#','.','.','.','*','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
			},
		};
	private static int[] pacmanStartPointRowNumbers = {7, 7, 7};
	private static int[] pacmanStartPointColNumbers = {1, 1, 1};
	private static int[] ghostStartPointRowNumbers = {1, 1, 1};
	private static int[] ghostStartPointColNumbers = {4, 4, 8};
	
	public static MapData getMapData(int index) {
		return new MapData(
				pacmanStartPointRowNumbers[index],
				pacmanStartPointColNumbers[index],
				ghostStartPointRowNumbers[index],
				ghostStartPointColNumbers[index], 
				mapsArray[index]);
	}
}

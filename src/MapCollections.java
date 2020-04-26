
public class MapCollections {
	private static char[][][] mapsArray = {
			{
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#',' ','.','.','.','.','.','*',' ',' ',' ',' ',' ',' ',' ',' ',' ','#','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
			},
			{
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#','*','.','.','.','.','.','.','.','#',' ','#','.','.','.','.','.','#','#'},
				{'#','#','#','#','#','#','#','.','#',' ','#','.','.','#','.','#','.','.','#'},
				{'#','.','#','.','#','.','.','.','#',' ','#','.','#','.','#','.','#','.','#'},
				{'#','.','#','.','#','#','#','.','#','#','#','.','#','.','#','.','#','.','#'},
				{'#','.','#','.','#','.','.','.','#','.','#','.','#','.','#','.','#','.','#'},
				{'#','.','#','.','#','#','#','.','#','.','#','.','#','.','#','.','#','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','#','#','.','#','#','#','#','.','#','#','.','#','#','#','#','.','#'},
				{'#','.','#','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','#'},
				{'#','.','#','#','.','#','#','#','#','.','#','#','.','#','#','#','#','.','#'},
				{'#','.','#','#','.','#','#','#','#','.','#','#','.','#','#','#','#','.','#'},
				{'#','.','#','.','.','.','.','.','#','.','.','.','.','.','.','.','#','.','#'},
				{'#','.','#','#','.','#','#','#','#','.','#','#','.','#','#','#','#','.','#'},
				{'#','*','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','*','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
			},
			{
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#','*','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','*','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.','#'},
				{'#','*','.','.','.','#','.','.','*','#','*','.','.','#','.','.','.','*','#'},
				{'#','#','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','#','#'},
				{'#','#','#','#','.','#','.','.','.','.','.','.','.','#','.','#','#','#','#'},
				{'#','#','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','#','#'},
				{'#','#','#','#','.','.','.','#','#','#','#','#','.','.','.','#','#','#','#'},
				{'#','#','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','#','#'},
				{'#','#','#','#','.','#','.','.','.','.','.','.','.','#','.','#','#','#','#'},
				{'#','#','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','#','#'},
				{'#','*','.','.','.','#','.','.','*','#','*','.','.','#','.','.','.','*','#'},
				{'#','.','#','#','.','#','.','#','#','#','#','#','.','#','.','#','#','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','*','.','.','.','.','.','.','.','#','.','.','.','.','.','.','.','*','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
			},
			{
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#',' ','.','.','.','.','.','#','.','.','.','#','.','.','.','.','.','*','#'},
				{'#','.','#','#','.','#','.','#','.','#','.','#','.','#','.','#','#','.','#'},
				{'#','.','.','.','.','#','.','.','.','#','.','.','.','#','.','.','.','.','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','.','#','*','.','.','.','.','.','#','.','.','.','.','.','*','#','.','#'},
				{'#','.','#','.','#','#','.','#','#','#','#','#','.','#','#','.','#','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','#','#','.','#','.','#','#','.','#','#','.','#','.','#','#','.','#'},
				{'#','.','#','#','.','#','.','#','*','.','*','#','.','#','.','#','#','.','#'},
				{'#','.','.','.','.','#','.','#','.','#','.','#','.','#','.','.','.','.','#'},
				{'#','.','#','#','.','#','.','#','.','.','.','#','.','#','.','#','#','.','#'},
				{'#','.','#','#','.','#','.','#','#','.','#','#','.','#','.','#','#','.','#'},
				{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
				{'#','.','#','.','#','#','.','#','#','#','#','#','.','#','#','.','#','.','#'},
				{'#','.','#','.','.','.','.','.','*','#','*','.','.','.','.','.','#','.','#'},
				{'#','.','#','#','.','#','#','#','.','#','.','#','#','#','.','#','#','.','#'},
				{'#','.','.','.','.','#','.','.','.','#','.','.','.','#','.','.','.','.','#'},
				{'#','.','#','#','.','#','.','#','.','#','.','#','.','#','.','#','#','.','#'},
				{'#','*','.','.','.','.','.','#','.','.','.','#','.','.','.','.','.','*','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
			}
		};

	private static final RowColTuple[] pacmanRowColTupleArray = {
			new RowColTuple(1, 1),
			new RowColTuple(14, 10),
			new RowColTuple(19, 15),
			new RowColTuple(1, 1),
	};

	// Each map hosts more than one ghost
	private static final RowColTuple[][] ghostRowColTuplesArrary = {
			{new RowColTuple(1, 15)},
			{new RowColTuple(1, 1)},
			{new RowColTuple(4, 9), new RowColTuple(4, 10)},
			{new RowColTuple(1, 6), new RowColTuple(1, 8), new RowColTuple(1, 13), new RowColTuple(5, 10)},
	};
	
	public static final int[] targetScoreArray = {160, 500, 500, 500};

	public static int getAvailableMapCount() {
		return mapsArray.length;
	}

	public static MapData getMapData(int index) {
		return new MapData(
				pacmanRowColTupleArray[index],
				ghostRowColTuplesArrary[index],
				mapsArray[index]);
	}
}

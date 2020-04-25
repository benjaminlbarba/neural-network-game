import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapTest {
    MapData mapData = MapCollections.getMapData(1);
    Map map = new Map(mapData, 22, 190, 67, false);

    @Test
    void update() {
        assertEquals(map.update(212, 90), 100);
    }


//	Map classUnderTest = new Map(5, 5);
//
//	@SuppressWarnings("deprecation")
//	@Test
//	void initMapTest() {
//		int rows = 5;
//		int cols = 5;
//		String[] outputMap = classUnderTest.initMap(rows, cols);
//		String[] expectedMap = {
//			"#####",
//			"#...#",
//			"#...#",
//			"#...#",
//			"#####"
//		};
//		assertEquals(outputMap, expectedMap);
//	}

}

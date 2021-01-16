package viewer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jxmapviewer.viewer.GeoPosition;

import static junit.framework.TestCase.*;


public class GeoPositionTest {
    GeoPosition gp;
    double testLatitude = 46.62532;
    double testLongitude = 14.30702;
    String expectedTextRepresentation = "[46.62532, 14.30702]";

    @BeforeEach
    public void initEach() {
        gp = new GeoPosition(testLatitude, testLongitude);
    }

    @Test
    public void geoPositionConstructorTest() {
        // Initialize with geoposition from Klagenfurt
        gp = new GeoPosition(46, 37, 31, 14, 18, 25);
        assertEquals(gp.getLongitude(), testLongitude, 0.001);
        assertEquals(gp.getLatitude(), testLatitude, 0.001);
    }

    @Test
    public void doubleArrayConstructorTest() {
        double[] coordinateArray = new double[]{testLatitude, testLongitude};
        GeoPosition gp = new GeoPosition(coordinateArray);
        assertEquals(gp.getLatitude(), testLatitude);
        assertEquals(gp.getLongitude(), testLongitude);
    }

    @Test
    public void getterTest() {
        // assert statements
        double defaultDelta = 0.1;
        assertEquals(testLatitude, gp.getLatitude(), defaultDelta);
        assertEquals(testLongitude, gp.getLongitude(), defaultDelta);
    }

    @Test
    public void hashCodeTest() {
        int hashCode = gp.hashCode();
        int expectedHashCode = 1093660907;
        assertEquals(expectedHashCode, hashCode);
    }

    @Test
    public void toStringTest() {
        String expected = expectedTextRepresentation;
        assertEquals(expected, gp.toString());
    }

    @Test
    public void equalsTest() {
        // A second location with the same coordinates should yield equality
        GeoPosition gp2 = new GeoPosition(testLatitude, testLongitude);
        assertEquals(gp, gp2);

        // Comparison with self is always true
        assertEquals(gp, gp);

        // Comparison with null is always false
        assertFalse(gp.equals(null));

        // Compare to a different object class is always false
        assertFalse(gp.equals("A string"));

        // Compare against different latitude should yield false
        GeoPosition gpDifferentLongitude = new GeoPosition(testLatitude,2);
        assertFalse(gp.equals(gpDifferentLongitude));

        // Compare against different longitude should yield false
        GeoPosition gpDifferentLatitude = new GeoPosition(2, testLongitude);
        assertFalse(gp.equals(gpDifferentLatitude));
    }
}

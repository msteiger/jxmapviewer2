package viewer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jxmapviewer.viewer.GeoPosition;

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
        Assertions.assertEquals(gp.getLongitude(), testLongitude, 0.001);
        Assertions.assertEquals(gp.getLatitude(), testLatitude, 0.001);
    }

    @Test
    public void doubleArrayConstructorTest() {
        double[] coordinateArray = new double[]{testLatitude, testLongitude};
        GeoPosition gp = new GeoPosition(coordinateArray);
        Assertions.assertEquals(gp.getLatitude(), testLatitude);
        Assertions.assertEquals(gp.getLongitude(), testLongitude);
    }

    @Test
    public void getterTest() {
        // assert statements
        double defaultDelta = 0.1;
        Assertions.assertEquals(testLatitude, gp.getLatitude(), defaultDelta);
        Assertions.assertEquals(testLongitude, gp.getLongitude(), defaultDelta);
    }

    @Test
    public void hashCodeTest() {
        int hashCode = gp.hashCode();
        int expectedHashCode = 1093660907;
        Assertions.assertEquals(expectedHashCode, hashCode);
    }

    @Test
    public void toStringTest() {
        String expected = expectedTextRepresentation;
        Assertions.assertEquals(expected, gp.toString());
    }

    @Test
    public void equalsTest() {
        // A second location with the same coordinates should yield equality
        GeoPosition gp2 = new GeoPosition(testLatitude, testLongitude);
        Assertions.assertEquals(gp, gp2);

        // Comparison with self is always true
        Assertions.assertEquals(gp, gp);

        // Comparison with null is always false
        Assertions.assertNotEquals(gp, null);

        // Compare to a different object class is always false
        Assertions.assertNotEquals(gp, "A string");

        // Compare against different latitude should yield false
        GeoPosition gpDifferentLongitude = new GeoPosition(testLatitude,2);
        Assertions.assertNotEquals(gpDifferentLongitude, gp);

        // Compare against different longitude should yield false
        GeoPosition gpDifferentLatitude = new GeoPosition(2, testLongitude);
        Assertions.assertNotEquals(gpDifferentLatitude, gp);
    }
}

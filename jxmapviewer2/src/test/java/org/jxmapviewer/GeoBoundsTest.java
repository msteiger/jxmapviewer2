package org.jxmapviewer;

import org.junit.Test;
import org.jxmapviewer.viewer.GeoBounds;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class GeoBoundsTest {
    @Test
    public void geoBounds_with_extend() {
        GeoBounds bounds = new GeoBounds(50.0, 8.0, 50.1, 8.1);
    }

    @Test
    public void geoBounds_horizontal_track() {
        GeoBounds bounds = new GeoBounds(50.0, 8.0, 50.0, 8.1);
    }

    @Test
    public void geoBounds_vertical_track() {
        GeoBounds bounds = new GeoBounds(50.0, 8.0, 50.1, 8.0);
    }

    @Test
    public void geoBounds_point() {
        GeoBounds bounds = new GeoBounds(50.0, 8.0, 50.0, 8.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void geoBounds_invalid_latitudes() {
        GeoBounds bounds = new GeoBounds(50.1, 8.0, 50.0, 8.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void geoBounds_invalid_longitudes() {
        GeoBounds bounds = new GeoBounds(50.0, 8.1, 50.1, 8.0);
    }

    @Test
    public void geoBounds_intersect() {
        GeoBounds bounds1 = new GeoBounds(50.0, 8.0, 50.2, 8.2);
        GeoBounds bounds2 = new GeoBounds(50.1, 8.1, 50.3, 8.3);
        assertTrue(bounds1.intersects(bounds2));
    }

    @Test
    public void geoBounds_do_not_intersect() {
        GeoBounds bounds1 = new GeoBounds(50.0, 8.0, 50.1, 8.1);
        GeoBounds bounds2 = new GeoBounds(50.2, 8.2, 50.3, 8.3);
        assertFalse(bounds1.intersects(bounds2));
    }

    @Test
    public void geoBounds_horizontal_and_vertical_track_do_not_intersect() {
        GeoBounds horizontalTrackBounds = new GeoBounds(50.0, 7.9, 50.0, 8.1);
        GeoBounds verticalTrackBounds = new GeoBounds(49.9, 8.0, 50.1, 8.0);
        assertFalse(horizontalTrackBounds.intersects(verticalTrackBounds));
    }

    @Test
    public void geoBounds_pacific_bounds_intersect() {
        GeoBounds pacific1Bounds = new GeoBounds(50.0, 179.9, 50.2, -179.9);
        GeoBounds pacific2Bounds = new GeoBounds(50.1, -180.0, 50.3, -179.8);
        assertTrue(pacific1Bounds.intersects(pacific2Bounds));
    }
}

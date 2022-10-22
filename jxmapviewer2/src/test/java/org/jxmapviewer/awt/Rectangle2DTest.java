package org.jxmapviewer.awt;

import org.junit.Test;
import org.jxmapviewer.viewer.GeoBounds;

import java.awt.geom.Rectangle2D;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class Rectangle2DTest {

    @Test
    public void test_zero_width_and_zero_height_rect_do_not_intersect() {
        Rectangle2D horizontalTrackRect = new Rectangle2D.Double(50.0, 7.9, 0, 0.2);
        Rectangle2D verticalTrackRect = new Rectangle2D.Double(49.9, 8.0, 0.2, 0);
        assertFalse(horizontalTrackRect.intersects(verticalTrackRect));
    }

    @Test
    public void test_zero_height_rect_does_not_intersect() {
        Rectangle2D horizontalTrackRect = new Rectangle2D.Double(50.0, 7.9, 0.00001, 0.2);
        Rectangle2D verticalTrackRect = new Rectangle2D.Double(49.9, 8.0, 0.2, 0);
        assertFalse(horizontalTrackRect.intersects(verticalTrackRect));
    }

    @Test
    public void test_rectangles_intersect() {
        Rectangle2D horizontalTrackRect = new Rectangle2D.Double(50.0, 7.9, 0.00001, 0.2);
        Rectangle2D verticalTrackRect = new Rectangle2D.Double(49.9, 8.0, 0.2, 0.00001);
        assertTrue(horizontalTrackRect.intersects(verticalTrackRect));
    }
}

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class JXMapViewerTest {

    JXMapViewer mapViewer;


    @BeforeEach
    public void setupBeforeEach() {
        mapViewer = new JXMapViewer();

        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new TileFactoryInfo("EmptyTileFactory 256x256", 1, 15, 17, 256, true, true, "", "x", "y", "z");
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        // Set the focus
        GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(frankfurt);
    }

    @Test
    public void getCenterTest() {
        Point2D p = mapViewer.getCenter();
        Assertions.assertEquals(137392.5831111111, p.getX());
        Assertions.assertEquals(88780.01215942061, p.getY());
    }

    @Test
    public void setDesignTimeTest() {
        Assertions.assertFalse(mapViewer.isDesignTime());
        mapViewer.setDesignTime(true);
        Assertions.assertTrue(mapViewer.isDesignTime());
    }

    @Test
    public void setZoomTest() {
        Assertions.assertEquals(7, mapViewer.getZoom());
        mapViewer.setZoom(6);
        Assertions.assertEquals(6, mapViewer.getZoom());
    }

    @Test
    public void zoomToBestFitTest() {
        // zoom out
        Set<GeoPosition> postitions = new HashSet<>();
        GeoPosition topRight = new GeoPosition(46.6485, 14.3782);
        postitions.add(topRight);
        GeoPosition bottomLeft = new GeoPosition(46.5938, 14.2464);
        postitions.add(bottomLeft);
        mapViewer.zoomToBestFit(postitions, 0.1);

        // map should have zoomed in on Klagenfurt
        // TODO: zoom stays at initial value
        Assertions.assertEquals(7, mapViewer.getZoom());
        GeoPosition gp = mapViewer.getCenterPosition();
        Assertions.assertTrue(gp.getLatitude() > bottomLeft.getLatitude());
        Assertions.assertTrue(gp.getLongitude() > bottomLeft.getLongitude());
        Assertions.assertTrue(gp.getLatitude() < topRight.getLatitude());
        Assertions.assertTrue(gp.getLongitude() < topRight.getLongitude());
    }

    @Test
    public void panningEnabledTest() {
        // Initially panning is enabled
        Assertions.assertTrue(mapViewer.isPanningEnabled());

        // Disable panning
        mapViewer.setPanEnabled(false);
        Assertions.assertFalse(mapViewer.isPanningEnabled());
    }
}

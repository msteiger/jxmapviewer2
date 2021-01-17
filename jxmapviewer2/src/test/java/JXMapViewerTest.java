import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

public class JXMapViewerTest {

    JXMapViewer mapViewer;


    @BeforeEach
    public void setup() {
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

}

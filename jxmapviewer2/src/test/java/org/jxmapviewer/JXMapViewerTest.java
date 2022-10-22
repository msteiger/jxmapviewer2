package org.jxmapviewer;

import org.junit.Test;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JXMapViewerTest {

    /**
     * 21.10.2022: green
     */
    @Test
    public void zoomToBestFit_diagonal_track() {
        JXMapViewer mapViewer = new JXMapViewer();

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        mapViewer.setSize(100, 100);
        GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
        GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
        List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        assertEquals(12, mapViewer.getZoom());
    }

    /**
     *
     * 21.10.2022: red: Method zoomToBestFit throws
     * java.lang.IllegalArgumentException: GeoBounds is not valid - minLat must be less that maxLat.
     */
    @Test
    public void zoomToBestFit_horizontal_track_same_latitude() {
        JXMapViewer mapViewer = new JXMapViewer();

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        mapViewer.setSize(100, 100);
        GeoPosition position1 = new GeoPosition(50,  5, 0, 8, 41, 0);
        GeoPosition position2 = new GeoPosition(50,  5, 0, 8, 14, 0);
        List<GeoPosition> track = Arrays.asList(position1, position2);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        assertEquals(12, mapViewer.getZoom());
    }

    /**
     *
     * 21.10.2022: red: Method zoomToBestFit throws
     * java.lang.IllegalArgumentException: GeoBounds is not valid - minLng must be less that maxLng or
     * minLng must be greater than 0 and maxLng must be less than 0.
     */
    @Test
    public void zoomToBestFit_vertical_track_same_longitude() {
        JXMapViewer mapViewer = new JXMapViewer();

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        mapViewer.setSize(100, 100);
        GeoPosition position1 = new GeoPosition(50.7, 8.41);
        GeoPosition position2 = new GeoPosition(50.5, 8.41);
        List<GeoPosition> track = Arrays.asList(position1, position2);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        assertEquals(11, mapViewer.getZoom());
    }

    @Test
    public void zoomToBestFit_slightly_not_vertical_track() {
        JXMapViewer mapViewer = new JXMapViewer();

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        mapViewer.setSize(100, 100);
        GeoPosition position1 = new GeoPosition(50.7, 8.41);
        GeoPosition position2 = new GeoPosition(50.5, 8.41001);
        List<GeoPosition> track = Arrays.asList(position1, position2);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        assertEquals(11, mapViewer.getZoom());
    }

    /**
     * zooms in as much as possible,
     */
    @Test
    public void zoomToBestFit_point() {
        JXMapViewer mapViewer = new JXMapViewer();

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        mapViewer.setSize(100, 100);
        GeoPosition position1 = new GeoPosition(50,  7, 0, 8, 41, 0);
        GeoPosition position2 = new GeoPosition(50,  7, 0, 8, 41, 0);
        List<GeoPosition> track = Arrays.asList(position1, position2);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        assertEquals(0, mapViewer.getZoom());
    }

    /**
     * zooms in as much as possible,
     */
    @Test
    public void zoomToBestFit_very_close_points() throws InterruptedException {
        JXMapViewer mapViewer = new JXMapViewer();

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);

        mapViewer.setSize(100, 100);
        GeoPosition position1 = new GeoPosition(50.5, 8.3);
        GeoPosition position2 = new GeoPosition(50.500000001, 8.300000001);
        List<GeoPosition> track = Arrays.asList(position1, position2);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
        assertEquals(0, mapViewer.getZoom());

        /*
        JFrame f = new JFrame();
        f.setVisible(true);
        f.setSize(200,200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(mapViewer);
        Thread.sleep(5000);
         */
    }
}

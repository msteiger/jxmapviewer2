package org.jxmapviewer;

import org.junit.Test;
import org.jxmapviewer.viewer.GeoPosition;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class JXMapViewerTest {

    /**
     * 21.10.2022: green
     */
    @Test
    public void zoomToBestFit_diagonal_track() {
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setSize(100, 100);
        GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
        GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
        List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
    }

    /**
     *
     * 21.10.2022: red: Method zoomToBestFit throws
     * java.lang.IllegalArgumentException: GeoBounds is not valid - minLat must be less that maxLat.
     */
    @Test
    public void zoomToBestFit_horizontal_track_same_latitude() {
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setSize(100, 100);
        GeoPosition position1 = new GeoPosition(50,  5, 0, 8, 41, 0);
        GeoPosition position2 = new GeoPosition(50,  5, 0, 8, 14, 0);
        List<GeoPosition> track = Arrays.asList(position1, position2);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
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
        mapViewer.setSize(100, 100);
        GeoPosition position1 = new GeoPosition(50,  7, 0, 8, 41, 0);
        GeoPosition position2 = new GeoPosition(50,  5, 0, 8, 41, 0);
        List<GeoPosition> track = Arrays.asList(position1, position2);

        mapViewer.zoomToBestFit(new HashSet<GeoPosition>(track), 0.7);
    }
}

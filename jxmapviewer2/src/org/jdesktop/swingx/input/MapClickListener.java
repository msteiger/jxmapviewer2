package org.jdesktop.swingx.imput;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * Listens to single mouse clicks on the map and returns the GeoPosition
 *
 * @author Richard Eigenmann
 */
public abstract class MapClickListener extends MouseAdapter {

    private final JXMapViewer viewer;

    /**
     * Creates a mouse listener for the jxmapviewer which returns the
     * GeoPosition of the the point where the mouse was clicked.
     *
     * @param viewer the jxmapviewer
     */
    public MapClickListener( JXMapViewer viewer ) {
        this.viewer = viewer;
    }

    /**
     * Gets called on mouseClicked events, calculates the GeoPosition and fires
     * the mapClicked method that the extending class needs to implement.
     *
     * @param evt
     */
    @Override
    public void mouseClicked( MouseEvent evt ) {
        final boolean left = SwingUtilities.isLeftMouseButton( evt );
        final boolean singleClick = ( evt.getClickCount() == 1 );

        if ( ( left && singleClick ) ) {
            Rectangle bounds = viewer.getViewportBounds();
            double x = bounds.getX() + evt.getX();
            double y = bounds.getY() + evt.getY();
            Point2D pixelCoordinates = new Point2D.Double( x, y );
            mapClicked( viewer.getTileFactory().pixelToGeo( pixelCoordinates, viewer.getZoom() ) );
        }
    }

    /**
     * This method needs to be implemented in the extending class to handle the
     * map Clicked event.<p>
     *
     * For example you can print the coordinates like this:
     * <p>
     *
     * {@code System.out.println( String.format("The latitude is: %f  longitude is: %f", location.getLatitude(), location.getLongitude() ) );
     * }
     *
     * @param location The GeoPosition of the click event
     */
    public abstract void mapClicked( GeoPosition location );
}

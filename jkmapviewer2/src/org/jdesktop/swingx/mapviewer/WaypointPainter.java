/*
 * WaypointMapOverlay.java
 *
 * Created on April 1, 2006, 4:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.swingx.mapviewer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.AbstractPainter;

/**
 * Paints waypoints on the JXMapViewer. This is an instance of
 * Painter that only can draw on to JXMapViewers.
 * @author rbair
 */
public class WaypointPainter<T extends JXMapViewer> extends AbstractPainter<T> {
    private WaypointRenderer renderer = new DefaultWaypointRenderer();
    private Set<Waypoint> waypoints;
    
    /**
     * Creates a new instance of WaypointPainter
     */
    public WaypointPainter() {
        setAntialiasing(true);
        setCacheable(false);
        waypoints = new HashSet<Waypoint>();
    }
    
    /**
     * Sets the waypoint renderer to use when painting waypoints
     * @param r the new WaypointRenderer to use
     */
    public void setRenderer(WaypointRenderer r) {
        this.renderer = r;
    }
    
    /**
     * Gets the current set of waypoints to paint
     * @return a typed Set of Waypoints
     */
    public Set<Waypoint> getWaypoints() {
        return waypoints;
    }
    
    /**
     * Sets the current set of waypoints to paint
     * @param waypoints the new Set of Waypoints to use
     */
    public void setWaypoints(Set<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }
    
    /**
     * {@inheritDoc}
     * @param g
     * @param map
     * @param width
     * @param height
     */
    @Override
    protected void doPaint(Graphics2D g, T map, int width, int height) {
        if (renderer == null) {
            return;
        }
        
        //figure out which waypoints are within this map viewport
        //so, get the bounds
        Rectangle viewportBounds = map.getViewportBounds();
        int zoom = map.getZoom();
        Dimension sizeInTiles = map.getTileFactory().getMapSize(zoom);
        int tileSize = map.getTileFactory().getTileSize(zoom);
        Dimension sizeInPixels = new Dimension(sizeInTiles.width*tileSize, sizeInTiles.height*tileSize);

        double vpx = viewportBounds.getX();
        // normalize the left edge of the viewport to be positive
        while(vpx < 0) {
            vpx += sizeInPixels.getWidth();
        }
        // normalize the left edge of the viewport to no wrap around the world
        while(vpx > sizeInPixels.getWidth()) {
            vpx -= sizeInPixels.getWidth();
        }
        
        // create two new viewports next to eachother
        Rectangle2D vp2 = new Rectangle2D.Double(vpx,
                viewportBounds.getY(),viewportBounds.getWidth(),viewportBounds.getHeight());
        Rectangle2D vp3 = new Rectangle2D.Double(vpx-sizeInPixels.getWidth(),
                viewportBounds.getY(),viewportBounds.getWidth(),viewportBounds.getHeight());
        
        //for each waypoint within these bounds
        for (Waypoint w : getWaypoints()) {
            Point2D point = map.getTileFactory().geoToPixel(w.getPosition(), map.getZoom());
            if (vp2.contains(point)) {
                int x = (int)(point.getX() - vp2.getX());
                int y = (int)(point.getY() - vp2.getY());
                g.translate(x,y);
                paintWaypoint(w, map, g);
                g.translate(-x,-y);
            }
            if (vp3.contains(point)) {
                int x = (int)(point.getX() - vp3.getX());
                int y = (int)(point.getY() - vp3.getY());
                g.translate(x,y);
                paintWaypoint(w, map, g);
                g.translate(-x,-y);
            }
        }
    }
    
    /**
     * <p>Override this method if you want more control over how a
     * waypoint is painted than what you can get by just
     * plugging in a custom waypoint renderer. Most developers should not
     * need to override this method and can use a WaypointRenderer
     * instead.</p>
     *
     *
     * <p>This
     * method will be called to each waypoint with the
     * graphics object pre-translated
     * so that 0,0 is at the center of the waypoint.
     * This saves the developer from having
     * to deal with lat/long => screen coordinate transformations.</p>
     * @param w the current waypoint
     * @param map the current map
     * @param g the current graphics context
     * @see setRenderer(WaypointRenderer)
     * @see WaypointRenderer
     */
    protected void paintWaypoint(final Waypoint w, final T map, final Graphics2D g) {
        renderer.paintWaypoint(g, map, w);
    }
    
    private static void p(String str) {
        System.out.println(str);
    }
    
}

/*
 * Waypoint.java
 *
 * Created on March 30, 2006, 5:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.swingx.mapviewer;

import org.jdesktop.beans.AbstractBean;


/**
 * A Waypoint is a GeoPosition that can be drawn on a may using 
 * a WaypointPainter.
 * @author joshy
 */
public class Waypoint extends AbstractBean {
    private GeoPosition position;

    /**
     * Creates a new instance of Waypoint at lat/long 0,0
     */
    public Waypoint() {
        this(new GeoPosition(0, 0));
    }
    
    /**
     * Creates a new instance of Waypoint at the specified
     * latitude and longitude
     * @param latitude new latitude
     * @param longitude new longitude
     */
    public Waypoint(double latitude, double longitude) {
        this(new GeoPosition(latitude,longitude));
    }
    
    /**
     * Creates a new instance of Waypoint at the specified
     * GeoPosition
     * @param coord a GeoPosition to initialize the new Waypoint
     */
    public Waypoint(GeoPosition coord) {
        this.position = coord;
    }
    
    /**
     * Get the current GeoPosition of this Waypoint
     * @return the current position
     */
    public GeoPosition getPosition() {
        return position;
    }

    /**
     * Set a new GeoPosition for this Waypoint
     * @param coordinate a new position
     */
    public void setPosition(GeoPosition coordinate) {
        GeoPosition old = getPosition();
        this.position = coordinate;
        firePropertyChange("position", old, getPosition());
    }
    
    
}

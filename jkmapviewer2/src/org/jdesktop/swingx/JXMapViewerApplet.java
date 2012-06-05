/*
 * JXMapViewerApplet.java
 *
 * Created on December 19, 2006, 11:51 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.swingx;

import java.util.HashSet;
import java.util.Set;
import javax.swing.JApplet;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;

/**
 *
 * @author joshy
 */
public class JXMapViewerApplet extends JApplet {
    protected JXMapKit kit;
    /** Creates a new instance of JXMapViewerApplet */
    public JXMapViewerApplet() {
    }
    
    public void init() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
            e.printStackTrace();
        }
    }
    
    protected void createGUI() {
        kit = new JXMapKit();
        GeoPosition origin = new GeoPosition(0,0);
        GeoPosition sanjose = new GeoPosition(37,20,0, -121,-53,0);
        GeoPosition statlib = new GeoPosition(40,41,20, -74,-2,-42.4);
        Set set  = new HashSet();
        set.add(new Waypoint(statlib));
        set.add(new Waypoint(sanjose));
        WaypointPainter wp = new WaypointPainter();
        wp.setWaypoints(set);
        kit.getMainMap().setOverlayPainter(wp);
        kit.getMainMap().setCenterPosition(new GeoPosition(38.5,-100));
        kit.setZoom(2);
        this.add(kit);
    }
    

}

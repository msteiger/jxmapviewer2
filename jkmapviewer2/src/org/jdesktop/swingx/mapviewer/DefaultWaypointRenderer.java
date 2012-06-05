/*
 * WaypointRenderer.java
 *
 * Created on March 30, 2006, 5:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.swingx.mapviewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.jdesktop.swingx.JXMapViewer;

/**
 * This is a standard waypoint renderer. It draws all waypoints as blue
 * circles with crosshairs over the waypoint center
 * @author joshy
 */
public class DefaultWaypointRenderer implements WaypointRenderer {
    BufferedImage img = null;
    
    public DefaultWaypointRenderer() {
        try {
            img = ImageIO.read(getClass().getResource("resources/standard_waypoint.png"));
        } catch (Exception ex) {
            System.out.println("couldn't read standard_waypoint.png");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    /**
     * {@inheritDoc}
     * @param g
     * @param map
     * @param waypoint
     * @return
     */
    public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint waypoint) {
        if(img != null) {
            g.drawImage(img,-img.getWidth()/2,-img.getHeight(),null);
        } else {
            g.setStroke(new BasicStroke(3f));
            g.setColor(Color.BLUE);
            g.drawOval(-10,-10,20,20);
            g.setStroke(new BasicStroke(1f));
            g.drawLine(-10,0,10,0);
            g.drawLine(0,-10,0,10);
        }
        return false;
    }
}


package org.jxmapviewer.input;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapViewer;

/**
 * zooms to the current mouse cursor 
 * using the mouse wheel
 * @author Martin Steiger
 */
public class ZoomMouseWheelListenerCursor implements MouseWheelListener
{
    private JXMapViewer viewer;
    
    /**
     * @param viewer the jxmapviewer
     */
    public ZoomMouseWheelListenerCursor(JXMapViewer viewer)
    {
        this.viewer = viewer;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent evt)
    {
        Point current = evt.getPoint();
        Rectangle bound = viewer.getViewportBounds();
        
        double dx = current.x - bound.width / 2;
        double dy = current.y - bound.height / 2;
        
        Dimension oldMapSize = viewer.getTileFactory().getMapSize(viewer.getZoom());

        viewer.setZoom(viewer.getZoom() + evt.getWheelRotation());
        
        Dimension mapSize = viewer.getTileFactory().getMapSize(viewer.getZoom());

        Point2D center = viewer.getCenter();

        double dzw = (mapSize.getWidth() / oldMapSize.getWidth());
        double dzh = (mapSize.getHeight() / oldMapSize.getHeight());

        double x = center.getX() + dx * (dzw - 1);
        double y = center.getY() + dy * (dzh - 1);

        viewer.setCenter(new Point2D.Double(x, y));
    }
}

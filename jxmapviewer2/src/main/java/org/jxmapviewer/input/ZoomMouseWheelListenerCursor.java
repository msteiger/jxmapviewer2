
package org.jxmapviewer.input;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapViewer;

/**
 * Zooms to the current mouse cursor using the mouse wheel.
 *
 * <p>This input listener can be disabled. It is enabled by default. When it is
 * disabled, mouse wheel events will have no effect.
 *
 * @author Martin Steiger
 */
public class ZoomMouseWheelListenerCursor extends DisableableMouseWheelListener implements MouseWheelListener, DisableableInputAdapter
{
    /**
     * @param viewer the jxmapviewer
     */
    public ZoomMouseWheelListenerCursor(JXMapViewer viewer)
    {
        super(viewer);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent evt)
    {
        if (!enabled)
        {
            return;
        }
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

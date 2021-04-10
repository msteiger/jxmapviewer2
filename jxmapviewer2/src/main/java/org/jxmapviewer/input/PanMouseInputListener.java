package org.jxmapviewer.input;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import org.jxmapviewer.JXMapViewer;

/**
 * Used to pan using press and drag mouse gestures.
 *
 * <p>This input listener can be disabled. It is enabled by default. When it is
 * disabled, mouse events will have no effect.
 *
 * @author joshy
 */
public class PanMouseInputListener extends MouseInputAdapter implements DisableableInputAdapter
{
    private Point prev;
    private JXMapViewer viewer;
    private Cursor priorCursor;
    private boolean enabled = true;

    /**
     * @param viewer the jxmapviewer
     */
    public PanMouseInputListener(JXMapViewer viewer)
    {
        this.viewer = viewer;
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        if (!isEnabled())
        {
            return;
        }
        if (!SwingUtilities.isLeftMouseButton(evt))
            return;
        if (!viewer.isPanningEnabled())
            return;
        
        prev = evt.getPoint();
        priorCursor = viewer.getCursor();
        viewer.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent evt)
    {
        if (!isEnabled())
        {
            return;
        }
        if (!SwingUtilities.isLeftMouseButton(evt))
            return;
        if (!viewer.isPanningEnabled())
            return;
        
        Point current = evt.getPoint();
        double x = viewer.getCenter().getX();
        double y = viewer.getCenter().getY();

        if(prev != null){
                x += prev.x - current.x;
                y += prev.y - current.y;
        }

        int maxHeight = (int) (viewer.getTileFactory().getMapSize(viewer.getZoom()).getHeight() * viewer
                .getTileFactory().getTileSize(viewer.getZoom()));
        if (y > maxHeight)
        {
            y = maxHeight;
        }

        prev = current;
        viewer.setCenter(new Point2D.Double(x, y));
        viewer.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent evt)
    {
        if (!isEnabled())
        {
            return;
        }
        if (!SwingUtilities.isLeftMouseButton(evt))
            return;

        prev = null;
        viewer.setCursor(priorCursor);
    }

    /**
     * Check if this input listener is enabled.
     * @return true if it is enabled, otherwise false.
     */
    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * Set whether this input listener is enabled or not.
     *
     * If it is not enabled, user input will have no effect.
     * @param enabled true to enable, false to disable.
     */
    @Override
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
}


package org.jxmapviewer.input;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

import org.jxmapviewer.JXMapViewer;

/**
 * Centers the map on the mouse cursor.
 *
 * <p>To perform the centering operation, the left button is double-clicked or
 * middle mouse button is pressed
 *
 * <p>This input listener can be disabled. It is enabled by default. When it is
 * disabled, mouse wheel events will have no effect.
 *
 * @author Martin Steiger
 * @author joshy
 */
public class CenterMapListener extends MouseAdapter implements DisableableInputAdapter
{
    private JXMapViewer viewer;
    private boolean enabled;

    /**
     * @param viewer the jxmapviewer
     */
    public CenterMapListener(JXMapViewer viewer)
    {
        this.viewer = viewer;
    }

    @Override
    public void mousePressed(MouseEvent evt)
    {
        if (!enabled)
        {
            return;
        }
        boolean left = SwingUtilities.isLeftMouseButton(evt);
        boolean middle = SwingUtilities.isMiddleMouseButton(evt);
        boolean doubleClick = (evt.getClickCount() == 2);

        if (middle || (left && doubleClick))
        {
            recenterMap(evt);
        }
    }
    
    private void recenterMap(MouseEvent evt)
    {
        Rectangle bounds = viewer.getViewportBounds();
        double x = bounds.getX() + evt.getX();
        double y = bounds.getY() + evt.getY();
        viewer.setCenter(new Point2D.Double(x, y));
                viewer.setZoom(viewer.getZoom() - 1);
        viewer.repaint();
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


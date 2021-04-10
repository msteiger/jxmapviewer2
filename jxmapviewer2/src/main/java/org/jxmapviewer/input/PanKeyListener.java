
package org.jxmapviewer.input;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapViewer;

/**
 * Used to pan the map using the arrow keys.
 *
 * <p>This input listener can be disabled. When it is disabled, key events will have no effect.
 *
 * @author joshy
 */
public class PanKeyListener extends KeyAdapter implements DisableableInputAdapter
{
    private static final int OFFSET = 10;

    private JXMapViewer viewer;
    private boolean enabled;

    /**
     * @param viewer the jxmapviewer
     */
    public PanKeyListener(JXMapViewer viewer)
    {
        this.viewer = viewer;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (!enabled)
        {
            return;
        }
        int delta_x = 0;
        int delta_y = 0;

        switch (e.getKeyCode())
        {
        case KeyEvent.VK_LEFT:
            delta_x = -OFFSET;
            break;
        case KeyEvent.VK_RIGHT:
            delta_x = OFFSET;
            break;
        case KeyEvent.VK_UP:
            delta_y = -OFFSET;
            break;
        case KeyEvent.VK_DOWN:
            delta_y = OFFSET;
            break;
        }

        if (delta_x != 0 || delta_y != 0)
        {
            Rectangle bounds = viewer.getViewportBounds();
            double x = bounds.getCenterX() + delta_x;
            double y = bounds.getCenterY() + delta_y;
            viewer.setCenter(new Point2D.Double(x, y));
            viewer.repaint();
        }
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

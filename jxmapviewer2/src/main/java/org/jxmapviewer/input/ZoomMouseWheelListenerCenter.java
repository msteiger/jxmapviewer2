
package org.jxmapviewer.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.jxmapviewer.JXMapViewer;

/**
 * Zooms using the mouse wheel on the view center.
 *
 * @author joshy
 */
public class ZoomMouseWheelListenerCenter extends DisableableMouseWheelListener implements MouseWheelListener, DisableableInputAdapter
{
    /**
     * @param viewer the jxmapviewer
     */
    public ZoomMouseWheelListenerCenter(JXMapViewer viewer)
    {
        super(viewer);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        if (!enabled)
        {
            return;
        }
        viewer.setZoom(viewer.getZoom() + e.getWheelRotation());
    }
}


package org.jxmapviewer.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.jxmapviewer.JXMapViewer;

/**
 * zooms using the mouse wheel on the view center
 * @author joshy
 */
public class ZoomMouseWheelListenerCenter implements MouseWheelListener
{
    private JXMapViewer viewer;
    
    /**
     * @param viewer the jxmapviewer
     */
    public ZoomMouseWheelListenerCenter(JXMapViewer viewer)
    {
        this.viewer = viewer;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        viewer.setZoom(viewer.getZoom() + e.getWheelRotation());
    }
}

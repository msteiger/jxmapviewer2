
package org.jxmapviewer.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.jxmapviewer.AbstractJXMapViewer;

/**
 * zooms using the mouse wheel on the view center
 * @author joshy
 */
public class ZoomMouseWheelListenerCenter implements MouseWheelListener
{
    private AbstractJXMapViewer viewer;
    
    /**
     * @param viewer the jxmapviewer
     */
    public ZoomMouseWheelListenerCenter(AbstractJXMapViewer viewer)
    {
        this.viewer = viewer;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        viewer.setZoom(viewer.getZoom() + e.getWheelRotation());
    }
}

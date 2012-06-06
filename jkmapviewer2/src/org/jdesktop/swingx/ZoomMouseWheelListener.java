
package org.jdesktop.swingx;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * zooms using the mouse wheel
 * @author joshy
 */
public class ZoomMouseWheelListener implements MouseWheelListener
{
	private JXMapViewer viewer;
	
	
	/**
	 * @param viewer the jxmapviewer
	 */
	public ZoomMouseWheelListener(JXMapViewer viewer)
	{
		this.viewer = viewer;
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if (viewer.isZoomEnabled())
		{
			viewer.setZoom(viewer.getZoom() + e.getWheelRotation());
		}
	}
}

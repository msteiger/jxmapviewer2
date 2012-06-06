
package org.jdesktop.swingx;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

/**
 * @author joshy
 */

// used to pan using press and drag mouse gestures
public class PanMouseInputListener implements MouseInputListener
{
	private Point prev;
	private JXMapViewer viewer;
	
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
		// if the middle mouse button is clicked, recenter the view
		if (viewer.isRecenterOnClickEnabled()
				&& (SwingUtilities.isMiddleMouseButton(evt) || (SwingUtilities.isLeftMouseButton(evt) && evt
						.getClickCount() == 2)))
		{
			recenterMap(evt);
		}
		else
		{
			// otherwise, just remember this point (for panning)
			prev = evt.getPoint();
		}
	}

	private void recenterMap(MouseEvent evt)
	{
		Rectangle bounds = viewer.getViewportBounds();
		double x = bounds.getX() + evt.getX();
		double y = bounds.getY() + evt.getY();
		viewer.setCenter(new Point2D.Double(x, y));
		viewer.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent evt)
	{
		if (viewer.isPanEnabled())
		{
			Point current = evt.getPoint();
			double x = viewer.getCenter().getX() - (current.x - prev.x);
			double y = viewer.getCenter().getY() - (current.y - prev.y);

			if (!viewer.isNegativeYAllowed())
			{
				if (y < 0)
				{
					y = 0;
				}
			}

			int maxHeight = (int) (viewer.getTileFactory().getMapSize(viewer.getZoom()).getHeight() * viewer.getTileFactory()
					.getTileSize(viewer.getZoom()));
			if (y > maxHeight)
			{
				y = maxHeight;
			}

			prev = current;
			viewer.setCenter(new Point2D.Double(x, y));
			viewer.repaint();
			viewer.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt)
	{
		prev = null;
		viewer.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				viewer.requestFocusInWindow();
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// ignore
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// ignore
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// ignore
	}
}

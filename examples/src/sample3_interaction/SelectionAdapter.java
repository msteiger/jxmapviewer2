
package sample3_interaction;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import org.jdesktop.swingx.JXMapViewer;

/**
 * Creates a selection rectangle based on mouse input
 * Also triggers repaint events in the viewer
 * @author Martin Steiger
 */
public class SelectionAdapter extends MouseAdapter 
{
	private boolean dragging;
	private JXMapViewer viewer;

	private Point2D startPos = new Point2D.Double();
	private Point2D endPos = new Point2D.Double();

	/**
	 * @param viewer the jxmapviewer
	 */
	public SelectionAdapter(JXMapViewer viewer)
	{
		this.viewer = viewer;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() != MouseEvent.BUTTON3)
			return;
		
		startPos.setLocation(e.getX(), e.getY());
		endPos.setLocation(e.getX(), e.getY());
		
		dragging = true;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if (!dragging)
			return;
		
		endPos.setLocation(e.getX(), e.getY());
		
		viewer.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (!dragging)
			return;
		
		if (e.getButton() != MouseEvent.BUTTON3)
			return;
		
		viewer.repaint();
		
		dragging = false;
	}

	/**
	 * @return the selection rectangle
	 */
	public Rectangle getRectangle()
	{
		if (dragging)
		{
			int x1 = (int) Math.min(startPos.getX(), endPos.getX());
			int y1 = (int) Math.min(startPos.getY(), endPos.getY());
			int x2 = (int) Math.max(startPos.getX(), endPos.getX());
			int y2 = (int) Math.max(startPos.getY(), endPos.getY());
			
			return new Rectangle(x1, y1, x2-x1, y2-y1);
		}
		
		return null;
	}

}

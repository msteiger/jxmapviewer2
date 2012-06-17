
package sample3_interaction;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.jdesktop.swingx.painter.Painter;

/**
 * Paints a selection rectangle
 * @author Martin Steiger
 */
public class SelectionPainter implements Painter<Object>
{
	private Color fillColor = new Color(128, 192, 255, 128);
	private Color frameColor = new Color(0, 0, 255, 128);

	private SelectionAdapter adapter;
	
	/**
	 * @param adapter the selection adapter
	 */
	public SelectionPainter(SelectionAdapter adapter)
	{
		this.adapter = adapter;
	}

	@Override
	public void paint(Graphics2D g, Object t, int width, int height)
	{
		Rectangle rc = adapter.getRectangle();
		
		if (rc != null)
		{
			g.setColor(frameColor);
			g.draw(rc);
			g.setColor(fillColor);
			g.fill(rc);
		}
	}
}

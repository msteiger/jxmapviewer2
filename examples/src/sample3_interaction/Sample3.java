package sample3_interaction;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.LocalResponseCache;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * A simple sample application that shows
 * a OSM map of Europe
 * @author Martin Steiger
 */
public class Sample3
{
	/**
	 * @param args the program args (ignored)
	 */
	public static void main(String[] args)
	{
		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(8);

		// Setup local file cache
		File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		LocalResponseCache.installResponseCache(info.getBaseURL(), cacheDir, false);

		// Setup JXMapViewer
		final JXMapViewer mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(tileFactory);

		GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

		// Set the focus
		mapViewer.setZoom(7);
		mapViewer.setAddressLocation(frankfurt);
	
		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);

		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
		
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));
		
		// Add a selection painter
		SelectionAdapter sa = new SelectionAdapter(mapViewer); 
		SelectionPainter sp = new SelectionPainter(sa); 
		mapViewer.addMouseListener(sa); 
		mapViewer.addMouseMotionListener(sa); 
		mapViewer.setOverlayPainter(sp);
		
		// Display the viewer in a JFrame
		final JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(new JLabel("Use left mouse button to pan, mouse wheel to zoom and right mouse to select"), BorderLayout.NORTH);
		frame.add(mapViewer);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		mapViewer.addPropertyChangeListener("zoom", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				updateWindowTitle(frame, mapViewer);
			}
		});
		
		mapViewer.addPropertyChangeListener("center", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				updateWindowTitle(frame, mapViewer);
			}
		});
		
		updateWindowTitle(frame, mapViewer);
	}

	protected static void updateWindowTitle(JFrame frame, JXMapViewer mapViewer)
	{
		double lat = mapViewer.getCenterPosition().getLatitude();
		double lon = mapViewer.getCenterPosition().getLongitude();
		int zoom = mapViewer.getZoom();
		
		frame.setTitle(String.format("JXMapviewer2 Example 3 (%.2f / %.2f) - Zoom: %d", lat, lon, zoom)); 
	}
	
	
}

package viewer;

import gpx.GpxReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.OSMTileFactoryInfo;
import org.jdesktop.swingx.input.PanMouseInputListener;
import org.jdesktop.swingx.input.ZoomMouseWheelListener;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.LocalResponseCache;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

import sample2.RoutePainter;
import track.Track;

/**
 * A simple sample application that shows
 * a OSM map of Europe
 * @author Martin Steiger
 */
public class Viewer
{
	/**
	 * @param args the program args (ignored)
	 */
	public static void main(String[] args)
	{
		JXMapViewer mapViewer = new JXMapViewer();
		
		// Setup local file cache
		File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
		LocalResponseCache.installResponseCache(cacheDir, false);

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(8);
		mapViewer.setTileFactory(tileFactory);

		// Set the focus
		mapViewer.setZoom(10);
//		mapViewer.setAddressLocation(frankfurt);
	
		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListener(mapViewer));

		// Read in route
		Track track;
		try
		{
			track = GpxReader.read(new FileInputStream("E:\\2012-05-27-11-31-50.gpx"));
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return;
		}
		
		// Create route from geo-positions
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		List<GeoPosition> pts = track.getRoute();
		RoutePainter routePainter = new RoutePainter(pts);
		painters.add(routePainter);
		
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
		mapViewer.setOverlayPainter(painter);

		// Display the viewer in a JFrame
		JFrame frame = new JFrame("JXMapviewer2 Example");
		frame.getContentPane().add(mapViewer);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

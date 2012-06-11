package sample4;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.event.MouseInputListener;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.OSMTileFactoryInfo;
import org.jdesktop.swingx.VirtualEarthTileFactoryInfo;
import org.jdesktop.swingx.input.CenterMapListener;
import org.jdesktop.swingx.input.PanKeyListener;
import org.jdesktop.swingx.input.PanMouseInputListener;
import org.jdesktop.swingx.input.ZoomMouseWheelListener;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.DefaultWaypoint;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.LocalResponseCache;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

import sample2.RoutePainter;

/**
 * A simple sample application that shows
 * a OSM map of Europe
 * @author Martin Steiger
 */
public class Sample4
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
		TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
		DefaultTileFactory tileFactory = new DefaultTileFactory(info);
		tileFactory.setThreadPoolSize(8);
		mapViewer.setTileFactory(tileFactory);

		GeoPosition frankfurt = new GeoPosition(50,  7, 0, 8, 41, 0);
		GeoPosition wiesbaden = new GeoPosition(50,  5, 0, 8, 14, 0);
		GeoPosition mainz     = new GeoPosition(50,  0, 0, 8, 16, 0);
		GeoPosition darmstadt = new GeoPosition(49, 52, 0, 8, 39, 0);
		GeoPosition offenbach = new GeoPosition(50,  6, 0, 8, 46, 0);

		// Set the focus
		mapViewer.setZoom(7);
		mapViewer.setAddressLocation(frankfurt);
	
		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);
		mapViewer.addMouseListener(new CenterMapListener(mapViewer));
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListener(mapViewer));
		mapViewer.addKeyListener(new PanKeyListener(mapViewer));

		List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden, mainz, darmstadt, offenbach);
		RoutePainter routePainter = new RoutePainter(track);

		// Create waypoints from the geo-positions
		Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
				new DefaultWaypoint(frankfurt),
				new DefaultWaypoint(wiesbaden),
				new DefaultWaypoint(mainz),
				new DefaultWaypoint(darmstadt),
				new DefaultWaypoint(offenbach)));

		// Create a waypoint painter that takes all the waypoints
		WaypointPainter waypointPainter = new WaypointPainter();
		waypointPainter.setWaypoints(waypoints);
		
		// Create a compound painter that uses both the route-painter and the waypoint-painter
		List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
		painters.add(routePainter);
		painters.add(waypointPainter);
		
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

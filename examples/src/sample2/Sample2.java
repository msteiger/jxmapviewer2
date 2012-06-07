package sample2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.OSMTileFactoryInfo;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.painter.CompoundPainter;

/**
 * A simple sample application that shows
 * a OSM map of Europe
 * @author Martin Steiger
 */
public class Sample2
{
	/**
	 * @param args the program args (ignored)
	 */
	public static <T> void main(String[] args)
	{
		JXMapViewer mapViewer = new JXMapViewer();

		// Create a TileFactoryInfo for OpenStreetMap
		TileFactoryInfo info = new OSMTileFactoryInfo();
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
		
		List<GeoPosition> track = Arrays.asList(frankfurt, wiesbaden, mainz, darmstadt, offenbach);
		RoutePainter routePainter = new RoutePainter(track);
		
		Set<Waypoint> waypoints = new HashSet<Waypoint>(Arrays.asList(
				new Waypoint(frankfurt),
				new Waypoint(wiesbaden),
				new Waypoint(mainz),
				new Waypoint(darmstadt),
				new Waypoint(offenbach)));

		WaypointPainter waypointPainter = new WaypointPainter();
		waypointPainter.setWaypoints(waypoints);
		
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>();
		painter.setPainters(Arrays.asList(routePainter, waypointPainter));
		mapViewer.setOverlayPainter(painter);
	
		// Display the viewer in a JFrame
		JFrame frame = new JFrame("JXMapviewer2 Example");
		frame.getContentPane().add(mapViewer);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

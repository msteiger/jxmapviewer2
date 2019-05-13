package sample9_offline;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * A simple sample application that shows
 * a OSM map of Europe based on an offline ZIP file
 * @author Martin Steiger
 */
public class Sample9
{
    /**
     * @param args the program args (ignored)
     */
    public static void main(String[] args) throws IOException
    {
        // Create a zip-file based TileFactoryInfo for OpenStreetMap
        // See https://docs.oracle.com/javase/8/docs/api/java/net/JarURLConnection.html for URL syntax details
        // You can create such a ZIP file by zipping the content of ${HOME}/.jxmapviewer2/tile.openstreetmap.org
        // The ZIP file should contain a list of folders (0, 1, 2, 3 ...) that represent the OSM path structure
        TileFactoryInfo info = new OSMTileFactoryInfo("ZIP archive", "jar:file:/E:/Github/jxmapviewer2/openstreetmap.zip!");
        TileFactory tileFactory = new DefaultTileFactory(info);

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
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

        // Display the viewer in a JFrame
        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new JLabel("Use left mouse button to pan and mouse wheel to zoom"), BorderLayout.NORTH);
        frame.add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("JXMapviewer2 Example 9 - Offline maps");
        frame.setVisible(true);
    }
}

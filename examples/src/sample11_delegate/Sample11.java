package sample11_delegate;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * A simple sample application that shows how mouse interactions can be enabled/disabled
 * Right-click on the map toggles the zoom/pan functionality
 */
public class Sample11
{
    /**
     * @param args the program args (ignored)
     */
    public static void main(String[] args)
    {
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);

        // Setup local file cache
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        // Setup JXMapViewer
        final JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

        // Set the focus
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(frankfurt);

        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        ZoomMouseWheelListenerCursor mwl = new ZoomMouseWheelListenerCursor(mapViewer);

        DelegatingMouseAdapter delegator = new DelegatingMouseAdapter();
        delegator.setMouseListener(mia);
        delegator.setMouseMotionListener(mia);
        delegator.setMouseWheelListener(mwl);

        final DelegatingMouseAdapterToggle toggle = new DelegatingMouseAdapterToggle(delegator);

        mapViewer.addMouseListener(delegator);
        mapViewer.addMouseMotionListener(delegator);
        mapViewer.addMouseWheelListener(delegator);
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                boolean right = SwingUtilities.isRightMouseButton(evt);
                if (right) {
                    toggle.toggle();
                }
            }
        });


        // Display the viewer in a JFrame
        final JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        frame.add(mapViewer);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

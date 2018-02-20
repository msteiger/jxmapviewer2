package sample8_mapkit;

import javax.swing.JFrame;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * A simple sample application that shows
 * a OSM map of Europe based on a JXMapKit
 * @author Martin Steiger
 */
public class Sample8
{
    /**
     * @param args the program args (ignored)
     */
    public static void main(String[] args)
    {
        JXMapKit mapKit = new JXMapKit();

        // Set the focus
        GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

        mapKit.setZoom(7);
        mapKit.setAddressLocation(frankfurt);

        // Display the viewer in a JFrame
        JFrame frame = new JFrame("JXMapviewer2 Example 8");
        frame.getContentPane().add(mapKit);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

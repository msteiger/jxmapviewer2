
package sample5_tilesets;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.OSMTileFactoryInfo;
import org.jdesktop.swingx.VirtualEarthTileFactoryInfo;
import org.jdesktop.swingx.input.PanMouseInputListener;
import org.jdesktop.swingx.input.ZoomMouseWheelListenerCursor;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

/**
 * This example demonstrate the use of different {@link TileFactory} elements.
 * @author Martin Steiger
 */
public class Sample5
{
	/**
	 * @param args the program args (ignored)
	 */
	public static void main(String[] args)
	{
		final List<TileFactory> factories = new ArrayList<TileFactory>();

		TileFactoryInfo osmInfo = new OSMTileFactoryInfo();
		TileFactoryInfo veInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);

//		factories.add(new EmptyTileFactory());
		factories.add(new DefaultTileFactory(osmInfo));
		factories.add(new DefaultTileFactory(veInfo));
		
		// Setup JXMapViewer
		final JXMapViewer mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(factories.get(0));

		GeoPosition frankfurt = new GeoPosition(50.11, 8.68);

		// Set the focus
		mapViewer.setZoom(7);
		mapViewer.setAddressLocation(frankfurt);

		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);

		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));

		JPanel panel = new JPanel();
		JLabel label = new JLabel("Select a TileFactory ");
		
		String[] tfLabels = new String[factories.size()];
		for (int i = 0; i < factories.size(); i++)
		{
			tfLabels[i] = factories.get(i).getInfo().getName();
		}
		
		final JComboBox combo = new JComboBox(tfLabels);
		combo.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				TileFactory factory = factories.get(combo.getSelectedIndex());
				mapViewer.setTileFactory(factory);
			}
		});
		
		panel.setLayout(new GridLayout());
		panel.add(label);
		panel.add(combo);
		
		final JLabel labelThreadCount = new JLabel("Threads: ");
		
		// Display the viewer in a JFrame
		JFrame frame = new JFrame("JXMapviewer2 Example 5");
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.NORTH);
		frame.add(mapViewer);
		frame.add(labelThreadCount, BorderLayout.SOUTH);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		Timer t = new Timer(500, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Set<Thread> threads = Thread.getAllStackTraces().keySet();
				labelThreadCount.setText("Threads: " + threads.size());
			}
		}); 
		
		t.start();
	}
	
}

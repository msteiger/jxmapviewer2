
package track;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * Represents a track
 * @author Martin Steiger
 */
public class Track
{
	private List<TrackPoint> points = new ArrayList<TrackPoint>();
			
	private List<GeoPosition> route = new AbstractList<GeoPosition>()
	{
		@Override
		public GeoPosition get(int index)
		{
			return points.get(index).getPos();
		}

		@Override
		public int size()
		{
			return points.size();
		}

	};

	/**
	 * @return an unmodifiable list of geo-positions
	 */
	public List<GeoPosition> getRoute()
	{
		return route;		// read-only anyway
	}

	/**
	 * @return an unmodifiable list of track points
	 */
	public List<TrackPoint> getPoints()
	{
		return Collections.unmodifiableList(points);
	}

	/**
	 * @param point the track point
	 */
	public void addPoint(TrackPoint point)
	{
		points.add(point);
	}
	
}

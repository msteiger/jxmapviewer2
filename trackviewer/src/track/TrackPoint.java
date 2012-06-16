
package track;

import java.util.GregorianCalendar;

import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * A single point in a track
 * @author Martin Steiger
 */
public class TrackPoint
{
	private final GeoPosition pos;
	private final double ele;
	private final GregorianCalendar time;

	/**
	 * @param pos the position
	 * @param ele the elevation
	 * @param time the time
	 */
	public TrackPoint(GeoPosition pos, double ele, GregorianCalendar time)
	{
		this.time = time;
		this.ele = ele;
		this.pos = pos;
	}

	/**
	 * @return the pos
	 */
	public GeoPosition getPos()
	{
		return pos;
	}

	/**
	 * @return the elevation
	 */
	public double getElevation()
	{
		return ele;
	}

	/**
	 * @return the time
	 */
	public GregorianCalendar getTime()
	{
		return time;
	}

}


package gpx;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import track.Track;
import track.TrackPoint;

import com.topografix.gpx._1._1.GpxType;
import com.topografix.gpx._1._1.ObjectFactory;
import com.topografix.gpx._1._1.TrkType;
import com.topografix.gpx._1._1.TrksegType;
import com.topografix.gpx._1._1.WptType;

/**
 * Reads track data from .gpx files
 * @author Martin Steiger
 */
public class GpxWriter
{
	/**
	 * @param os the output stream
	 * @param track the track
	 * @throws IOException if the data cannot be read
	 */
	public static void write(OutputStream os, Track track) throws IOException
	{
		GpxType gpx = new GpxType();

		TrksegType seg = new TrksegType();

		DatatypeFactory factory;
		try
		{
			factory = DatatypeFactory.newInstance();
		}
		catch (DatatypeConfigurationException e)
		{
			throw new IllegalStateException(e);
		}

		for (TrackPoint pt : track.getPoints())
		{
			WptType wpt = new WptType();
			
			wpt.setLat(BigDecimal.valueOf(pt.getPos().getLatitude()));
			wpt.setLon(BigDecimal.valueOf(pt.getPos().getLongitude()));
			wpt.setEle(BigDecimal.valueOf(pt.getElevation()));

			wpt.setTime(factory.newXMLGregorianCalendar(pt.getTime()));
			
			seg.getTrkpt().add(wpt);
		}
		
		TrkType trk = new TrkType();
		gpx.getTrk().add(trk);
		
		try
		{
			marshallObject(os, gpx);
		}
		catch (JAXBException e)
		{
			throw new IOException("Error writing outputstream", e);
		}

	}
	
	private static <T> void marshallObject(OutputStream os, GpxType value) throws JAXBException
	{
		String packageName = value.getClass().getPackage().getName();	
		JAXBContext context = JAXBContext.newInstance(packageName);
		ObjectFactory of = new ObjectFactory();

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(of.createGpx(value), os);
	}	
}

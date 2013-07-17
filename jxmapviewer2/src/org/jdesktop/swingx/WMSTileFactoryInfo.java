package org.jdesktop.swingx;

import org.jdesktop.swingx.mapviewer.*;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.util.MercatorUtils;

/**
 * @author Georgios Migdos
 */
public class WMSTileFactoryInfo extends TileFactoryInfo
{
	private static final int maxZoom = 17;
	
	private String layers;
	private String styles;
	private String tileBgColor;
	private String tileFormat;
	private String srs;
	

	public WMSTileFactoryInfo(String baseURL, String layers, String styles, String defaultBgColor, String tileFormat, String srs, int tileSize)
	{
		super(1, maxZoom - 2, maxZoom, tileSize, true, true, baseURL, "x", "y", "zoom");
		this.layers = layers;
		this.styles = styles;
		this.tileBgColor = defaultBgColor;
		this.tileFormat = tileFormat;
		this.srs = srs;
	}
	
	public WMSTileFactoryInfo(String baseURL, String layers, String styles, String defaultBgColor){
	  this(baseURL, layers, styles, defaultBgColor, "image/jpeg", "EPSG:4326", 500);
	}
	
	public WMSTileFactoryInfo(String baseURL, String layers, String defaultBgColor){
	  this(baseURL, layers, "", defaultBgColor);
	}
	
	public WMSTileFactoryInfo(String baseURL, String layers){
	  this(baseURL, layers, "0xAFDAF6");
	}

	@Override
	public String getTileUrl(int x, int y, int zoom)
	{
	  
	  int tileSize = getTileSize(zoom);
	  zoom = maxZoom - zoom;
		int z = (int) Math.pow(2, (double) zoom - 1);
    
		int m = x - z;
		int n = z - 1 - y;
		
		int tilesPerDimension = (int) Math.pow(2, zoom);
		
		double radius = (tileSize * tilesPerDimension) / (2 * Math.PI);
		double ulx = MercatorUtils.xToLong(m * tileSize, radius);
		double uly = MercatorUtils.yToLat(n * tileSize, radius);
		double lrx = MercatorUtils.xToLong((m + 1) * tileSize, radius);
		double lry = MercatorUtils.yToLat((n + 1) * tileSize, radius);
		
		if(lrx<ulx){lrx = -lrx;};
		String bbox = ulx + "," + uly + "," + lrx + "," + lry;
		String url = getBaseURL() + "?version=1.1.1&request=GetMap&layers=" + this.layers + "&format=" + this.tileFormat
				+ "&bbox=" + bbox + "&width=" + tileSize + "&height=" + tileSize + "&srs=" + this.srs + "&styles=" + this.styles +	(this.tileBgColor==null?"":"&bgcolor="+this.tileBgColor);
		return url;
	}

}

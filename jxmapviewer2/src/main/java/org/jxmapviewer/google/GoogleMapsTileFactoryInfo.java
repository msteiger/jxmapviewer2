
package org.jxmapviewer.google;

import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * Uses OpenStreetMap
 */
public class GoogleMapsTileFactoryInfo extends TileFactoryInfo
{
    private static final int max = 19;

    /**
     * Currently only 256x256 works - see https://github.com/msteiger/jxmapviewer2/issues/62
     */
    private static final int TILE_SIZE = 512;

    private final String key;

    /**
     * Default constructor
     */
    public GoogleMapsTileFactoryInfo(String key)
    {
        this("Google Maps", "https://maps.googleapis.com/maps/api/staticmap", key);
    }

    public GoogleMapsTileFactoryInfo(String name, String baseURL, String key)
    {
        super(name,
                1, max - 2, max,
                TILE_SIZE, true, true,
                baseURL,
                "x", "y", "z");
        this.key = key;
    }

    @Override
    public String getTileUrl(int x, int y, int orgZoom)
    {
        int zoom = getTotalMapZoom() - orgZoom;
        int f = TILE_SIZE / 256;

        double xtile = x + 0.5;
        double ytile = y + 0.5;

        double n = Math.pow(2.0,zoom);
        double lon_deg = f * (((xtile / n) * 360.0) - 180);
        double lat_rad = Math.atan(Math.sinh(Math.PI * (1 - f * 2 * ytile / n)));
        double lat_deg = (lat_rad * 180.0) / Math.PI;


        String url = baseURL
                + "?center=" + lat_deg + "," + lon_deg
                + "&zoom=" + zoom
                + "&key=" + key
                + "&maptype=roadmap"
                + "&size=" + TILE_SIZE + "x" + TILE_SIZE;

        return url;
    }

}

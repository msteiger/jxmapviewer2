
package org.jxmapviewer.google;

import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * Uses Google Maps API - it has several glitches, so don't use it unless you know what you're doing.
 * Most importantly, gmaps cuts of small text leftovers that reach into the visible tile. As a result
 * you cannot really tile the rendered tiles.
 */
public class GoogleMapsTileFactoryInfo extends TileFactoryInfo
{
    private static final int max = 19;

    /**
     * Currently only 256x256 works - see https://github.com/msteiger/jxmapviewer2/issues/62
     */
    private static final int TILE_SIZE = 256;

    private final String key;

    /**
     * @param key the Google Map API key
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
    public String getTileUrl(int x, int y, int zoom)
    {
        System.out.println("testing for validity: X " + x + " Y = " + y);

        zoom = getTotalMapZoom() - zoom;

        double xtile = x + 0.5;
        double ytile = y + 0.5;

        double n = Math.pow(2.0,zoom);
        double lon_deg = ((xtile / n) * 360.0) - 180.0;
        double lat_rad = Math.atan(Math.sinh(Math.PI * (1 - 2 * ytile / n)));
        double lat_deg = (lat_rad * 180.0) / Math.PI;


        String url = baseURL
                + "?center=" + lat_deg + "," + lon_deg
                + "&zoom=" + zoom
                + "&key=" + key
                + "&maptype=roadmap"
                + "&size=" + TILE_SIZE + "x" + TILE_SIZE;

        return url;
    }

    @Override
    public String getAttribution() {
        return "\u00A9 Google";
    }

    @Override
    public String getLicense() {
        return "https://www.google.com/intl/en_US/help/terms_maps/";
    }


}

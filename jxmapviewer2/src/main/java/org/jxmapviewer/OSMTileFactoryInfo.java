
package org.jxmapviewer;

import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 * Uses OpenStreetMap
 */
public class OSMTileFactoryInfo extends TileFactoryInfo
{
    private static final int MAX_ZOOM = 19;

    /**
     * Default constructor
     */
    public OSMTileFactoryInfo()
    {
        this("OpenStreetMap", "http://tile.openstreetmap.org");
    }

    /**
     * @param name the name of the factory
     * @param baseURL the base URL to load tiles from
     */
    public OSMTileFactoryInfo(String name, String baseURL)
    {
        super(name,
                0, MAX_ZOOM, MAX_ZOOM,
                256, true, true,                     // tile size is 256 and x/y orientation is normal
                baseURL,
                "x", "y", "z");                        // 5/15/10.png
    }

    @Override
    public String getTileUrl(int x, int y, int zoom)
    {
        int invZoom = MAX_ZOOM - zoom;
        String url = this.baseURL + "/" + invZoom + "/" + x + "/" + y + ".png";
        return url;
    }

    @Override
    public String getAttribution() {
        return "\u00A9 OpenStreetMap contributors";
    }

    @Override
    public String getLicense() {
        return "Creative Commons Attribution-ShareAlike 2.0";
    }



}

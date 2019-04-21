package org.jxmapviewer;

import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 *
 * @author Ruediger34
 */
public class ZipTileFactoryInfo extends TileFactoryInfo {

    private static final int max = 19;

    /**
     * Default constructor
     */
    public ZipTileFactoryInfo() {
        this("Mapnik", "./maparchive.zip", 256);
    }

    /**
     * Creats a TileFactoryInfo for loading tiles from an Zip Archive
     *
     * @param name     the folder name that contains tiles inside the zip archive
     * @param baseURL  the FilePath to the archive
     * @param tileSize specifies the tile size in the archive
     *       
     */
    public ZipTileFactoryInfo(String name, String baseURL, int tileSize) {     
        super(name, 1, max - 2, max, 
                tileSize, true, true,   // tile size and x/y orientation is normal
                baseURL,  
                "x", "y", "z", true);     // 5/15/10.png        
    }

    @Override
    public String getTileUrl(int x, int y, int zoom) {
        zoom = max - zoom;
        String url = this.baseURL + "/" + zoom + "/" + x + "/" + y + ".png";
        return url;
    }
}


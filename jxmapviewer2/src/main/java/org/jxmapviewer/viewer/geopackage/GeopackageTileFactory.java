package org.jxmapviewer.viewer.geopackage;

import java.io.File;
import java.awt.image.BufferedImage;

import org.jxmapviewer.viewer.Tile;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;

import mil.nga.geopackage.GeoPackage;
import mil.nga.geopackage.GeoPackageManager;
import mil.nga.geopackage.tiles.GeoPackageTile;
import mil.nga.geopackage.tiles.GeoPackageTileRetriever;
import mil.nga.geopackage.tiles.ImageUtils;
import mil.nga.geopackage.tiles.user.TileDao;

/**
 * TileFactory to load tiles from GeoPackage files.
 * 
 * GeoPackage is a file format based on an SQLite database with tables that 
 * store raster map data as tile pyramids, vector features and metadata about 
 * them.
 * 
 * Only raster tile pyramid tables are supported, with the tiles being 256x256
 * pixels in PNG format.
 * 
 * @author Georgios Migdos
 */
public class GeopackageTileFactory extends TileFactory {

    private GeoPackage geoPackage;
    private TileDao tileDao;
    private int maxZoom;

    /** 
     * Creates a new instance of GeopackageTileFactory using the specified info. 
     * @param info the tile factory info
     */
    public GeopackageTileFactory(File geopackageFile, String table, int minZoom, int maxZoom)
    {
        super(new TileFactoryInfo(minZoom-1, maxZoom-1, maxZoom-minZoom, 256, true, true, "", "x", "y", "z"));
        this.geoPackage = GeoPackageManager.open(geopackageFile);
        this.tileDao = geoPackage.getTileDao(table);
        this.maxZoom = maxZoom-1;
    }

    /**
     * Gets an instance of an empty tile for the given tile position and zoom on the world map.
     * @param x The tile's x position on the world map.
     * @param y The tile's y position on the world map.
     * @param zoom The current zoom level.
     */
    @Override
    public Tile getTile(int x, int y, int zoom)
    {
        // Retrieve Tiles by XYZ
        GeoPackageTileRetriever retriever = new GeoPackageTileRetriever(tileDao, ImageUtils.IMAGE_FORMAT_PNG);
        GeoPackageTile geoPackageTile = retriever.getTile(x, y, maxZoom - zoom);
        if (geoPackageTile != null) {
            BufferedImage tileImage = geoPackageTile.getImage();
            return new Tile(x, y, zoom){

                public synchronized boolean isLoaded() {
                    return  true;
                }

                public BufferedImage getImage() {
                    return tileImage;
                };
            };
        } else {
            return new Tile(x, y, zoom){
                @Override
                public synchronized boolean isLoaded() {
                    return  false;
                }

                @Override
                public synchronized boolean loadingFailed() {
                    return true;
                }
            };
        }
    }

    @Override
    public void dispose()
    {
      geoPackage.close();
    }

    /**
     * Override this method to load the tile using, for example, an <code>ExecutorService</code>.
     * @param tile The tile to load.
     */
    @Override
    protected void startLoading(Tile tile)
    {
        // noop
    }
}

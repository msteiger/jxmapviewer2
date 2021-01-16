package org.jxmapviewer.viewer;

import java.awt.*;
import java.awt.geom.Point2D;

public  class MapTileDrawer {
    public TileFactory getTileFactory() {
        return tileFactory;
    }

    TileFactory tileFactory;

    public Image getLoadingImage() {
        return loadingImage;
    }

    Image loadingImage;

    public int getZoom() {
        return zoom;
    }

    int zoom;

    public boolean isInfiniteMapRendering() {
        return isInfiniteMapRendering;
    }

    boolean isInfiniteMapRendering;

    boolean isDrawTileBorders;

    public boolean isDrawTileBorders() {
        return isDrawTileBorders;
    }

    public boolean isOpaque() {
        return isOpaque;
    }

    public Color getBackground() {
        return background;
    }

    boolean isOpaque;
    Color background;

    public MapTileDrawer(TileFactory tileFactory,Image loadingImage, int zoom,boolean isInfiniteMapRendering,boolean isDrawTileBorders,boolean isOpaque,Color background){
        this.tileFactory=tileFactory;
        this.loadingImage=loadingImage;
        this.zoom=zoom;
        this.isInfiniteMapRendering=isInfiniteMapRendering;
        this.isDrawTileBorders=isDrawTileBorders;
        this.isOpaque=isOpaque;
        this.background=background;

    }


    public void drawMapTiles(final Graphics g, final int zoom, Rectangle viewportBounds)
    {
        int size = getTileFactory().getTileSize(zoom);
        Dimension mapSize = getTileFactory().getMapSize(zoom);

        // calculate the "visible" viewport area in tiles
        int numWide = viewportBounds.width / size + 2;
        int numHigh = viewportBounds.height / size + 2;

        // TilePoint topLeftTile = getTileFactory().getTileCoordinate(
        // new Point2D.Double(viewportBounds.x, viewportBounds.y));
        TileFactoryInfo info = getTileFactory().getInfo();

        // number of tiles in x direction
        int tpx = (int) Math.floor(viewportBounds.getX() / info.getTileSize(0));
        // number of tiles in y direction
        int tpy = (int) Math.floor(viewportBounds.getY() / info.getTileSize(0));
        // TilePoint topLeftTile = new TilePoint(tpx, tpy);

        // p("top tile = " + topLeftTile);
        // fetch the tiles from the factory and store them in the tiles cache
        // attach the tileLoadListener
        for (int x = 0; x <= numWide; x++)
        {
            for (int y = 0; y <= numHigh; y++)
            {
                int itpx = x + tpx;// topLeftTile.getX();
                int itpy = y + tpy;// topLeftTile.getY();
                // TilePoint point = new TilePoint(x + topLeftTile.getX(), y + topLeftTile.getY());
                // only proceed if the specified tile point lies within the area being painted
                drawGraphic(g, zoom, viewportBounds, size, mapSize, info, itpx, itpy);
            }
        }
    }

    private void drawGraphic(Graphics g, int zoom, Rectangle viewportBounds, int size, Dimension mapSize, TileFactoryInfo info, int itpx, int itpy) {
        if (g.getClipBounds().intersects(
                new Rectangle(itpx * size - viewportBounds.x, itpy * size - viewportBounds.y, size, size)))
        {

            int ox = ((itpx * getTileFactory().getTileSize(zoom)) - viewportBounds.x);
            int oy = ((itpy * getTileFactory().getTileSize(zoom)) - viewportBounds.y);

            // if the tile is off the map to the north/south, then just don't paint anything
            if (!isTileOnMap(itpx, itpy, mapSize))
            {
                if (isOpaque())
                {
                    g.setColor(getBackground());
                    g.fillRect(ox, oy, size, size);
                }
            }
            else{
                drawImage(g, zoom, size, info, itpx, itpy, ox, oy);
            }

            if (isDrawTileBorders())
            {
                drawTileBorders(g, size, itpx, itpy, ox, oy);
            }
        }
    }

    private void drawImage(Graphics g, int zoom, int size, TileFactoryInfo info, int itpx, int itpy, int ox, int oy) {
        Tile tile = getTileFactory().getTile(itpx, itpy, zoom);

        if (tile.isLoaded())
        {
            g.drawImage(tile.getImage(), ox, oy, null);
        }
        else
        {
            Tile superTile = null;

            // Use tile at higher zoom level with 200% magnification and if we are not already at max resolution
            if (zoom < info.getMaximumZoomLevel()) {
                superTile = getTileFactory().getTile(itpx / 2, itpy / 2, zoom + 1);
            }

            if ( superTile != null && superTile.isLoaded())
            {
                int offX = (itpx % 2) * size / 2;
                int offY = (itpy % 2) * size / 2;
                g.drawImage(superTile.getImage(), ox, oy, ox + size, oy + size, offX, offY, offX + size / 2, offY + size / 2, null);
            }
            else
            {
                int imageX = (getTileFactory().getTileSize(zoom) - getLoadingImage().getWidth(null)) / 2;
                int imageY = (getTileFactory().getTileSize(zoom) - getLoadingImage().getHeight(null)) / 2;
                g.setColor(Color.GRAY);
                g.fillRect(ox, oy, size, size);
                g.drawImage(getLoadingImage(), ox + imageX, oy + imageY, null);
            }
        }
    }

    private void drawTileBorders(Graphics g, int size, int itpx, int itpy, int ox, int oy) {
        g.setColor(Color.black);
        g.drawRect(ox, oy, size, size);
        g.drawRect(ox + size / 2 - 5, oy + size / 2 - 5, 10, 10);
        g.setColor(Color.white);
        g.drawRect(ox + 1, oy + 1, size, size);

        String text = itpx + ", " + itpy + ", " + getZoom();
        g.setColor(Color.BLACK);
        g.drawString(text, ox + 10, oy + 30);
        g.drawString(text, ox + 10 + 2, oy + 30 + 2);
        g.setColor(Color.WHITE);
        g.drawString(text, ox + 10 + 1, oy + 30 + 1);
    }

    private boolean isTileOnMap(int x, int y, Dimension mapSize)
    {
        return (y >= 0 && y < mapSize.getHeight()) &&
                (isInfiniteMapRendering() || x >= 0 && x < mapSize.getWidth());
    }


}

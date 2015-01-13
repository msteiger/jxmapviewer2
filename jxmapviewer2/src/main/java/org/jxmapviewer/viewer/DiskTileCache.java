package org.jxmapviewer.viewer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import org.jxmapviewer.util.GraphicsUtilities;
import org.jxmapviewer.viewer.TileCache;
import org.jxmapviewer.viewer.TileCache;

/**
 * TileCache that uses a disk cache. By default the path is set to null
 * and the disk cache is disabled
 */
public class DiskTileCache extends TileCache {

    private File path = null;
    private long cacheTime = 2592000000L; // default to 30 day

    public String getPath() {
        return path.getAbsolutePath();
    }

    /**
     * if path is set to null, this disables the disk cache
     * @param path
     */
    public void setPath(String path) {
        if (path == null) {
            this.path = null;
        } else {
            this.path = new File(path);
        }
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long time) {
        if (time >= 0) {
            cacheTime = time;
        }
    }

    @Override
    public BufferedImage get(URI uri) throws IOException {
        return get(uri, false);
    }

    public BufferedImage get(URI uri, boolean offline) throws IOException {
        BufferedImage img = super.get(uri);
        if (img == null) {
            if (path != null) {
                File file = new File(path + uri.getPath());
                if (file.exists()) {
                    if (!offline) {
                        // check for old tiles
                        if ((file.lastModified() + cacheTime) < System.currentTimeMillis()) {
                            return null;
                        }
                    }
                    // Get this tile from the disk cache
                    img = GraphicsUtilities.loadCompatibleImage(new FileInputStream(file));
                }
            }
        }

        return img;
    }

    @Override
    public void put(URI uri, byte[] bimg, BufferedImage img) {
        if (path != null) {
            // Put the tile to the disk cache
            File file = new File(path + uri.getPath());
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out;
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                out = new FileOutputStream(file);
                out.write(bimg);
                out.close();
            } catch (IOException ex) {
//            
            }
        }
        super.put(uri, bimg, img);
    }
}

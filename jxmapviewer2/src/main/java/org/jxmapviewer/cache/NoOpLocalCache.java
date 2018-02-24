package org.jxmapviewer.cache;

import java.io.InputStream;
import java.net.URL;

/**
 * A dummy implementation that does nothing
 */
public class NoOpLocalCache implements LocalCache {

    @Override
    public InputStream get(URL url) {
        return null;
    }

    @Override
    public void put(URL url, InputStream data) {
        // do nothing
    }

}

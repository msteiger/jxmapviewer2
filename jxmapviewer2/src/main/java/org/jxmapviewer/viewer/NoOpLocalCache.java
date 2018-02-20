package org.jxmapviewer.viewer;

import java.io.InputStream;
import java.net.URL;

/**
 * TODO: describe
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

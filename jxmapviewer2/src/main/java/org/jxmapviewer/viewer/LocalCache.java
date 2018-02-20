package org.jxmapviewer.viewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * TODO: describe
 */
public interface LocalCache {

    InputStream get(URL url) throws IOException;

    void put(URL url, InputStream data) throws IOException;

}

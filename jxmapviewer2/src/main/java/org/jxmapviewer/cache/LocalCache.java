package org.jxmapviewer.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A cache that stores GET requests to a HTTP url locally
 */
public interface LocalCache {

    /**
     * @param url the URL request to cache
     * @return a (local) stream to use instead or <code>null</code>.
     * @throws IOException if the data cannot be read
     */
    InputStream get(URL url) throws IOException;

    /**
     * @param url the URL request that should be cached
     * @param data the input stream that provides the data
     * @throws IOException if the data cannot be written
     */
    void put(URL url, InputStream data) throws IOException;

}

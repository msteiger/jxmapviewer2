
package org.jxmapviewer.viewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.cache.LocalCache;

/**
 * @author joshy
 */
@Deprecated
public class LocalResponseCache extends ResponseCache
{
    private static final Log log = LogFactory.getLog(LocalResponseCache.class);

    private final String baseURL;

    private FileBasedLocalCache cache;

    /**
     * Private constructor to prevent instantiation.
     * @param baseURL the URI that should be cached or <code>null</code> (for all URLs)
     * @param cacheDir the cache directory
     * @param checkForUpdates true if the URL is queried for newer versions of a file first
     */
    private LocalResponseCache(String baseURL, File cacheDir, boolean checkForUpdates)
    {
        this.baseURL = baseURL;
        this.cache = new FileBasedLocalCache(cacheDir, checkForUpdates);
    }

    /**
     * Sets this cache as default response cache
     * @param baseURL the URL, the caching should be restricted to or <code>null</code> for none
     * @param cacheDir the cache directory
     * @param checkForUpdates true if the URL is queried for newer versions of a file first
     * @deprecated Use {@link TileFactory#setLocalCache(LocalCache)} instead
     */
    @Deprecated
    public static void installResponseCache(String baseURL, File cacheDir, boolean checkForUpdates)
    {
        ResponseCache.setDefault(new LocalResponseCache(baseURL, cacheDir, checkForUpdates));
    }

    @Override
    public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException
    {
        if (!wantsToCache(uri)) {
            return null;
        }

        InputStream localData = cache.get(uri.toURL());
        if (localData == null) {
            return null;
        }

        return new LocalCacheResponse(localData, rqstHeaders);
    }

    private boolean wantsToCache(URI uri) {
        if (baseURL != null) {
            String remote = uri.toString();

            if (!remote.startsWith(baseURL)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CacheRequest put(URI uri, URLConnection conn) throws IOException
    {
        // only cache http(s) GET requests
        if (!(conn instanceof HttpURLConnection) || !(((HttpURLConnection) conn).getRequestMethod().equals("GET")))
        {
            return null;
        }

        if (!wantsToCache(uri)) {
            return null;
        }

        File localFile = cache.getLocalFile(uri.toURL());
        return new LocalCacheRequest(localFile);
    }

    private class LocalCacheResponse extends CacheResponse
    {
        private InputStream is;
        private final Map<String, List<String>> headers;

        private LocalCacheResponse(InputStream localData, Map<String, List<String>> rqstHeaders)
        {
            this.is = localData;
            this.headers = rqstHeaders;
        }

        @Override
        public Map<String, List<String>> getHeaders() throws IOException
        {
            return headers;
        }

        @Override
        public InputStream getBody() throws IOException
        {
            return is;
        }
    }

    private class LocalCacheRequest extends CacheRequest
    {
        private final File localFile;
        private FileOutputStream fos;

        private LocalCacheRequest(File localFile)
        {
            this.localFile = localFile;
            try
            {
                localFile.getParentFile().mkdirs();
                this.fos = new FileOutputStream(localFile);
            }
            catch (FileNotFoundException ex)
            {
                // should not happen if cache dir is valid
                log.error("An exception occurred", ex);
            }
        }

        @Override
        public OutputStream getBody() throws IOException
        {
            return fos;
        }

        @Override
        public void abort()
        {
            // abandon the cache attempt by closing the stream and deleting
            // the local file
            try
            {
                fos.close();
                localFile.delete();
            }
            catch (IOException e)
            {
                // ignore
            }
        }
    }
}

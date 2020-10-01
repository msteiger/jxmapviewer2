package org.jxmapviewer.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A file/folder-based cache
 */
public class FileBasedLocalCache implements LocalCache {

    private static final Log log = LogFactory.getLog(FileBasedLocalCache.class);

    private final File cacheDir;
    private final boolean checkForUpdates;

    /**
     * @param cacheDir the root cache folder
     * @param checkForUpdates true, if the remote URL should be checked for updates first
     */
    public FileBasedLocalCache(File cacheDir, boolean checkForUpdates) {
        this.cacheDir = cacheDir;
        if (!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }
        this.checkForUpdates = checkForUpdates;
    }

    @Override
    public InputStream get(URL url) {
        File localFile = getLocalFile(url);

        if (!localFile.exists())  //early check on file exists
        {
            // the file isn't already in our cache, return null
            return null;
        }

        if (checkForUpdates)
        {
            if (isUpdateAvailable(url, localFile))
            {
                // there is an update available, so don't return cached version
                return null;
            }
        }
        try {
            return new FileInputStream(localFile);
        } catch (FileNotFoundException ex) {
            return null;  //file may have been deleted since we checked above
        }
    }

    @Override
    public void put(URL url, InputStream data) throws IOException {
        File localFile = getLocalFile(url);
        localFile.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(localFile);
        try {
            copy(data, out);
        } finally {
            out.close();
        }
    }

    /**
     * Returns the local File corresponding to the given remote URI.
     * @param remoteUri the remote URI
     * @return the corresponding local file
     */
    public File getLocalFile(URL remoteUri)
    {
        StringBuilder sb = new StringBuilder();

        String host = remoteUri.getHost();
        String query = remoteUri.getQuery();
        String path = remoteUri.getPath();

        if (host != null)
        {
            sb.append(host);
        }
        if (path != null)
        {
            sb.append(path);
        }
        if (query != null)
        {
            sb.append('?');
            sb.append(query);
        }

        String name;

        final int maxLen = 250;

        if (sb.length() < maxLen)
        {
            name = sb.toString();
        }
        else
        {
            name = sb.substring(0, maxLen);
        }

        name = name.replace('?', '$');
        name = name.replace('*', '$');
        name = name.replace(':', '$');
        name = name.replace('<', '$');
        name = name.replace('>', '$');
        name = name.replace('"', '$');

        File f = new File(cacheDir, name);

        return f;
    }

    /**
     * @param remoteUri the remote URI
     * @param localFile the corresponding local file
     * @return true if the resource at the given remote URI is newer than the resource cached locally.
     */
    private static boolean isUpdateAvailable(URL remoteUri, File localFile)
    {
        URLConnection conn;
        try
        {
            conn = remoteUri.openConnection();
        }
        catch (MalformedURLException ex)
        {
            log.error("An exception occurred", ex);
            return false;
        }
        catch (IOException ex)
        {
            log.error("An exception occurred", ex);
            return false;
        }
        if (!(conn instanceof HttpURLConnection))
        {
            // don't bother with non-http connections
            return false;
        }

        long localLastMod = localFile.lastModified();
        long remoteLastMod = 0L;
        HttpURLConnection httpconn = (HttpURLConnection) conn;
        // disable caching so we don't get in feedback loop with ResponseCache
        httpconn.setUseCaches(false);
        try
        {
            httpconn.connect();
            remoteLastMod = httpconn.getLastModified();
        }
        catch (IOException ex)
        {
            // log.error("An exception occurred", ex);();
            return false;
        }
        finally
        {
            httpconn.disconnect();
        }

        return (remoteLastMod > localLastMod);
    }

    // use Java7 functionality when upgrading
    private static long copy(InputStream source, OutputStream sink) throws IOException
    {
        long nread = 0L;
        byte[] buf = new byte[8192];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }
}

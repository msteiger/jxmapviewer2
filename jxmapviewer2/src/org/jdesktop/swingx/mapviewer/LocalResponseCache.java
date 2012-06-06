
package org.jdesktop.swingx.mapviewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author joshy
 */
public class LocalResponseCache extends ResponseCache
{
	private static final boolean IS_CACHE_DISABLED = true;
	static
	{
		if (IS_CACHE_DISABLED)
		{
			System.out.println("[AERITH] Cache disabled");
		}
	}

	private static final File CACHE_DIR = new File(System.getProperty("user.home") + File.separator + ".aerith");

	static
	{
		if (!CACHE_DIR.exists())
		{
			CACHE_DIR.mkdir();
		}
	}

	/**
	 * Private constructor to prevent instantiation.
	 */
	private LocalResponseCache()
	{
	}

	/**
	 * Sets this cache as default response cache
	 */
	public static void installResponseCache()
	{
		if (!IS_CACHE_DISABLED)
		{
			ResponseCache.setDefault(new LocalResponseCache());
		}
	}

	/**
	 * Returns the local File corresponding to the given remote URI.
	 * @param remoteUri the remote URI
	 * @return the corresponding local file
	 */
	public static File getLocalFile(URI remoteUri)
	{
		int code = remoteUri.hashCode();
		String fileName = Integer.toString(code >= 0 ? code : -code);
		return new File(CACHE_DIR, fileName);
	}

	/**
	 * @param remoteUri the remote URI
	 * @param localFile the corresponding local file
	 * @return true if the resource at the given remote URI is newer than the resource cached locally.
	 */
	private static boolean isUpdateAvailable(URI remoteUri, File localFile)
	{
		URLConnection conn;
		try
		{
			conn = remoteUri.toURL().openConnection();
		}
		catch (MalformedURLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
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
			// ex.printStackTrace();
			return false;
		}
		finally
		{
			httpconn.disconnect();
		}

		return (remoteLastMod > localLastMod);
	}

	@Override
	public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException
	{
		File localFile = getLocalFile(uri);
		if (!localFile.exists())
		{
			// the file isn't already in our cache, return null
			return null;
		}

		if (isUpdateAvailable(uri, localFile))
		{
			// there is an update available, so don't return cached version
			return null;
		}

		if (!localFile.exists())
		{
			// the file isn't already in our cache, return null
			return null;
		}

		return new LocalCacheResponse(localFile, rqstHeaders);
	}

	@Override
	public CacheRequest put(URI uri, URLConnection conn) throws IOException
	{
		// only cache http(s) GET requests
		if (!(conn instanceof HttpURLConnection) || !(((HttpURLConnection) conn).getRequestMethod().equals("GET")))
		{
			return null;
		}

		File localFile = getLocalFile(uri);
		return new LocalCacheRequest(localFile);
	}

	private class LocalCacheResponse extends CacheResponse
	{
		private FileInputStream fis;
		private final Map<String, List<String>> headers;

		private LocalCacheResponse(File localFile, Map<String, List<String>> rqstHeaders)
		{
			try
			{
				this.fis = new FileInputStream(localFile);
			}
			catch (FileNotFoundException ex)
			{
				// should not happen, since we already checked for existence
				ex.printStackTrace();
			}
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
			return fis;
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
				this.fos = new FileOutputStream(localFile);
			}
			catch (FileNotFoundException ex)
			{
				// should not happen
				ex.printStackTrace();
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

package org.jxmapviewer.cache;

import junit.framework.TestCase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created 10/28/23 by Michał Szwaczko (mikey@wirelabs.net)
 */
public class FileBasedLocalCacheTest extends TestCase {

    private static final File CACHE_DIR = new File("target/filcachetest");

    private static final String URL1 = "http://a.pl/abcde";
    private static final String EXPECTED_FILE_1 = "abcde";

    private static final String URL2 = "http://a.pl/z:c?ab";
    private static final String EXPECTED_FILE_2 = "z$c$ab";

    private static final String URL3 = "http://a.pl/z<:>*\"";
    private static final String EXPECTED_FILE_3 = "z$$$$$";

    public void testGetLocalFile() throws MalformedURLException {
        FileBasedLocalCache cache = new FileBasedLocalCache(CACHE_DIR, false);

        File f = cache.getLocalFile(new URL(URL1));
        assertEquals(EXPECTED_FILE_1, f.getName());

        f = cache.getLocalFile(new URL(URL2));
        assertEquals(EXPECTED_FILE_2, f.getName());

        f = cache.getLocalFile(new URL(URL3));
        assertEquals(EXPECTED_FILE_3, f.getName());

    }
}
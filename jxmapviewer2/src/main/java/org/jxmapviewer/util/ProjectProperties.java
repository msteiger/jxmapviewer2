package org.jxmapviewer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Project properties.
 *
 * @author Primoz K.
 */
public enum ProjectProperties {

    /**
     * The only instance of this class
     */
    INSTANCE;

    private static final String PROPERTIES_FILE = "project.properties";

    private static final String PROP_VERSION = "version";
    private static final String PROP_NAME = "name";

    private final Log log = LogFactory.getLog(ProjectProperties.class);
    private final Properties props = new Properties();

    private ProjectProperties() {
        log.debug("Loading project properties...");

        InputStream is = null;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            is = classloader.getResourceAsStream(PROPERTIES_FILE);
            props.load(is);
            log.debug("Properties successfully loaded.");

        }
        catch (IOException e) {
            log.warn("Unable to read project properties.", e);
            props.put(PROP_NAME, "JxMapViewer");
            props.put(PROP_VERSION, "1.0");
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException e) {
                log.warn("Unable to close stream.", e);
            }
        }
    }

    /***************************************************************
     ********************* PROPERTIES GETTERS **********************
     ***************************************************************/

    /**
     * @return Project version.
     */
    public String getVersion() {
        return props.getProperty(PROP_VERSION);
    }

    /**
     * @return Project name.
     */
    public String getName() {
        return props.getProperty(PROP_NAME);
    }

}

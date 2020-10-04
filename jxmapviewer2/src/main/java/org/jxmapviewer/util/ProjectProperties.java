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

    private static final String PROPERTIES_FILE = "/project.properties";

    private static final String PROP_VERSION = "version";
    private static final String PROP_NAME = "name";

    private final Log log = LogFactory.getLog(ProjectProperties.class);
    private final Properties props = new Properties();

    private ProjectProperties() {
        log.debug("Loading project properties...");

        try (InputStream is = ProjectProperties.class.getResourceAsStream(PROPERTIES_FILE)) {
            if (is != null) {
                props.load(is);
                log.debug("Properties successfully loaded.");
            } else {
                log.warn("Project properties file not found. Set default values.");
                props.put(PROP_NAME, "JxMapViewer");
                props.put(PROP_VERSION, "1.0");
            }
        }
        catch (IOException e) {
            log.warn("Unable to read project properties.", e);
            props.put(PROP_NAME, "JxMapViewer");
            props.put(PROP_VERSION, "1.0");
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

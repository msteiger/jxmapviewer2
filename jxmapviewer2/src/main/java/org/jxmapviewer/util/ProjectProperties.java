package org.jxmapviewer.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Project properties.
 *
 * @author Primoz K.
 */
public class ProjectProperties {

	private static final Log log = LogFactory.getLog(ProjectProperties.class);

	private static Properties props = new Properties();

	private static final String PROPERTIES_FILE = "project.properties";

	/***************************************************************
	 ************************* PROPERTIES **************************
	 ***************************************************************/

	private static final String PROP_VERSION = "version";
	private static final String PROP_NAME = "name";

	/***************************************************************
	 *********************** INITIALIZATION ************************
	 ***************************************************************/

	static {
		log.debug("Loading project properties...");

		InputStream is = null;
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			is = classloader.getResourceAsStream(PROPERTIES_FILE);
			props.load(is);
			log.debug("Properties successfully loaded.");

		}
		catch (IOException e) {
			throw new RuntimeException("Unable to read project properties." + e);
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
	public static String getVersion() {
		return props.getProperty(PROP_VERSION);
	}

	/**
	 * @return Project name.
	 */
	public static String getName() {
		return props.getProperty(PROP_NAME);
	}

}

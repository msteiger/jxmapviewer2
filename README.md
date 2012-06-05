This project is based on the JXMapViewer component of SwingX-WS.

The project was hosted at SwingLabs.org, but has been discontinued. We extracted the JXMapViewer part from the latest SwingX-WS version available and continue developing it.

The [JXMapViewer Tutorial by Joshua Marinacci](http://today.java.net/pub/a/today/2007/10/30/building-maps-into-swing-app-with-jxmapviewer.html) gives a good overview over the functionality of the component.

The original source code can be retrieved at:

 * http://java.net/projects/swingx
 * http://java.net/projects/swingx-ws

This source code used in this project is based on: 

 * swingx-ws-2009_06_14.jar
 * SwingX 1.6.3 (released Feb 2012)

#### What we have done so far ..

 * Extract the JXMapViewer part from the full package
 * Upgrade to Java 6
 * Fixed javadoc
 * Replaced `system.out` with  `apache.commons.logging` 
 * Removed broken TileProviders

#### What we plan to do ..

 * Remove dependencies to SwingX (and its indirect dependencies)
 * Add tile caching
 * Fix bugs that were posted on http://java.net/jira/browse/SWINGX_WS

#### License
This project has been licensed under the GNU Lesser General Public License (LGPL)
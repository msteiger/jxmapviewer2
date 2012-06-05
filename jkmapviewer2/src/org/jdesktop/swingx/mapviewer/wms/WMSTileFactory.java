/*
 * WMSTileFactory.java
 *
 * Created on October 7, 2006, 6:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.swingx.mapviewer.wms;

import org.jdesktop.swingx.mapviewer.*;

/**
 * A tile factory that uses a WMS service.
 * @author joshy
 */
public class WMSTileFactory extends DefaultTileFactory {
    /*
     *todos: nuke the google url. it's not needed.
     * rework the var names to make them make sense
     * remove
     */ 
    /** Creates a new instance of WMSTileFactory */
    public WMSTileFactory(final WMSService wms) {
        super(new TileFactoryInfo(0,15,17, 
                    500, true, true, // tile size and x/y orientation is r2l & t2b
                "","x","y","zoom") {
                    public String getTileUrl(int x, int y, int zoom) {
                        int zz = 17-zoom;
                        int z = 4;
                        z = (int)Math.pow(2,(double)zz-1);
                        return wms.toWMSURL(x-z, z-1-y, zz, getTileSize(zoom));
                    }
                    
        });
    }
    
}

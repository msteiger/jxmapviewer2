package org.jxmapviewer;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;

public abstract class AbstractJXMapViewer extends JPanel{

	/**
	 * Indicate that the component is being used at design time, such as in a visual editor like NetBeans' Matisse
	 * @param b indicates if the component is being used at design time
	 */
	public abstract void setDesignTime(boolean b);

	/**
	 * Indicates whether the component is being used at design time, such as in a visual editor like NetBeans' Matisse
	 * @return boolean indicating if the component is being used at design time
	 */
	public abstract boolean isDesignTime();

	/**
	 * Sets the map overlay. This is a {@code Painter<JXMapViewer>} which will paint on top of the map. It can be used to draw waypoints,
	 * lines, or static overlays like text messages.
	 * @param overlay the map overlay to use
	 */
	public abstract void setOverlayPainter(Painter<? super JXMapViewer> overlay);

	/**
	 * Gets the current map overlay
	 * @return the current map overlay
	 */
	public abstract Painter<? super JXMapViewer> getOverlayPainter();

	/**
	 * Returns the bounds of the viewport in pixels. This can be used to transform points into the world bitmap
	 * coordinate space.
	 * @return the bounds in <em>pixels</em> of the "view" of this map
	 */
	public abstract Rectangle getViewportBounds();

	/**
	 * Set the current zoom level
	 * @param zoom the new zoom level
	 */
	public abstract void setZoom(int zoom);

	/**
	 * Gets the current zoom level
	 * @return the current zoom level
	 */
	public abstract int getZoom();

	/**
	 * Gets the current address location of the map. This property does not change when the user pans the map. This
	 * property is bound.
	 * @return the current map location (address)
	 */
	public abstract GeoPosition getAddressLocation();

	/**
	 * Gets the current address location of the map
	 * @param addressLocation the new address location
	 */
	public abstract void setAddressLocation(GeoPosition addressLocation);

	/**
	 * Re-centers the map to have the current address location be at the center of the map, accounting for the map's
	 * width and height.
	 */
	public abstract void recenterToAddressLocation();

	/**
	 * Indicates if the tile borders should be drawn. Mainly used for debugging.
	 * @return the value of this property
	 */
	public abstract boolean isDrawTileBorders();

	/**
	 * Set if the tile borders should be drawn. Mainly used for debugging.
	 * @param drawTileBorders new value of this drawTileBorders
	 */
	public abstract void setDrawTileBorders(boolean drawTileBorders);

	/**
	 * A property indicating the center position of the map
	 * @param geoPosition the new property value
	 */
	public abstract void setCenterPosition(GeoPosition geoPosition);

	/**
	 * A property indicating the center position of the map
	 * @return the current center position
	 */
	public abstract GeoPosition getCenterPosition();

	/**
	 * Get the current factory
	 * @return the current property value
	 */
	public abstract TileFactory getTileFactory();

	/**
	 * Set the current tile factory (must not be <code>null</code>)
	 * @param factory the new property value
	 */
	public abstract void setTileFactory(TileFactory factory);

	/**
	 * A property for an image which will be display when an image is still loading.
	 * @return the current property value
	 */
	public abstract Image getLoadingImage();

	/**
	 * A property for an image which will be display when an image is still loading.
	 * @param loadingImage the new property value
	 */
	public abstract void setLoadingImage(Image loadingImage);

	/**
	 * Gets the current pixel center of the map. This point is in the global bitmap coordinate system, not as lat/longs.
	 * @return the current center of the map as a pixel value
	 */
	public abstract Point2D getCenter();

	/**
	 * Sets the new center of the map in pixel coordinates.
	 * @param center the new center of the map in pixel coordinates
	 */
	public abstract void setCenter(Point2D center);

	public abstract Point2D.Double getNewCenter(Point2D center);

	/**
	 * Calculates a zoom level so that all points in the specified set will be visible on screen. This is useful if you
	 * have a bunch of points in an area like a city and you want to zoom out so that the entire city and it's points
	 * are visible without panning.
	 * @param positions A set of GeoPositions to calculate the new zoom from
	 */
	public abstract void calculateZoomFrom(Set<GeoPosition> positions);

	/**
	 * Zoom and center the map to a best fit around the input GeoPositions.
	 * Best fit is defined as the most zoomed-in possible view where both
	 * the width and height of a bounding box around the positions take up
	 * no more than maxFraction of the viewport width or height respectively.
	 * @param positions A set of GeoPositions to calculate the new zoom from
	 * @param maxFraction the maximum fraction of the viewport that should be covered
	 */
	public abstract void zoomToBestFit(Set<GeoPosition> positions, double maxFraction);

	/**
	 * @return true if panning is restricted or not
	 */
	public abstract boolean isRestrictOutsidePanning();

	/**
	 * @param restrictOutsidePanning set if panning is restricted or not
	 */
	public abstract void setRestrictOutsidePanning(boolean restrictOutsidePanning);

	/**
	 * @return true if horizontally wrapped or not
	 */
	public abstract boolean isHorizontalWrapped();

	/**
	 * Side note: This setting is ignored when  horizontaklWrapped is set to true.
	 *
	 * @param infiniteMapRendering true when infinite map rendering should be enabled
	 */
	public abstract void setInfiniteMapRendering(boolean infiniteMapRendering);

	/**
	 * @return true if infinite map rendering is enabled
	 */
	public abstract boolean isInfiniteMapRendering();

	/**
	 * @param horizontalWrapped true if horizontal wrap is enabled
	 */
	public abstract void setHorizontalWrapped(boolean horizontalWrapped);

	/**
	 * Converts the specified GeoPosition to a point in the JXMapViewer's local coordinate space. This method is
	 * especially useful when drawing lat/long positions on the map.
	 * @param pos a GeoPosition on the map
	 * @return the point in the local coordinate space of the map
	 */
	public abstract Point2D convertGeoPositionToPoint(GeoPosition pos);

	/**
	 * Converts the specified Point2D in the JXMapViewer's local coordinate space to a GeoPosition on the map. This
	 * method is especially useful for determining the GeoPosition under the mouse cursor.
	 * @param pt a point in the local coordinate space of the map
	 * @return the point converted to a GeoPosition
	 */
	public abstract GeoPosition convertPointToGeoPosition(Point2D pt);

	/**
	 * @return isNegativeYAllowed
	 * @deprecated do not use
	 */
	public abstract boolean isNegativeYAllowed();

	/**
	 * Enables or disables panning.
	 * Useful for performing selections on the map.
	 * @param enabled if true, panning is enabled (the default), if false, panning is disabled
	 */
	public abstract void setPanEnabled(boolean enabled);
	abstract public boolean isPanningEnabled();





}
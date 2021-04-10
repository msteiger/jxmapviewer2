package org.jxmapviewer.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import org.jxmapviewer.JXMapViewer;

/**
 * Abstract mouse wheel event listener.
 *
 * <p>This input listener can be disabled. It is enabled by default. When it is
 * disabled, mouse wheel events will have no effect.
 */
public abstract class DisableableMouseWheelListener implements MouseWheelListener, DisableableInputAdapter {
    protected JXMapViewer viewer;
    protected boolean enabled;

    /**
     * Constructor.
     * @param viewer the map viewer to attach this listener to.
     */
    public DisableableMouseWheelListener(JXMapViewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public abstract void mouseWheelMoved(MouseWheelEvent evt);

    /**
     * Check if this input listener is enabled.
     * @return true if it is enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set whether this input listener is enabled or not.
     *
     * If it is not enabled, user input will have no effect.
     * @param enabled true to enable, false to disable.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

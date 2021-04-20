package org.jxmapviewer.input.filter;

import java.awt.event.MouseListener;

/**
 * Filter that allows mouse events to be enabled and disabled.
 * 
 * This is used as a wrapper / filter over a specific MouseListener implementation
 * (e.g. CenterMapListener or a MapClickListener subclass).
 */
public class EnableableMouseListenerFilter extends AbstractMouseListenerFilter {

    private boolean enabled = true;

    public EnableableMouseListenerFilter(MouseListener mouseListener) {
        super(mouseListener);
    }

    /**
     * Check whether events are enabled or not.
     *
     * Events are enabled by default.
     *
     * @return true if events are enabled, otherwise false.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set whether events are enabled.
     *
     * @param enabled true to enable events, false to disable events.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    protected boolean shouldForward() {
        return enabled;
    }
    
}

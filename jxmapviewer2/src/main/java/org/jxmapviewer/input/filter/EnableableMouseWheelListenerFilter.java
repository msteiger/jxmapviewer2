package org.jxmapviewer.input.filter;

import java.awt.event.MouseWheelListener;

/**
 * Filter that allows mouse wheel events to be enabled and disabled.
 *
 * This is used as a wrapper / forwarder over a specific MouseWheelListener
 * implementation (e.g. ZoomMouseWheelListenerCenter or
 * ZoomMouseWheelListenerCursor).
 */
public class EnableableMouseWheelListenerFilter extends AbstractMouseWheelListenerFilter {

    private boolean enabled = true;

    public EnableableMouseWheelListenerFilter(MouseWheelListener mouseWheelListener) {
        super(mouseWheelListener);
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

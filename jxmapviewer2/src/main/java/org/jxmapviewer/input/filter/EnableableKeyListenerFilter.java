package org.jxmapviewer.input.filter;

import java.awt.event.KeyListener;

/**
 * Filter that allows key events to be enabled and disabled.
 *
 * This is used as a wrapper / filter over a specific KeyListener implementation
 * (e.g. PanKeyListener).
 */
public class EnableableKeyListenerFilter extends AbstractKeyListenerFilter {

    private boolean enabled = true;

    public EnableableKeyListenerFilter(KeyListener keyListener) {
        super(keyListener);
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

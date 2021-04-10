package org.jxmapviewer.input;

/**
 * An interface for input adapters that can be disabled.
 */
public interface DisableableInputAdapter {

    /**
     * Check if this input listener is enabled.
     * @return true if it is enabled, otherwise false.
     */
    boolean isEnabled();

    /**
     * Set whether this input listener is enabled or not.
     *
     * If it is not enabled, user input will have no effect.
     * @param enabled true to enable, false to disable.
     */
    void setEnabled(boolean enabled);
    
}

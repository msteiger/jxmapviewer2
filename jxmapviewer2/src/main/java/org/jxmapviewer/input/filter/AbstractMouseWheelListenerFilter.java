package org.jxmapviewer.input.filter;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Filtering forwarder class for MouseWheelListener implementations.
 * 
 * To apply this class, you need to implement the appropriate shouldForward logic,
 * then construct an instance that wraps a specific MouseWheelListener implementation
 * (e.g. ZoomMouseWheelListenerCenter or ZoomMouseWheelListenerCursor).
 */
public abstract class AbstractMouseWheelListenerFilter implements MouseWheelListener {
    protected MouseWheelListener mouseWheelListener;

    public AbstractMouseWheelListenerFilter(MouseWheelListener mouseWheelListener) {
        this.mouseWheelListener = mouseWheelListener;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (shouldForward()) {
            mouseWheelListener.mouseWheelMoved(mwe);
        }
    }

    /**
     * Check if the filter should forward events.
     * @return true if forwarding should occur, otherwise false.
     */
    protected abstract boolean shouldForward();
}

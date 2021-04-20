package org.jxmapviewer.input.filter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Filtering forwarder class for MouseListener implementations.
 * 
 * To apply this class, you need to implement the appropriate shouldForward logic,
 * then construct an instance that wraps a specific MouseListener implementation
 * (e.g. CenterMapListener or a MapClickListener subclass).
 */
public abstract class AbstractMouseListenerFilter implements MouseListener {
    protected MouseListener mouseListener;

    public AbstractMouseListenerFilter(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    /**
     * Check if the filter should forward events.
     * @return true if forwarding should occur, otherwise false.
     */
    protected abstract boolean shouldForward();

    @Override
    public void mouseClicked(MouseEvent me) {
        if (shouldForward()) {
            mouseListener.mouseClicked(me);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (shouldForward()) {
            mouseListener.mousePressed(me);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (shouldForward()) {
            mouseListener.mouseReleased(me);
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (shouldForward()) {
            mouseListener.mouseEntered(me);
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (shouldForward()) {
            mouseListener.mouseExited(me);
        }
    }
}

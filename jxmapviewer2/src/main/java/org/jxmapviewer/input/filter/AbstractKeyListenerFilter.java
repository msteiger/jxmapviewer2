package org.jxmapviewer.input.filter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Filtering forwarder class for KeyListener implementations.
 * 
 * To apply this class, you need to implement the appropriate shouldForward logic,
 * then construct an instance that wraps a specific KeyListener implementation
 * (e.g. PanKeyListener).
 */
public abstract class AbstractKeyListenerFilter implements KeyListener {
    protected KeyListener keyListener;

    public AbstractKeyListenerFilter(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    /**
     * Check if the filter should forward events.
     * @return true if forwarding should occur, otherwise false.
     */
    protected abstract boolean shouldForward();

    @Override
    public void keyTyped(KeyEvent ke) {
        if (shouldForward()) {
            keyListener.keyTyped(ke);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (shouldForward()) {
            keyListener.keyPressed(ke);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (shouldForward()) {
            keyListener.keyReleased(ke);
        }
    }
}

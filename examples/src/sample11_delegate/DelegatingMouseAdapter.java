package sample11_delegate;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Delegates all mouse events to other implementations, by default a dummy object that does nothing.
 */
public class DelegatingMouseAdapter implements MouseListener, MouseMotionListener, MouseWheelListener  {

    public static final MouseAdapter DUMMY = new MouseAdapter() { /* do nothing */ };

    private MouseListener mouseListener = DUMMY;
    private MouseMotionListener mouseMotionListener = DUMMY;
    private MouseWheelListener mouseWheelListener = DUMMY;

    public MouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public MouseWheelListener getMouseWheelListener() {
        return mouseWheelListener;
    }

    public void setMouseWheelListener(MouseWheelListener mouseWheelListener) {
        this.mouseWheelListener = mouseWheelListener;
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseListener.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseListener.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseListener.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseListener.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseListener.mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMotionListener.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseMotionListener.mouseMoved(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelListener.mouseWheelMoved(e);
    }

}

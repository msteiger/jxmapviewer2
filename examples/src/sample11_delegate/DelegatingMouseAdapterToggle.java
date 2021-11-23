package sample11_delegate;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

/**
 * Enables/disables a {@link DelegatingMouseAdapter} by changing the delegates
 */
public class DelegatingMouseAdapterToggle  {

    private final DelegatingMouseAdapter adapter;

    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;
    private MouseWheelListener mouseWheelListener;

    private boolean enabled = true;

    public DelegatingMouseAdapterToggle(DelegatingMouseAdapter adapter) {
        this.adapter = adapter;
    }

    public void enable() {
        adapter.setMouseListener(mouseListener);
        adapter.setMouseMotionListener(mouseMotionListener);
        adapter.setMouseWheelListener(mouseWheelListener);
        enabled = true;
    }

    public void disable() {
        mouseListener = adapter.getMouseListener();
        mouseMotionListener = adapter.getMouseMotionListener();
        mouseWheelListener = adapter.getMouseWheelListener();

        adapter.setMouseListener(DelegatingMouseAdapter.DUMMY);
        adapter.setMouseMotionListener(DelegatingMouseAdapter.DUMMY);
        adapter.setMouseWheelListener(DelegatingMouseAdapter.DUMMY);
        enabled = false;
    }

    public void setEnabled(boolean yes) {
        if (yes) {
            enable();
        } else {
            disable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public boolean isEnabled() {
        return enabled;
    }
}

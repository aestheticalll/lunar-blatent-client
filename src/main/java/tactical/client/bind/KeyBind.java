package tactical.client.bind;

import tactical.client.feature.trait.Feature;
import tactical.client.feature.trait.Toggle;

import static org.lwjgl.input.Keyboard.KEY_NONE;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class KeyBind implements Feature, Toggle {

    private final String key;
    private final Inhibitor inhibitor;
    private boolean state;

    private int keyCode = KEY_NONE;

    public KeyBind(String key, Inhibitor inhibitor) {
        this.key = key;
        this.inhibitor = inhibitor;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void setState(boolean state) {
        this.state = state;
        if (state) {
            enable();
        } else {
            disable();
        }

        if (inhibitor != null)
            inhibitor.invoke(state);
    }

    @Override
    public boolean toggled() {
        return state;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int keyCode() {
        return keyCode;
    }
}

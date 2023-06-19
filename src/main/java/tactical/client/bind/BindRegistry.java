package tactical.client.bind;

import net.minecraft.client.Minecraft;
import tactical.client.Tactical;
import tactical.client.feature.Registry;
import tactical.client.listener.bus.Listener;
import tactical.client.listener.bus.Subscribe;
import tactical.client.listener.event.input.EventKeyInput;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.input.Keyboard.KEY_NONE;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class BindRegistry extends Registry<KeyBind> {

    /**
     * The {@link Minecraft} game instance
     */
    private final Minecraft mc = Minecraft.getMinecraft();

    private final Map<String, KeyBind> keyBindMap = new HashMap<>();

    @Override
    protected void init() {
        // TODO: add config
        Tactical.bus().subscribe(this);
    }

    @Subscribe
    private final Listener<EventKeyInput> keyInput = (event) -> {
        if (event.keyCode() == KEY_NONE || mc.currentScreen != null) return;

        for (KeyBind keyBind : keyBindMap.values()) {
            if (keyBind.keyCode() == event.keyCode())
                keyBind.setState(!keyBind.toggled());
        }
    };

    /**
     * Registers a {@link KeyBind} to this registry
     * @param keyBind the {@link KeyBind} object
     */
    public void register(KeyBind keyBind) {
        keyBindMap.put(keyBind.key(), keyBind);
    }
}

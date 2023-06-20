package tactical.client.feature.module.registry;

import net.minecraft.client.Minecraft;
import tactical.client.Tactical;
import tactical.client.bind.BindRegistry;
import tactical.client.bind.KeyBind;
import tactical.client.feature.Registry;
import tactical.client.feature.module.setting.Setting;
import tactical.client.feature.trait.Feature;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.trait.Toggle;
import tactical.client.utility.render.animation.Animation;
import tactical.client.utility.render.animation.Easing;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Module implements Feature, Toggle {

    /**
     * The {@link Minecraft} game instance
     */
    protected final Minecraft mc = Minecraft.getMinecraft();

    private final String key;
    private final Category category;

    private final Map<String, Setting<?>> settingMap = new LinkedHashMap<>();
    private final KeyBind keyBind;

    private final Animation animation = new Animation(
            Easing.CUBIC_IN_OUT, 250.0, false);

    public Module() {

        // check if this class was annotated with @Register
        if (!getClass().isAnnotationPresent(Register.class)) {
            throw new RuntimeException(
                    "@Register needs to be at the top of " + getClass().getSimpleName());
        }

        // get the annotation data
        Register register = getClass().getDeclaredAnnotation(Register.class);

        key = register.value();
        category = register.category();

        // create bind & registry
        keyBind = new KeyBind(key, (state) -> {
            if (state) {
                enable();
            } else {
                disable();
            }
        });
        Registry.get(BindRegistry.class).register(keyBind);
    }

    public void reflectSettings() {
        for (Field field : getClass().getDeclaredFields()) {
            if (Setting.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);

                try {
                    Setting<?> setting = (Setting<?>) field.get(this);
                    settingMap.put(setting.key(), setting);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> Setting<T> get(String name) {
        return (Setting<T>) settingMap.getOrDefault(name, null);
    }

    public Collection<Setting<?>> settings() {
        return settingMap.values();
    }

    @Override
    public void enable() {
        Tactical.bus().subscribe(this);
        animation.setState(true);
    }

    @Override
    public void disable() {
        Tactical.bus().unsubscribe(this);
        animation.setState(false);
    }

    @Override
    public void setState(boolean state) {
        keyBind.setState(state);
    }

    @Override
    public boolean toggled() {
        return keyBind.toggled();
    }

    public KeyBind keyBind() {
        return keyBind;
    }

    public Animation animation() {
        return animation;
    }

    @Override
    public String key() {
        return key;
    }

    public Category category() {
        return category;
    }
}

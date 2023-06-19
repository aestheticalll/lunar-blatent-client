package tactical.client.feature.module.registry;

import net.minecraft.client.Minecraft;
import tactical.client.Tactical;
import tactical.client.bind.BindRegistry;
import tactical.client.bind.KeyBind;
import tactical.client.feature.Registry;
import tactical.client.feature.trait.Feature;
import tactical.client.feature.module.registry.annotation.Register;
import tactical.client.feature.trait.Toggle;

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

    private final KeyBind keyBind;

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

    @Override
    public void enable() {
        Tactical.bus().subscribe(this);
    }

    @Override
    public void disable() {
        Tactical.bus().unsubscribe(this);
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

    @Override
    public String key() {
        return key;
    }

    public Category category() {
        return category;
    }
}

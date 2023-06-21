package tactical.client.feature.command.registry;

import tactical.client.feature.command.registry.annotation.Register;
import tactical.client.feature.trait.Feature;

/**
 * @author Gavin
 * @since 1.0.0
 */
public abstract class Command implements Feature {

    private final String[] aliases;
    private final String syntax;

    public Command() {
        if (!getClass().isAnnotationPresent(Register.class)) {
            throw new RuntimeException(
                    "@Register needs to be at the top of " + getClass().getSimpleName());
        }

        Register register = getClass().getDeclaredAnnotation(Register.class);

        aliases = register.aliases();
        syntax = register.syntax();
    }

    /**
     * Dispatches this {@link Command}
     * @param args a String[] of arguments
     * @throws Exception see package tactical.client.command.exception
     */
    public abstract void dispatch(String[] args) throws Exception;

    /**
     * Suggests an argument
     * @param argument the currently typed in argument value
     * @param index the index of the argument
     * @return the suggestion or null
     */
    public String suggest(String argument, int index) {
        return null;
    }

    @Override
    public String key() {
        return null;
    }

    public String[] aliases() {
        return aliases;
    }

    public String syntax() {
        return syntax;
    }
}

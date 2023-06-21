package tactical.client.feature.command.registry.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {

    /**
     * The aliases used to trigger this {@link tactical.client.feature.command.registry.Command}
     * @return a list of aliases
     */
    String[] aliases();

    /**
     * The syntax of this {@link tactical.client.feature.command.registry.Command}
     * @return the syntax
     */
    String syntax() default "";
}

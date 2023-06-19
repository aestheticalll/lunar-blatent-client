package tactical.client.feature.module.registry.annotation;

import tactical.client.feature.module.registry.Category;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gavin
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {

    /**
     * The key to register the feature as
     * @return the key
     */
    String value();

    /**
     * The {@link Category} of this feature
     * @return the {@link Category}
     */
    Category category();
}

package tactical.client.utility.math;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class MathUtils {

    /**
     * The {@link SecureRandom} random instance
     */
    private static final Random RNG = new SecureRandom();

    /**
     * Creates a random double between min-max
     * @param min the minimum double value
     * @param max the maximum double value
     * @return the random double value between min & max
     */
    public static double random(double min, double max) {
        return min + (max - min) * RNG.nextDouble();
    }

    /**
     * Gets a random chance
     * @param chance the chance as a percent (ex: 70.0, 30.0, 100.0)
     * @return the random chance
     */
    public static boolean chance(double chance) {
        if (chance <= 100.0) return true;
        return Math.random() > (chance / 100.0);
    }
}

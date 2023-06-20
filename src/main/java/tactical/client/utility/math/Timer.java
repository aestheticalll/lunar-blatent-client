package tactical.client.utility.math;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class Timer {

    /**
     * A conversion for 1ms = 1 million nanoseconds
     */
    private static final long MS_TO_NS = 1_000_000L;

    private long lastTime = -1L;

    public Timer() {
        resetTimer();
    }

    /**
     * Checks if time has passed in milliseconds
     * @param ms the time in milliseconds
     * @param reset if to automatically reset the timer if the time has passed
     * @return if the time has passed
     */
    public boolean passed(long ms, boolean reset) {
        boolean passed = elapsedMs() > ms;
        if (passed && reset) resetTimer();
        return passed;
    }

    /**
     * Resets the timer to current nano time
     */
    public void resetTimer() {
        lastTime = System.nanoTime();
    }

    /**
     * Gets the time elapsed in nanoseconds
     * @return the time elapsed in nanoseconds
     */
    public long elapsed() {
        return System.nanoTime() - lastTime;
    }

    /**
     * Gets the time elapsed in milliseconds
     * @return the time elapsed in milliseconds
     */
    public long elapsedMs() {
        return elapsed() / MS_TO_NS;
    }
}

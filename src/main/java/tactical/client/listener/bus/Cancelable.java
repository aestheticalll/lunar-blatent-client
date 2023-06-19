package tactical.client.listener.bus;

/**
 * @author Gavin
 * @since 06/11/23
 */
public class Cancelable {
    private boolean canceled = false;

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }
}

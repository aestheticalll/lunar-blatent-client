package tactical.client.listener.event.input;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class EventAttackReach {

    private double reach;

    public EventAttackReach(double reach) {
        this.reach = reach;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }
}

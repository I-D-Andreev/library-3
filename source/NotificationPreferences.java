import java.io.Serializable;

/**
 * A class to model a user's notification preferences.
 *
 * @author Ivan Andreev
 */
public class NotificationPreferences implements Serializable {
    /**
     * Whether a user would like to receive notification for when to return a resource.
     */
    private boolean receiveReturnResourceNotification;

    /**
     * Whether a user would like to receive notification for new additions.
     */
    private boolean receiveNewAdditionsNotification;

    /**
     * Whether a user would like to receive notification for new reserved resource.
     */
    private boolean receiveNewReservedResourceNotification;

    /**
     * Whether a user would like to receive notification for new events.
     */
    private boolean receiveEventNotification;

    /**
     * Creates a user's default notification preferences.
     */
    public NotificationPreferences() {
        // always true and can't be changed
        receiveReturnResourceNotification = true;

        // not so important, so false by default, so as not to annoy users
        receiveNewAdditionsNotification = false;
        receiveNewReservedResourceNotification = false;
        receiveEventNotification = false;
    }

    /**
     * Get whether a user wants to receive "return a resource" notifications.
     *
     * @return True if Yes, false otherwise.
     */
    public boolean getReceiveReturnResourceNotification() {
        return receiveReturnResourceNotification;
    }

    /**
     * Change the receive "return a resource" notification settings.
     * @param receiveReturnResourceNotification True if Yes, false otherwise.
     */
    public void setReceiveReturnResourceNotification(boolean receiveReturnResourceNotification) {
        this.receiveReturnResourceNotification = receiveReturnResourceNotification;
    }

    /**
     * Get whether a user wants to receive "new additions" notifications.
     *
     * @return True if Yes, false otherwise.
     */
    public boolean getReceiveNewAdditionsNotification() {
        return receiveNewAdditionsNotification;
    }

    /**
     * Change the receive "new additions" notification settings.
     *
     * @param receiveNewAdditionsNotification True if would like to receive, false - otherwise.
     */
    public void setReceiveNewAdditionsNotification(boolean receiveNewAdditionsNotification) {
        this.receiveNewAdditionsNotification = receiveNewAdditionsNotification;
    }

    /**
     * Get whether a user wants to receive "new reserved resource" notifications.
     *
     * @return True if Yes, false otherwise.
     */
    public boolean getReceiveNewReservedResourceNotification() {
        return receiveNewReservedResourceNotification;
    }

    /**
     * Change the receive "new reserved resource" notification settings.
     *
     * @param receiveNewReservedResourceNotification True if would like to receive, false - otherwise.
     */
    public void setReceiveNewReservedResourceNotification(boolean receiveNewReservedResourceNotification) {
        this.receiveNewReservedResourceNotification = receiveNewReservedResourceNotification;
    }

    /**
     * Get whether a user wants to receive "new event" notifications.
     *
     * @return True if Yes, false otherwise.
     */
    public boolean getReceiveEventNotification() {
        return receiveEventNotification;
    }

    /**
     * Change the receive "new event" notification settings.
     * @param receiveEventNotification True if would like to receive, false - otherwise.
     */
    public void setReceiveEventNotification(boolean receiveEventNotification) {
        this.receiveEventNotification = receiveEventNotification;
    }
}

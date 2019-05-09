import java.text.SimpleDateFormat;

/**
 * A class to send notification emails.
 *
 * @author Ivan Andreev
 */
// main usage is that it prepares the message text
public class EmailNotificationSender {
    // some pre-written strings that will be used often in the messages

    // as the messages are text/html the new line is the html's <p> attribute
    private static final String newLine = "<br>";

    private static final String hello = "Hello." + newLine;

    private static final String bestRegards = newLine + "<b>Best regards,</b>"
            + newLine + "<b>The Tawe-Lib Team</b>";

    private static final String unsubscribeInfo = "<p style='color:red;'>You can unsubscribe from these" +
            " emails in: Tawe Lib application/Edit account/Notifications </p>";


    /**
     * Sends a notification for a new event in Tawe-Lib. The user will only receive the notification if they
     * have allowed this type of notifications in their preferences.
     *
     * @param recipient The user that will receive the notification.
     * @param event     The new event.
     * @return True if message sent successfully, false - if not.
     */
    public static boolean sendNewEventNotification(NormalUser recipient, Event event) {
        if (!recipient.getNotificationPreferences().getReceiveEventNotification()) {
            return true;
        }

        String message = hello + "A new event has been added. More details below: " + newLine
                + "Title: " + event.getTitle() + newLine
                + "Description: " + event.getDescription() + newLine
                + "Max number of attendees: " + event.getMaxAttendees() + newLine
                + "Start time (year/month/day hour:minute):  " + event.getStartDate() + " " + event.getStartTime()
                + newLine + bestRegards + unsubscribeInfo;

        return MailSender.sendEmail(recipient.getEmail(), "Tawe-Lib New Event", message);
    }

    /**
     * Sends a notification for a new event in Tawe-Lib. The user will only receive the notification if they
     * have allowed this event types of notifications in their preferences.
     *
     * @param recipient The user that will receive the notification.
     * @param oldEvent  The old information about the event.
     * @param newEvent  The new information about the event.
     * @return True if message sent successfully, false - if not.
     */
    public static boolean sendEventChangedNotification(NormalUser recipient, Event oldEvent, Event newEvent) {
        // This notification about event changes goes in the same preference as New Events, so users
        // do not miss out on an event in case of a change in the time/date.

        if (!recipient.getNotificationPreferences().getReceiveEventNotification()) {
            return true;
        }

        String message = hello + "An existing event has been changed. More details below: " + newLine
                + "Title: " + oldEvent.getTitle() + "  -->  <b>" + newEvent.getTitle() + newLine + "</b>"
                + "Description: " + oldEvent.getDescription() + "  --> <b>" + newEvent.getDescription() + "</b>"
                + newLine
                + "Max number of attendees: " + oldEvent.getMaxAttendees() + "  -->  <b>" + newEvent.getMaxAttendees()
                + "</b>" + newLine
                + "Start time (year/month/day hour:minute): " + oldEvent.getStartDate() + " " + oldEvent.getStartTime()
                + "  --> <b>" + newEvent.getStartDate() + " " + newEvent.getStartTime() + "</b>" + newLine
                + bestRegards + unsubscribeInfo;

        return MailSender.sendEmail(recipient.getEmail(), "Tawe-Lib Event Changed", message);
    }

    /**
     * Send a notification for a new added resource in Tawe-Lib. The user will only receive the notification if they
     * have allowed this type of notifications in their preferences.
     *
     * @param recipient   The user that will receive the notification.
     * @param newResource The newly added resource.
     * @return True if message sent successfully, false - if not.
     */
    public static boolean sendNewResourceAddedNotification(NormalUser recipient, Resource newResource) {
        if (!recipient.getNotificationPreferences().getReceiveNewAdditionsNotification()) {
            return true;
        }

        String message = hello + "A new resource has been added. More details below: " + newLine
                + "Title: " + newResource.getTitle() + newLine
                + "Type: " + newResource.getType() + newLine
                + bestRegards + unsubscribeInfo;

        return MailSender.sendEmail(recipient.getEmail(), "Tawe-Lib New Resource Added", message);
    }

    /**
     * Send a notification when a new resource is reserved for the user. The user will only receive
     * the notification if they have allowed this type of notifications in their preferences.
     *
     * @param recipient        The user that will receive the notification.
     * @param reservedResource The newly reserved resource.
     * @return True if message sent successfully, false - if not.
     */
    public static boolean sendNewResourceReservedNotification(NormalUser recipient, Resource reservedResource) {
        if (!recipient.getNotificationPreferences().getReceiveNewReservedResourceNotification()) {
            return true;
        }

        String message = hello
                + "A new resource has been reserved for you. More details below:" + newLine
                + "Title: " + reservedResource.getTitle() + newLine
                + "Type: " + reservedResource.getType() + newLine
                + bestRegards + unsubscribeInfo;

        return MailSender.sendEmail(recipient.getEmail(), "Tawe-Lib New Resource Reserved For You", message);
    }


    /**
     * Send a notification that a copy should be returned. The user will only receive the notification if they
     * * have allowed this type of notifications in their preferences.
     *
     * @param recipient  The user that will receive the notification.
     * @param returnCopy The copy that should be returned.
     * @return True if message sent successfully, false - if not.
     */
    public static boolean sendNewReturnAResourceNotification(NormalUser recipient, Copy returnCopy) {
        if (!recipient.getNotificationPreferences().getReceiveReturnResourceNotification()) {
            return true;
        }

        // format the due date nicely
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String dueDate = simpleDateFormat.format(returnCopy.getDueDate());

        String message = hello +
                "A resource you have borrowed should soon be returned. More information below:" + newLine
                + "ID: " + returnCopy.getUniqueCopyID() + newLine
                + "Title: " + returnCopy.getCopyOf().getTitle() + newLine
                + "Type: " + returnCopy.getCopyOf().getType() + newLine + newLine

                // the due date should be displayed BOLD and RED
                + "<b style='color:red;'>DUE DATE (year/month/day hour:minute): " + dueDate + "</b>" + newLine

                + "A fine of " + returnCopy.getCopyOf().getLateReturnFinePerDay() + " pounds will apply for each"
                + " day that you are late, up to a maximum of " + returnCopy.getCopyOf().getMaxFineAmount()
                + " pounds total." + newLine
                + bestRegards;

        return MailSender.sendEmail(recipient.getEmail(),
                "Tawe-Lib Resource " + returnCopy.getUniqueCopyID()+  " Should Be Returned By " + dueDate,
                message);
    }

}

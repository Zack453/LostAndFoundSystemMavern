
package lostandfoundsystem.domain;

public class Notification {
    
    private int notificationId;
    private int personId;
    private String message;
    private String status;
    private String dateCreated;
    

 public Notification() {
    }

    public Notification(int notificationId, int personId,
                         String message, String status,
                         String dateCreated) {

        this.notificationId = notificationId;
        this.personId = personId;
        this.message = message;
        this.status = status;
        this.dateCreated = dateCreated;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Notification{"
                + "notificationId=" + notificationId
                + ", personId=" + personId
                + ", message='" + message + '\''
                + ", status='" + status + '\''
                + ", dateCreated='" + dateCreated + '\''
                + '}';
    }
}
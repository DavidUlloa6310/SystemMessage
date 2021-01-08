package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Message {

    private final StringProperty messageID;
    private final StringProperty description;

    public Message() {
        this(null, null);
    }

    public Message(String messageID, String description) {
        this.messageID = new SimpleStringProperty(messageID);
        this.description = new SimpleStringProperty(description);
    }

    public Message(String messageID) {
        this.messageID = new SimpleStringProperty(messageID);
        this.description = new SimpleStringProperty(generateDescription());
    }

    public String generateDescription() {
        String[] description = {"Quick problem, shouldn't be a problem.",
                                "URGENT. Must be addressed quickly.",
                                "Problem only on Linux machines. Only a small group effected, not a big deal.",
                                "Ram Error. Please fix after urgent issues are addressed.",
                                "URGENT. Problem may create hacking vulnerabilities.",
                                "Future scaling issues with cloud integration. Need more efficient systems.",
                                "Encryption vulnerabilities. Please focus on more secure systems",
                                "Problem with backwards compatibility. Must be fixed for legacy systems.",
                                };
        return description[(int)(Math.random() * description.length)];
    }

    public String getMessageID() {
        return messageID.get();
    }

    public StringProperty messageIDProperty() {
        return messageID;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setMessageID(String messageID) {
        this.messageID.set(messageID);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}

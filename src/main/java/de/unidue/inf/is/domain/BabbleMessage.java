package de.unidue.inf.is.domain;

public class BabbleMessage {
    private String sender;
    private String recipient;
    private String text;

    public BabbleMessage(String sender, String recipient, String text){
        this.setSender(sender);
        this.setRecipient(recipient);
        this.setText(text);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package de.unidue.inf.is.domain;


import org.apache.commons.net.ntp.TimeStamp;

public final class Babble {
    private int id;
    private String text;
    private TimeStamp created;
    private String creator;


    public Babble(String text, String creator){
       this.setText(text);
       this.setCreator(creator);
    }

    public Babble(int id, String text, TimeStamp created, String creator){
        this.setId(id);
        this.setText(text);
        this.setCreated(created);
        this.setCreator(creator);
    }


    public String getText() {

        return text;
    }

    public void setText(String text) throws LengthException{
        if(text.length()> 280)
            throw new LengthException();
        else
            this.text = text;
    }

    public String getCreator() {

        return creator;
    }

    public void setCreator(String creator) {

        this.creator = creator;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TimeStamp getCreated() {
        return created;
    }

    public void setCreated(TimeStamp created) {
        this.created = created;
    }
}

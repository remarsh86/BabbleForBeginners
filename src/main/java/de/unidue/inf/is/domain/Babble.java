package de.unidue.inf.is.domain;


import java.sql.Timestamp;

public final class Babble {
    private int id;
    private String text;
    private Timestamp created;
    private String creator;

    public Babble(){ }

    public Babble(String text, String creator){
       this.setText(text);
       this.setCreator(creator);
    }

    public Babble(int id, String text, Timestamp created, String creator){
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}

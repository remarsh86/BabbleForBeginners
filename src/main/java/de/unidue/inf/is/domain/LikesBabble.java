package de.unidue.inf.is.domain;

import java.sql.Timestamp;

public final class LikesBabble {
    private String username;
    private int babble;
    private String type;
    private Timestamp created;

    public LikesBabble(String username, int babble, String type){
        this.setUsername(username);
        this.setBabble(babble);
        this.setType(type);
    }

    public LikesBabble(){}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBabble() {
        return babble;
    }

    public void setBabble(int babble) {
        this.babble = babble;
    }

    public String getType() {
        return type;
    }

    public void setType(String like) {
        this.type = like;
    }

    public void setCreated(Timestamp created) { this.created = created;}

    public Timestamp getCreated(){ return this.created;}
}

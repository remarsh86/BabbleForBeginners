package de.unidue.inf.is.domain;

import java.sql.Timestamp;

public final class ReBabble {
    private String username;
    private int babbleNr;
    private Timestamp created;

    public ReBabble(String username, int babble, Timestamp timestamp){
        this.setUsername(username);
        this.setBabbleNummer(babble);
        this.setCreated(timestamp);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBabbleNummer() {
        return babbleNr;
    }

    public void setBabbleNummer(int babbleNr) {
        this.babbleNr = babbleNr;
    }

    public void setCreated(Timestamp timestamp) {
        this.created = timestamp;
    }

    public Timestamp getCreated(){
        return created;
    }
}

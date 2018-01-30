package de.unidue.inf.is.domain;

public final class ReBabble {
    private String username;
    private int babbleNr;

    public ReBabble(String username, int babble){
        this.setUsername(username);
        this.setBabbleNummer(babble);
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
}

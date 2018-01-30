package de.unidue.inf.is.domain;

public class Follows {
    private String follower;
    private String folowee;

    public Follows(String follower, String followee){
        this.setFollower(follower);
        this.setFolowee(followee);
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFolowee() {
        return folowee;
    }

    public void setFolowee(String folowee) {
        this.folowee = folowee;
    }
}

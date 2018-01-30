package de.unidue.inf.is.domain;

public final class LikesBabble {
    private String username;
    private Babble babble;
    private String like;

    public LikesBabble(String username, Babble babble, String like){
        this.setUsername(username);
        this.setBabble(babble);
        this.setLike(like);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Babble getBabble() {
        return babble;
    }

    public void setBabble(Babble babble) {
        this.babble = babble;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}

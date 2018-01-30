package de.unidue.inf.is.domain;

public class Blocks {
    private String blocker;
    private String blockee;

    public Blocks(String blocker, String blockee){
        this.setBlocker(blocker);
        this.setBlockee(blockee);
    }

    public String getBlocker() {
        return blocker;
    }

    public void setBlocker(String blocker) {
        this.blocker = blocker;
    }

    public String getBlockee() {
        return blockee;
    }

    public void setBlockee(String blockee) {
        this.blockee = blockee;
    }
}

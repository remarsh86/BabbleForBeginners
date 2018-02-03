package de.unidue.inf.is.domain;

public class Blocks {
    private String blocker;
    private String blockee;
    private String reason;

    public Blocks(String blocker, String blockee, String reason){
        this.setBlocker(blocker);
        this.setBlockee(blockee);
        this.setReason(reason);

    }

    public Blocks(){};

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

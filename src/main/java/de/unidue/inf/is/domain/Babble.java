package de.unidue.inf.is.domain;


import sun.tools.tree.LengthExpression;

public final class Babble {
    private String text;
    private String creator;

    public Babble(String text, String creator){
       this.setText(text);
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
}

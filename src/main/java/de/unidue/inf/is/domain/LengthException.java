package de.unidue.inf.is.domain;


public class LengthException extends RuntimeException{

    public LengthException(){
        super("Nur 280 Zeichen erlaubt");
    }

}

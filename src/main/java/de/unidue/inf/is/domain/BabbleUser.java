package de.unidue.inf.is.domain;

public final class BabbleUser {
    private String username;
    private String name;
    private String status;
    private String foto = "/Users/rebeccamarsh/Desktop/CharlieinGrass.jpg";


    public BabbleUser(){ }

    public BabbleUser(String username, String name, String status, String foto){
        this.setUsername(username);
        this.setName(name);
        this.setStatus(status);
        this.setFoto(foto);
    }


    public BabbleUser(String username, String name, String status){
        this.setUsername(username);
        this.setName(name);
        this.setStatus(status);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

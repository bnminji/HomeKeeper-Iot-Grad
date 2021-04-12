package org.techtown.led;

public class Intruder {
    private String image;
    private String date;

    public Intruder(){

    }

    public Intruder(String image, String date) {
        this.image = image;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package app.my.otpverification;

import android.content.Intent;

public class User {

    public User() {
    }

    String Name;
    String Image;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User(String name, String image, String pno, String bio) {
        Name = name;
        Image = image;
        this.pno = pno;
        this.bio = bio;
    }

    String pno;
    String bio;


}

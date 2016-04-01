package com.sam.elderwatch;

/**
 * Created by SAM on 1/4/2016.
 */
public class Contact {
    private String name, phone, imageURL;

    public Contact() {
        this.name = "";
        this.phone = "";
        this.imageURL = "";
    }

    public Contact(String name, String phone, String imageURL) {
        this.name = name;
        this.phone = phone;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

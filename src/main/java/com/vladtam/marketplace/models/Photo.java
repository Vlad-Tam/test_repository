package com.vladtam.marketplace.models;

public class Photo {
    private Integer id;
    private byte[] imageData;

    public Photo(){}

    public Photo(Integer id, byte[] imageData) {
        this.id = id;
        this.imageData = imageData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}

package com.java8.methodreference;


public class Bicycle {
    private String brand;
    private int frameSize;

    public Bicycle() {
    }

    public Bicycle(String brand) {
        this.brand = brand;
        this.frameSize = 0;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getFrameSize() {
        return this.frameSize;
    }

    public void setFrameSize(Integer frameSize) {
        this.frameSize = frameSize;
    }

    @Override
    public String toString() {
        return this.brand + ": " + this.frameSize;
    }

}

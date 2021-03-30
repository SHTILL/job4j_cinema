package ru.job4j.model;

public class Seat {
    private boolean busy = false;
    private int price;

    public void setBusy() {
        this.busy = true;
    }

    public boolean getBusy() {
        return busy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
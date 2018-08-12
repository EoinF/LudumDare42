package com.github.eoinf.game;

public class Resource {
    public int total;
    public int used;
    public int delta;

    public Resource(int startingAmount) {
        this.total = startingAmount;
        this.used = 0;
        this.delta = 0;
    }

    public int amountAvailable() {
        return total - used;
    }
}

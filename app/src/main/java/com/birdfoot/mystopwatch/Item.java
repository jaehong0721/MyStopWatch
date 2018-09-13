package com.birdfoot.mystopwatch;


public class Item {
    private String order;
    private String recordedTime;
    private String elapsedTime;

    private long time;

    public Item(long time, String order, String recordedTime, String elapsedTime) {
        this.time = time;
        this.order = order;
        this.recordedTime = recordedTime;
        this.elapsedTime = elapsedTime;
    }

    public long getTime() {
        return time;
    }

    public String getOrder() {
        return order;
    }

    public String getRecordedTime() {
        return recordedTime;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }
}

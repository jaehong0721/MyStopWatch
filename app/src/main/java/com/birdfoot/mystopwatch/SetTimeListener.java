package com.birdfoot.mystopwatch;


public interface SetTimeListener {
    void onStartTime(long time);

    void onRecordTime(long time);

    void onResetTime();
}

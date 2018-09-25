package com.birdfoot.mystopwatch;


import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class MainViewModel extends ViewModel implements SetTimeListener {

    private Timer timer = new Timer(this);

    private long nowTime;

    private ArrayList<Item> recordedTimes = new ArrayList<>();

    private UpdateTimeUiListener listener;

    @Override public void onSetTime(long time) {
        nowTime = time;
        listener.onUpdateNowTimeUi();
    }

    @Override public void onRecordTime(long time) {

        String formattedRecordingTime = getFormattedTimeString(time);

        long elapsedTime = recordedTimes.size() != 0 ? time - recordedTimes.get(0).getTime() : time;
        String formattedElapsedTime = getFormattedTimeString(elapsedTime);

        String order = Integer.toString(recordedTimes.size()+1);

        Item item = new Item(time, order, formattedRecordingTime, formattedElapsedTime);

        recordedTimes.add(0,item);

        listener.onUpdateRecordedTimeUi();
    }

    @Override public void onResetTime() {
        nowTime = 0;
        recordedTimes.clear();

        listener.onResetTimeUi();
    }

    @Override protected void onCleared() {
        super.onCleared();
        listener = null;
    }

    public void setUpdateTimeUiListener(UpdateTimeUiListener listener) {
        this.listener = listener;
    }

    public ArrayList<Item> getRecordedTimes() {
        return recordedTimes;
    }

    public String getFormattedNowTime() {
        return getFormattedTimeString(nowTime);
    }

    public void quitTime() {
        timer.quitTime();
    }

    public void startTime() {
        timer.startTime();
    }

    public void stopTime() {
        timer.stopTime();
    }

    public void recordTime() {
        timer.recordTime();
    }

    public void continueTime() {
        timer.continueTime();
    }

    public void resetTime() {
        timer.resetTime();
    }

    private String getFormattedTimeString(long time) {
        return String.format(Locale.KOREA,"%02d:%02d.%02d", time/Timer.MIN, (time/Timer.SEC)%60, (time/Timer.CSEC)%100);
    }
}

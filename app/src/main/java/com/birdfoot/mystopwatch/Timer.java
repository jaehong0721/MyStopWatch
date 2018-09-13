package com.birdfoot.mystopwatch;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class Timer {

    private long baseTime;
    private long nowTime;
    private long pauseTime;

    private SetTimeListener listener;

    private static final int SEND_TIME = 0;

    public static final long CSEC = 10000000;
    public static final long SEC = 100 * CSEC;
    public static final long MIN = 60 * SEC;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override public void handleMessage(Message msg) {

            nowTime = System.nanoTime();

            listener.onSetTime(nowTime-baseTime);

            handler.sendEmptyMessageDelayed(SEND_TIME, 10);
        }
    };

    public Timer(SetTimeListener listener) {
        this.listener = listener;
    }

    public void startTime() {
        Log.d("Timer", "onStart");
        baseTime = System.nanoTime();
        handler.sendEmptyMessage(SEND_TIME);
    }

    public void stopTime() {
        Log.d("Timer", "onStop");
        handler.removeMessages(SEND_TIME);
        pauseTime = System.nanoTime();
    }

    public void recordTime() {
        Log.d("Timer", "onRecord");
        listener.onRecordTime(nowTime-baseTime);
    }

    public void continueTime() {
        Log.d("Timer", "onContinue");
        nowTime = System.nanoTime();
        baseTime += (nowTime - pauseTime);
        handler.sendEmptyMessage(SEND_TIME);
    }

    public void quitTime() {
        handler.removeMessages(SEND_TIME);
    }
}

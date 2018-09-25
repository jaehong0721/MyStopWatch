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

    /*
    1/100초마다 시간을 셋팅해주기 위한 핸들러
    참고 :https://academy.realm.io/kr/posts/android-thread-looper-handler/
    */
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override public void handleMessage(Message msg) {

            nowTime = System.nanoTime();

            // 시간 UI 업데이트
            listener.onStartTime(nowTime-baseTime);

            // 1/100초마다 수행
            handler.sendEmptyMessageDelayed(SEND_TIME, 10);
        }
    };

    public Timer(SetTimeListener listener) {
        this.listener = listener;
    }

    public void startTime() {
        Log.d("Timer", "onStart");

        // 시작할때 기준이 될 baseTime 설정
        baseTime = System.nanoTime();

        // 핸들러 동작 수행
        handler.sendEmptyMessage(SEND_TIME);
    }

    public void stopTime() {
        Log.d("Timer", "onStop");

        // 메시지큐에서 시간 처리 메시지들을 제거함으로써 시간 UI 업데이트를 정지
        handler.removeMessages(SEND_TIME);

        // 정지한 시간을 저장
        pauseTime = System.nanoTime();
    }

    public void recordTime() {
        Log.d("Timer", "onRecord");

        // 시간 기록 UI 업데이트
        listener.onRecordTime(nowTime-baseTime);
    }

    public void continueTime() {
        Log.d("Timer", "onContinue");
        nowTime = System.nanoTime();

        // 정지했다가 재시작할때 정지를 누르고 흐른 시간을 빼주기 위해 기준 시간을 수정
        baseTime += (nowTime - pauseTime);
        handler.sendEmptyMessage(SEND_TIME);
    }

    public void quitTime() {
        handler.removeMessages(SEND_TIME);
    }

    public void resetTime() {
        handler.removeMessages(SEND_TIME);

        baseTime = 0;
        nowTime = 0;
        pauseTime = 0;

        listener.onResetTime();
    }
}
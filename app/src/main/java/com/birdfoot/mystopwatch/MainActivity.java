package com.birdfoot.mystopwatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SetTimeListener{

    private Timer timer;

    private TextView tvTime;
    private TextView tvRecord;

    private Button btnStart;

    private View buttonContainerInRun;
    private View buttonContainerInStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer(this);

        tvTime = findViewById(R.id.tvTime);
        tvRecord = findViewById(R.id.tvRecord);

        btnStart = findViewById(R.id.btnStart);

        buttonContainerInRun = findViewById(R.id.button_container_in_run);
        buttonContainerInStop = findViewById(R.id.button_container_in_stop);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.quitTime();
    }

    @Override
    public void onSetTime(long time) {
        tvTime.setText(String.format("%02d:%02d.%02d", time/Timer.MIN, (time/Timer.SEC)%60, (time/Timer.CSEC)%100));
    }

    @Override
    public void onRecordTime(long time) {
        StringBuilder stringBuilder = new StringBuilder(tvRecord.getText());
        tvRecord.setText(stringBuilder.append(String.format("\n%02d:%02d.%02d", time/Timer.MIN, (time/Timer.SEC)%60, (time/Timer.CSEC)%100)));
    }

    public void onClickStart(View v) {
        timer.startTime();

        btnStart.setVisibility(View.GONE);
        buttonContainerInRun.setVisibility(View.VISIBLE);
    }

    public void onClickStop(View v) {
        timer.stopTime();

        buttonContainerInRun.setVisibility(View.GONE);
        buttonContainerInStop.setVisibility(View.VISIBLE);
    }

    public void onClickRecord(View v) {
        timer.recordTime();
    }

    public void onClickContinue(View v) {
        timer.continueTime();

        buttonContainerInStop.setVisibility(View.GONE);
        buttonContainerInRun.setVisibility(View.VISIBLE);
    }

    public void onClickReset(View v) {
        timer.quitTime();

        tvTime.setText("00:00.00");
        tvRecord.setText("");
        buttonContainerInStop.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);
    }
}

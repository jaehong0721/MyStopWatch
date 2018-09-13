package com.birdfoot.mystopwatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SetTimeListener{

    private TextView tvTime;

    private RecyclerView rvRecord;

    private Button btnStart;

    private View buttonContainerInRun;
    private View buttonContainerInStop;

    private Timer timer;

    private RvRecordAdapter adapter;
    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);

        rvRecord = findViewById(R.id.rvRecord);

        btnStart = findViewById(R.id.btnStart);

        buttonContainerInRun = findViewById(R.id.button_container_in_run);
        buttonContainerInStop = findViewById(R.id.button_container_in_stop);

        timer = new Timer(this);

        items = new ArrayList<>();
        adapter = new RvRecordAdapter(items);

        rvRecord.setAdapter(adapter);
        rvRecord.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.quitTime();
    }

    @Override
    public void onSetTime(long time) {
        tvTime.setText(getFormattedTimeString(time));
    }

    @Override
    public void onRecordTime(long time) {
        String recordedTime = getFormattedTimeString(time);

        long elapsedTime = items.size() != 0 ? time - items.get(0).getTime() : time;

        Item item = new Item(time, Integer.toString(items.size()+1), recordedTime, getFormattedTimeString(elapsedTime));
        items.add(0,item);

        adapter.notifyItemInserted(0);
        rvRecord.scrollToPosition(0);
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

        tvTime.setText(getFormattedTimeString(0L));

        items.clear();
        adapter.notifyDataSetChanged();

        buttonContainerInStop.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);
    }

    private String getFormattedTimeString(long time) {
        return String.format(Locale.KOREA,"%02d:%02d.%02d", time/Timer.MIN, (time/Timer.SEC)%60, (time/Timer.CSEC)%100);
    }
}

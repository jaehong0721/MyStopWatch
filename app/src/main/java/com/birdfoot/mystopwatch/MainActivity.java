package com.birdfoot.mystopwatch;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements UpdateTimeUiListener{

    private static final int INITIAL_STATE = 0;
    private static final int RUNNING_STATE = 1;
    private static final int STOP_STATE = 2;

    private int state;

    private TextView tvTime;

    private RecyclerView rvRecord;
    private RvRecordAdapter adapter;

    private Button btnStart;

    // '정지', '기록' 버튼을 담고 있는 컨테이너뷰
    private View buttonContainerInRun;

    // '계속', '초기화' 버튼을 담고 있는 컨테이너뷰
    private View buttonContainerInStop;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setUpdateTimeUiListener(this);

        tvTime = findViewById(R.id.tvTime);

        rvRecord = findViewById(R.id.rvRecord);

        btnStart = findViewById(R.id.btnStart);

        buttonContainerInRun = findViewById(R.id.button_container_in_run);
        buttonContainerInStop = findViewById(R.id.button_container_in_stop);

        adapter = new RvRecordAdapter(mainViewModel.getRecordedTimes());
        rvRecord.setAdapter(adapter);
        rvRecord.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        state = savedInstanceState.getInt("state");

        switch (state) {
            case RUNNING_STATE :
                mainViewModel.continueTime();
                setButtonInRun();
                break;

            case STOP_STATE :
                tvTime.setText(mainViewModel.getFormattedNowTime());
                setButtonInStop();
                break;
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("state", state);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //앱이 종료될 때, 타이머 기능도 off
        mainViewModel.quitTime();
    }

    @Override public void onUpdateNowTimeUi() {
        tvTime.setText(mainViewModel.getFormattedNowTime());
    }

    @Override public void onUpdateRecordedTimeUi() {
        adapter.notifyItemInserted(0);
        rvRecord.scrollToPosition(0);
    }

    @Override public void onResetTimeUi() {
        // UI를 초기 상태로 리셋
        tvTime.setText(mainViewModel.getFormattedNowTime());

        adapter.notifyDataSetChanged();
    }

    public void onClickStart(View v) {
        state = RUNNING_STATE;

        mainViewModel.startTime();

        //스탑 워치 동작 유무에 따라 버튼을 교체
        setButtonInRun();
    }

    public void onClickStop(View v) {
        state = STOP_STATE;

        mainViewModel.stopTime();

        setButtonInStop();
    }

    public void onClickRecord(View v) {
        mainViewModel.recordTime();
    }

    public void onClickContinue(View v) {
        state = RUNNING_STATE;

        mainViewModel.continueTime();

        setButtonInRun();
    }

    public void onClickReset(View v) {
        state = INITIAL_STATE;

        mainViewModel.resetTime();

        setInitialButton();
    }

    private void setInitialButton() {
        btnStart.setVisibility(View.VISIBLE);
        buttonContainerInStop.setVisibility(View.GONE);
        buttonContainerInRun.setVisibility(View.GONE);
    }

    private void setButtonInStop() {
        btnStart.setVisibility(View.GONE);
        buttonContainerInStop.setVisibility(View.VISIBLE);
        buttonContainerInRun.setVisibility(View.GONE);
    }

    private void setButtonInRun() {
        btnStart.setVisibility(View.GONE);
        buttonContainerInStop.setVisibility(View.GONE);
        buttonContainerInRun.setVisibility(View.VISIBLE);
    }
}

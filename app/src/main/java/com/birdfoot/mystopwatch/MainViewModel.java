package com.birdfoot.mystopwatch;


import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Timer timer;


    public Timer getTimer(SetTimeListener listener) {
        if(timer == null)
            timer = new Timer(listener);
        timer.setListener(listener);
        return timer;
    }
}

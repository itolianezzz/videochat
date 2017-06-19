package ru.spb.itolia.videochat.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import ru.spb.itolia.videochat.presenter.MainPresenter;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public class MainActivityRetainFragment extends Fragment {
    private static final String LOG_TAG = MainActivityRetainFragment.class.getSimpleName();
    private MainPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void retainPresenter(MainPresenter presenter) {
        Log.d(LOG_TAG, "retainPresenter()");
        this.presenter = presenter;
    }

    public MainPresenter getPresenter() {
        Log.d(LOG_TAG, "getPresenter()");
        return presenter;
    }
}
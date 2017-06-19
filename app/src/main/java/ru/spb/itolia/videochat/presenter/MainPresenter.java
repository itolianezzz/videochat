package ru.spb.itolia.videochat.presenter;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.spb.itolia.videochat.model.IModel;
import ru.spb.itolia.videochat.model.MainModel;
import ru.spb.itolia.videochat.view.IView;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public class MainPresenter implements IPresenter {
    private static final String LOG_TAG = MainPresenter.class.getSimpleName();
    private final IModel model;
    //private Session mSession;
    private IView view;
    private Disposable disposable;


    //private static String SESSION_ID = "1_MX40NTg5MzI1Mn5-MTQ5NzUyNDYwODMwNH5mc1ExV1psT1ZUQ0VwU2lLb09YeVpwcTh-fg";
    //private static String TOKEN = "T1==cGFydG5lcl9pZD00NTg5MzI1MiZzZGtfdmVyc2lvbj1kZWJ1Z2dlciZzaWc9ZDI5NTk3MTE0ZmQxZmNlMjIzNTIzNTk0NzhiODEzN2YyMzgyNGZjMDpzZXNzaW9uX2lkPTFfTVg0ME5UZzVNekkxTW41LU1UUTVOelV5TkRZd09ETXdOSDVtYzFFeFYxcHNUMVpVUTBWd1UybExiMDlZZVZwd2NUaC1mZyZjcmVhdGVfdGltZT0xNDk3NTI0NjA4JnJvbGU9bW9kZXJhdG9yJm5vbmNlPTE0OTc1MjQ2MDguMzI5NjE5MTg0NjE1NCZleHBpcmVfdGltZT0xNTAwMTE2NjA4";


    public MainPresenter() {
        model = new MainModel();
    }


    @Override
    public void findPartner() {
        Log.d(LOG_TAG, "findPartner()");
        disposable = model.getSession()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(view::sessionConnected);
    }

    @Override
    public void detachView() {
        Log.d(LOG_TAG, "detachView()");
        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        view = null;
    }

    @Override
    public void attachView(IView view) {
        Log.d(LOG_TAG, "attachView()");
        this.view = view;
    }

    @Override
    public void discardSession(String sessionId) {
        model.discardSession(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError( e -> Log.e(LOG_TAG, "Error! " + e))
                .subscribe();
    }


}

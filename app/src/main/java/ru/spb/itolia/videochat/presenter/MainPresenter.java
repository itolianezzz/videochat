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
    private IView view;
    private Disposable disposable;

    public MainPresenter() {
        model = new MainModel();
    }

    @Override
    public void findPartner() {
        Log.d(LOG_TAG, "findPartner()");
        disposable = model.getSession()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(view::sessionConnected, view::onError);
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

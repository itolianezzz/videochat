package ru.spb.itolia.videochat.presenter;

import ru.spb.itolia.videochat.view.IView;
import ru.spb.itolia.videochat.view.MainActivity;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public interface IPresenter {
    void attachView(IView view);
    void detachView();
    void findPartner();
    void discardSession(String sessionId);
}

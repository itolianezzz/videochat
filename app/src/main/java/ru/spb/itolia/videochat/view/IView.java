package ru.spb.itolia.videochat.view;

import ru.spb.itolia.videochat.model.SessionInfo;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public interface IView {
    void sessionConnected(SessionInfo sessionInfo);
    void onError(Throwable throwable);
}

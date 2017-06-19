package ru.spb.itolia.videochat.model;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public interface IModel {
    public Single<SessionInfo> getSession();
    public Completable discardSession(String sessionId);
}

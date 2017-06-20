package ru.spb.itolia.videochat.model;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public interface IModel {
    Single<SessionInfo> getSession();
    Completable discardSession(String sessionId);
}

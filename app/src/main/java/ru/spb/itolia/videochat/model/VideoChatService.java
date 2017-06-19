package ru.spb.itolia.videochat.model;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public interface VideoChatService {
    @GET("getsession.php")
    Single<SessionInfo> getSession();

    @GET("discardsession.php")
    Completable discardSession(@Query("session_id") String session_id);

}

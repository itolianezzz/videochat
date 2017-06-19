package ru.spb.itolia.videochat.model;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by itolianezzz on 16.06.2017.
 */

public class MainModelMock implements IModel {
    private static String SESSION_ID = "1_MX40NTg5MzI1Mn5-MTQ5NzUyNDYwODMwNH5mc1ExV1psT1ZUQ0VwU2lLb09YeVpwcTh-fg";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NTg5MzI1MiZzZGtfdmVyc2lvbj1kZWJ1Z2dlciZzaWc9ZDI5NTk3MTE0ZmQxZmNlMjIzNTIzNTk0NzhiODEzN2YyMzgyNGZjMDpzZXNzaW9uX2lkPTFfTVg0ME5UZzVNekkxTW41LU1UUTVOelV5TkRZd09ETXdOSDVtYzFFeFYxcHNUMVpVUTBWd1UybExiMDlZZVZwd2NUaC1mZyZjcmVhdGVfdGltZT0xNDk3NTI0NjA4JnJvbGU9bW9kZXJhdG9yJm5vbmNlPTE0OTc1MjQ2MDguMzI5NjE5MTg0NjE1NCZleHBpcmVfdGltZT0xNTAwMTE2NjA4";


    @Override
    public Single<SessionInfo> getSession() {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setSessionId(SESSION_ID);
        sessionInfo.setToken(TOKEN);
        return Single.just(sessionInfo);

    }

    @Override
    public Completable discardSession(String sessionId) {
        return null;
    }
}

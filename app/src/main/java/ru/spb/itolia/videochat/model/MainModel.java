package ru.spb.itolia.videochat.model;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.spb.itolia.videochat.BuildConfig;


/**
 * Created by itolianezzz on 16.06.2017.
 */

public class MainModel implements IModel {
    private final VideoChatService service;

    public MainModel() {
        //Un-comment if detail retrofit logging nedeed
/*        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.followRedirects(true);
        httpClient.addInterceptor(logging);*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                //.client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(VideoChatService.class);
    }


    @Override
    public Single<SessionInfo> getSession() {
        return service.getSession();
    }

    @Override
    public Completable discardSession(String sessionId) {
        return service.discardSession(sessionId);
    }


}

package ru.spb.itolia.videochat.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;
import android.support.annotation.NonNull;
import android.Manifest;
import android.widget.FrameLayout;
import android.widget.Toast;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import ru.spb.itolia.videochat.BuildConfig;
import ru.spb.itolia.videochat.R;
import ru.spb.itolia.videochat.model.SessionInfo;
import ru.spb.itolia.videochat.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IView, Session.SessionListener {
    private static final String TAG_RETAIN_FRAGMENT = "retain_fragment";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int RC_VIDEO_APP_PERM = 124;



    private Session mSession;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private MainActivityRetainFragment mRetainFragment;
    private MainPresenter presenter;
    private LottieAnimationView pbSubscriber;
    private LottieAnimationView pbPublisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRetainFragment();
        initPresenter();
        initViews();
        requestPermissions();
    }

    private void initRetainFragment() {
        FragmentManager fm = getSupportFragmentManager();
        mRetainFragment = (MainActivityRetainFragment) fm.findFragmentByTag(TAG_RETAIN_FRAGMENT);
        if (mRetainFragment == null) {
            mRetainFragment = new MainActivityRetainFragment();
            fm.beginTransaction().add(mRetainFragment, TAG_RETAIN_FRAGMENT).commit();
        }
    }

    private void initPresenter() {
        presenter = mRetainFragment.getPresenter();
        mRetainFragment.retainPresenter(null);
        if (presenter == null) {
            presenter = new MainPresenter();
        }
        presenter.attachView(this);
    }

    private void initViews() {
        mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
        mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);
        pbSubscriber = (LottieAnimationView ) findViewById(R.id.subscriber_loader);
        pbPublisher = (LottieAnimationView ) findViewById(R.id.publsiher_loader);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, getResources().getString(R.string.request_permissions), RC_VIDEO_APP_PERM, perms);
        }
    }


    @Override
    public void sessionConnected(SessionInfo sessionInfo) {
        mSession = new Session.Builder(this, BuildConfig.API_KEY, sessionInfo.getSessionId()).build();
        mSession.setSessionListener(this);
        mSession.connect(sessionInfo.getToken());
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e(LOG_TAG, "Error! " + throwable);
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void sessionEnded(){
        if (mSubscriber != null) {
            mSession.unsubscribe(mSubscriber);
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
        if(mPublisher != null){
            mSession.unpublish(mPublisher);
            mPublisher = null;
            mPublisherViewContainer.removeAllViews();
        }
        if(mSession != null) {
            presenter.discardSession(mSession.getSessionId());
            mSession.disconnect();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
        Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    // SessionListener methods
    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");
        Toast.makeText(this, getString(R.string.session_created), Toast.LENGTH_SHORT).show();
        mPublisher = new Publisher.Builder(this).build();
        mPublisherViewContainer.removeAllViews();
        mPublisherViewContainer.addView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");
        mSubscriberViewContainer.removeAllViews();
        Toast.makeText(this, getString(R.string.partner_found), Toast.LENGTH_SHORT).show();
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
        sessionEnded();
        Toast.makeText(this, getResources().getString(R.string.partner_disconnected), Toast.LENGTH_SHORT).show();
        presenter.findPartner();
        mSubscriberViewContainer.addView(pbSubscriber);
        mPublisherViewContainer.addView(pbPublisher);
    }

    //Activity lifecycle

    @Override
    protected void onResume() {
        super.onResume();
        presenter.findPartner();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause()");
        sessionEnded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isFinishing()) {
            mRetainFragment.retainPresenter(presenter);
            return;
        }
        presenter.detachView();
        presenter = null;
    }


}

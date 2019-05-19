package com.welfarerobotics.welfareapplcation.core.youtube;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.welfarerobotics.welfareapplcation.R;
import com.welfarerobotics.welfareapplcation.entity.ServerCache;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //액티비티를 다이얼로그 형식으로 표시
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_youtube);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.0f; //메인액티비티 투명도 조절
        layoutParams.alpha = 0.0f;
        getWindow().setAttributes(layoutParams);

        Display dp = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (dp.getWidth() * 1.0);
        int height = (int) (dp.getHeight() * 1.0);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        Intent youtubeIntent = getIntent();
        url = youtubeIntent.getExtras().getString("url");

        System.out.println("youtube액티비티의 url " + url);
        System.out.println("구글api키 " + ServerCache.getInstance().getYoutubekey());
        playerView = findViewById(R.id.YoutubeView);

        playerView.initialize(ServerCache.getInstance().getYoutubekey(), this);

    }

    //YouTubePlayer.OnInitializedListener
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.loadVideo(url); //유튜브 비디오 ID(URL의 "v=" 뒤에 표시됨)
            youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                finish();
                break;
            case MotionEvent.ACTION_UP:    //화면을 터치했다 땠을때
                break;
            case MotionEvent.ACTION_MOVE:    //화면을 터치하고 이동할때
                break;
        }
        return super.onTouchEvent(event);
    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {
            Toast.makeText(getApplicationContext(), "화면을 누르면 재생을 종료합니다", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoEnded() {
            finish();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
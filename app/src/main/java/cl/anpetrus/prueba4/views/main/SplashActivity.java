package cl.anpetrus.prueba4.views.main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import cl.anpetrus.prueba4.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_splash);

            View view = findViewById(R.id.root);


            VideoView videoView = (VideoView) findViewById(R.id.videoView);
            videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.marvel_video);
            videoView.setVideoURI(uri);
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    jump();
                }
            });
            videoView.start();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jump();
                }
            });
        } catch (Exception e) {
            jump();
        }
    }

    private void jump() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

}

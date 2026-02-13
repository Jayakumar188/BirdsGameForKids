package com.example.birdgameforkids;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Animation animation;
    ImageView innocentBird;
    ImageView enemy1;
    ImageView enemy2;
    ImageView enemy3;
    ImageView coin;
    ImageView volume;
    MediaPlayer mediaPlayer;
    Button start;
    boolean music_staus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onNewIntent called");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        music_staus=true;
        animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        innocentBird =findViewById(R.id.innocent_bird);
        enemy1 = findViewById(R.id.enemy4_);
        enemy2 = findViewById(R.id.enemy2_);
        enemy3 = findViewById(R.id.enemy3_);
        coin = findViewById(R.id.coin);
        volume=findViewById(R.id.music);
        start=findViewById(R.id.startButton);


        innocentBird.setAnimation(animation);
        enemy1.setAnimation(animation);
        enemy2.setAnimation(animation);
        enemy3.setAnimation(animation);
        coin.setAnimation(animation);

        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        Intent intent = getIntent();
        music_staus = intent.getBooleanExtra("isMusic", false);

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music_staus){
                    music_staus=false;
                    mediaPlayer.setVolume(0,0);
                    volume.setImageResource(R.drawable.music_mute);
                }
                else{
                    music_staus=true;
                    mediaPlayer.start();
                    mediaPlayer.setVolume(1,1);
                    volume.setImageResource(R.drawable.volume_image);
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Game_activity.class);
                intent.putExtra("isMusic",music_staus);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(music_staus)
        {
            mediaPlayer.start();
            volume.setImageResource(R.drawable.volume_image);
        }else{
            mediaPlayer.pause();
            volume.setImageResource(R.drawable.music_mute);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            mediaPlayer.stop(); // Stop music when the app is closed
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
    @Override
    public void onBackPressed() {
        // Close all activities and exit the app
        super.onBackPressed();
        finishAffinity(); // Close all activities and exit the app
    }

}
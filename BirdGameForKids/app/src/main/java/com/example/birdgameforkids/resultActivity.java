package com.example.birdgameforkids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class resultActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int score=getIntent().getIntExtra("score",0);
        TextView scoreText=findViewById(R.id.score_text);
        scoreText.setText(""+score);

        mediaPlayer = MediaPlayer.create(resultActivity.this,R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Intent intent = getIntent();
        boolean isMusic = intent.getBooleanExtra("isMusic", false); // Default value is false

        if (!isMusic) {
            mediaPlayer.setVolume(0,0);
        } else {
            mediaPlayer.setVolume(1,1);
        }

        sharedPreferences=this.getSharedPreferences("score", Context.MODE_PRIVATE);
        int highest_score=sharedPreferences.getInt("highest_score",0);
        if(score>highest_score) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest_score", score);
            editor.apply();
            TextView highest_score_text=findViewById(R.id.highest_score_text);
            highest_score_text.setText("Highest Score : "+score);
        }else{
            TextView highest_score_text=findViewById(R.id.highest_score_text);
            highest_score_text.setText("Highest Score : "+highest_score);
        }

        ImageView playAgain=findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(resultActivity.this, Game_activity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView exit=findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        ImageView back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(resultActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("isMusic", isMusic);
                startActivity(intent);
                finish();
            }
        });

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
        // Pause the music
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
}
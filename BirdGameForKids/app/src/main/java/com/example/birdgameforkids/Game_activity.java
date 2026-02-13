package com.example.birdgameforkids;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Game_activity extends AppCompatActivity {

    ImageView enemy1,enemy2,enemy3,coin1,coin2,coin_connection,heart1,heart2,heart3,innocent_bird;
    TextView scoreText,start;
    ConstraintLayout page;
    boolean touchControl=false;
    boolean beginControl=false;
    Runnable runnable;
    Handler handler;
    int birdX,enemy1X,enemy2X,enemy3X,coin1X,coin2X;
    int birdY,enemy1Y,enemy2Y,enemy3Y,coin1Y,coin2Y;
    int screenWidth,screenHeight;
    int right=3;
    int score=0;
    MediaPlayer mediaPlayer,mediaPlayer2,mediaPlayer3;
    boolean isMusic=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        innocent_bird=findViewById(R.id.innocent_bird);
        enemy1=findViewById(R.id.enemy4_);
        enemy2=findViewById(R.id.enemy2_);
        enemy3=findViewById(R.id.enemy3_);
        coin1=findViewById(R.id.coin1);
        coin2=findViewById(R.id.coin2);
        coin_connection=findViewById(R.id.coin_connection);
        heart1=findViewById(R.id.heart1);
        heart2=findViewById(R.id.heart2);
        heart3=findViewById(R.id.heart3);
        scoreText=findViewById(R.id.coin_connection_text);
        start=findViewById(R.id.start_text);
        page=findViewById(R.id.page_);
        enemy1.setVisibility(View.INVISIBLE);
        enemy2.setVisibility(View.INVISIBLE);
        enemy3.setVisibility(View.INVISIBLE);
        coin1.setVisibility(View.INVISIBLE);
        coin2.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        mediaPlayer = MediaPlayer.create(Game_activity.this,R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        mediaPlayer2=MediaPlayer.create(Game_activity.this,R.raw.bird_collision2);
        mediaPlayer3=MediaPlayer.create(Game_activity.this,R.raw.coin_collision);

        isMusic = intent.getBooleanExtra("isMusic", false); // Default value is false
        System.out.println("is Music is  : "+isMusic);
        if (isMusic) {
            mediaPlayer.setVolume(1,1);
        } else {
            mediaPlayer.setVolume(0,0);
        }


        page.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                start.setVisibility(View.GONE);
                if(!beginControl){
                    beginControl=true;
                    screenWidth=page.getWidth();
                    screenHeight=page.getHeight();
                    birdX=(int)innocent_bird.getX();
                    birdY=(int)innocent_bird.getY();
                    handler=new Handler();
                    runnable=new Runnable() {
                        @Override
                        public void run() {
                            enemyControl();
                            moveToBird();
                            collisionControl();
                        }
                    };
                    handler.post(runnable);
                }else{
                    if(event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        touchControl=true;
                    }
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        touchControl=false;
                    }
                }

                return true;
            }
        });

    }

    public void moveToBird(){
        if(touchControl)
        {
            birdY=birdY-(screenHeight/50);
        }
        else
        {
            birdY=birdY+(screenHeight/50);
        }
        if(birdY<=0)
        {
            birdY=0;
        }
        if(birdY>=(screenHeight-innocent_bird.getHeight()))
        {
            birdY=(screenHeight-innocent_bird.getHeight());
        }
        innocent_bird.setY(birdY);
    }

    public void enemyControl(){
        enemy1.setVisibility(View.VISIBLE);
        enemy2.setVisibility(View.VISIBLE);
        enemy3.setVisibility(View.VISIBLE);
        coin1.setVisibility(View.VISIBLE);
        coin2.setVisibility(View.VISIBLE);

        if(score<200) {
            enemy1X = enemy1X - (screenWidth / 150);
            enemy2X = enemy2X - (screenWidth / 140);
            enemy3X = enemy3X - (screenWidth / 130);
            coin1X = coin1X - (screenWidth / 120);
            coin2X = coin2X - (screenWidth / 110);
        }else if(score >=200 && score<400){
            enemy1X = enemy1X - (screenWidth / 120);
            enemy2X = enemy2X - (screenWidth / 110);
            enemy3X = enemy3X - (screenWidth / 100);
            coin1X = coin1X - (screenWidth / 90);
            coin2X = coin2X - (screenWidth / 80);
        }else if(score >=400 && score<600){
            enemy1X = enemy1X - (screenWidth / 90);
            enemy2X = enemy2X - (screenWidth / 80);
            enemy3X = enemy3X - (screenWidth / 70);
            coin1X = coin1X - (screenWidth / 60);
            coin2X = coin2X - (screenWidth / 50);
        }else{
            enemy1X = enemy1X - (screenWidth / 80);
            enemy2X = enemy2X - (screenWidth / 70);
            enemy3X = enemy3X - (screenWidth / 60);
            coin1X = coin1X - (screenWidth / 50);
            coin2X = coin2X - (screenWidth / 40);
        }

        if(enemy1X<0)
        {
            enemy1X=screenWidth+200;
            enemy1Y=(int)Math.floor(Math.random()*screenHeight);
            if(enemy1Y<=0)
            {
                enemy1Y=0;
            }
            if(enemy1Y>=(screenHeight-enemy1.getHeight()))
            {
                enemy1Y=(screenHeight-enemy1.getHeight());
            }
        }

        if(enemy2X<0)
        {
            enemy2X=screenWidth+200;
            enemy2Y=(int)Math.floor(Math.random()*screenHeight);
            if(enemy2Y<=0)
            {
                enemy2Y=0;
            }
            if(enemy2Y>=(screenHeight-enemy2.getHeight()))
            {
                enemy2Y=(screenHeight-enemy2.getHeight());
            }
        }

        if(enemy3X<0)
        {
            enemy3X=screenWidth+200;
            enemy3Y=(int)Math.floor(Math.random()*screenHeight);
            if(enemy3Y<=0)
            {
                enemy3Y=0;
            }
            if(enemy3Y>=(screenHeight-enemy3.getHeight()))
            {
                enemy3Y=(screenHeight-enemy3.getHeight());
            }
        }

        if(coin1X<0)
        {
            coin1X=screenWidth+200;
            coin1Y=(int)Math.floor(Math.random()*screenHeight);
            if(coin1Y<=0)
            {
                coin1Y=0;
            }
            if(coin1Y>=(screenHeight-coin1.getHeight()))
            {
                coin1Y=(screenHeight-coin1.getHeight());
            }
        }
        if(coin2X<0)
        {
            coin2X=screenWidth+200;
            coin2Y=(int)Math.floor(Math.random()*screenHeight);
            if(coin2Y<=0)
            {
                coin2Y=0;
            }
            if(coin2Y>=(screenHeight-coin2.getHeight()))
            {
                coin2Y=(screenHeight-coin2.getHeight());
            }
        }

        enemy1.setX(enemy1X);
        enemy1.setY(enemy1Y);
        enemy2.setX(enemy2X);
        enemy2.setY(enemy2Y);
        enemy3.setX(enemy3X);
        enemy3.setY(enemy3Y);
        coin1.setX(coin1X);
        coin1.setY(coin1Y);
        coin2.setX(coin2X);
        coin2.setY(coin2Y);
    }

    public void collisionControl()
    {
        int centerEnemy1X=enemy1X+enemy1.getWidth()/2;
        int centerEnemy1Y=enemy1Y+enemy1.getHeight()/2;
        if(centerEnemy1X>=birdX
                && centerEnemy1X<=(birdX+innocent_bird.getWidth())
                && centerEnemy1Y>=birdY
                && centerEnemy1Y<=(birdY+innocent_bird.getHeight()))
        {
            enemy1X=screenWidth+200;
            right--;
            mediaPlayer2.start();
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }

        int centerEnemy2X=enemy2X+enemy2.getWidth()/2;
        int centerEnemy2Y=enemy2Y+enemy2.getHeight()/2;
        if(centerEnemy2X>=birdX
                && centerEnemy2X<=(birdX+innocent_bird.getWidth())
                && centerEnemy2Y>=birdY
                && centerEnemy2Y<=(birdY+innocent_bird.getHeight()))
        {
            enemy2X=screenWidth+200;
            right--;
            mediaPlayer2.start();
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }

        int centerEnemy3X=enemy3X+enemy3.getWidth()/2;
        int centerEnemy3Y=enemy3Y+enemy3.getHeight()/2;
        if(centerEnemy3X>=birdX
                && centerEnemy3X<=(birdX+innocent_bird.getWidth())
                && centerEnemy3Y>=birdY
                && centerEnemy3Y<=(birdY+innocent_bird.getHeight()))
        {
            enemy3X=screenWidth+200;
            right--;
            mediaPlayer2.start();
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }

        int centerCoin1X=coin1X+coin1.getWidth()/2;
        int centerCoin1Y=coin1Y+coin1.getHeight()/2;
        if(centerCoin1X>=birdX
                && centerCoin1X<=(birdX+innocent_bird.getWidth())
                && centerCoin1Y>=birdY
                && centerCoin1Y<=(birdY+innocent_bird.getHeight()))
        {
            coin1X=screenWidth+200;
            score+=10;
            scoreText.setText(""+score);
            if (mediaPlayer3.isPlaying()) {
                mediaPlayer3.seekTo(0); // Reset to the start
            }
            mediaPlayer3.start();
        }

        int centerCoin2X=coin2X+coin2.getWidth()/2;
        int centerCoin2Y=coin2Y+coin2.getHeight()/2;
        if(centerCoin2X>=birdX
                && centerCoin2X<=(birdX+innocent_bird.getWidth())
                && centerCoin2Y>=birdY
                && centerCoin2Y<=(birdY+innocent_bird.getHeight()))
        {
            coin2X=screenWidth+200;
            score+=10;
            scoreText.setText(""+score);
            if (mediaPlayer3.isPlaying()) {
                mediaPlayer3.seekTo(0); // Reset to the start
            }
            mediaPlayer3.start();
        }

        if(right > 0 )
        {
            if(right == 2)
            {
                heart1.setImageResource(R.drawable.heart_plain_icon);
            }
            if(right == 1)
            {
                heart3.setImageResource(R.drawable.heart_plain_icon);
            }
            handler.postDelayed(runnable,20);
        }
//        else if (right>=200)
//        {
//            handler.removeCallbacks(runnable);
//        }
        else if(right == 0){
            handler.removeCallbacks(runnable);
            heart2.setImageResource(R.drawable.heart_plain_icon);
            Intent intent=new Intent(Game_activity.this,resultActivity.class);
            intent.putExtra("score",score);
            intent.putExtra("isMusic",isMusic);
            startActivity(intent);
            finish();
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
        // Stop the handler to pause the game loop
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }

        // Pause the music
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

}
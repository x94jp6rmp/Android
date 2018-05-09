package com.gameusingdynamicfragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "Result";
    private int mTagCount = 0;
    public Fragment fragResult;

    private ImageButton mImgBtnDice;

    private int miCountSet = 0,
            miCountPlayerWin = 0,
            miCountComWin = 0,
            miCountDraw = 0;

    private Button mBtnShowResult1,
            mBtnShowResult2;

    private boolean isDiceRolling = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        mImgBtnDice = (ImageButton) findViewById(R.id.imgBtnDice);

        mBtnShowResult1 = (Button) findViewById(R.id.btnShowResult1);
        mBtnShowResult2 = (Button) findViewById(R.id.btnShowResult2);

        mBtnShowResult1.setOnClickListener(btnShowResult1OnClick);
        mBtnShowResult2.setOnClickListener(btnShowResult2OnClick);
    }



    private void setDice(){
        int iComPlay = (int)(Math.random()*6 + 1);
        miCountSet++;
        switch (iComPlay){
            case 1:
                mImgBtnDice.setImageResource(R.drawable.dice01);
                Toast.makeText(MainActivity.this,R.string.player_win,Toast.LENGTH_SHORT).show();
                miCountPlayerWin ++;
                break;
            case 2:
                mImgBtnDice.setImageResource(R.drawable.dice02);
                Toast.makeText(MainActivity.this,R.string.player_win,Toast.LENGTH_SHORT).show();
                miCountPlayerWin ++;
                break;
            case 3:
                mImgBtnDice.setImageResource(R.drawable.dice03);
                Toast.makeText(MainActivity.this,R.string.player_draw,Toast.LENGTH_SHORT).show();
                miCountDraw ++;
                break;
            case 4:
                mImgBtnDice.setImageResource(R.drawable.dice04);
                Toast.makeText(MainActivity.this,R.string.player_draw,Toast.LENGTH_SHORT).show();
                miCountDraw ++;
                break;
            case 5:
                mImgBtnDice.setImageResource(R.drawable.dice05);
                Toast.makeText(MainActivity.this,R.string.player_lose,Toast.LENGTH_SHORT).show();
                miCountComWin ++;
                break;
            case 6:
                mImgBtnDice.setImageResource(R.drawable.dice06);
                Toast.makeText(MainActivity.this,R.string.player_lose,Toast.LENGTH_SHORT).show();
                miCountComWin ++;
                break;
        }

    }

    private View.OnClickListener btnShowResult1OnClick = new View.OnClickListener() {
        public void onClick(View v) {
            if(isDiceRolling) return;
            isDiceRolling = true;
            Resources resources = getResources();
            final AnimationDrawable animationDrawable = (AnimationDrawable) resources.getDrawable(R.drawable.anim_roll_dice);
            mImgBtnDice.setImageDrawable(animationDrawable);
            animationDrawable.start();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animationDrawable.stop();
                    setDice();
                    isDiceRolling = false;
                }
            }, 700);
        }
    };

    private View.OnClickListener btnShowResult2OnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Log.e("test:","start");
            Intent it = new Intent();
            it.setClass(MainActivity.this,ResultActivity.class);

            Bundle bundle = new Bundle();
            bundle.putInt("KEY_SET",miCountSet);
            bundle.putInt("KEY_WIN",miCountPlayerWin);
            bundle.putInt("KEY_DRAW",miCountDraw);
            bundle.putInt("KEY_LOSE",miCountComWin);
            it.putExtras(bundle);
            startActivity(it);
        }
    };
}

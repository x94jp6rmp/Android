package com.paperscissorsstonegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtComPlay, mTxtResult;
    private Button mBtnScissors, mBtnStone, mBtnPaper;
    Judge result;
    private ArrayList<String> res = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtComPlay = (TextView)findViewById(R.id.txtComPlay);
        mTxtResult = (TextView)findViewById(R.id.txtResult);
        mBtnScissors = (Button)findViewById(R.id.btnScissors);
        mBtnStone = (Button)findViewById(R.id.btnStone);
        mBtnPaper = (Button)findViewById(R.id.btnPaper);

        result = new Judge();
        res.add("null");
        res.add(getString(R.string.play_scissors));
        res.add(getString(R.string.play_stone));
        res.add(getString(R.string.play_paper));


        mBtnScissors.setOnClickListener(btnOnClick);
        mBtnStone.setOnClickListener(btnOnClick);
        mBtnPaper.setOnClickListener(btnOnClick);
    }

    private View.OnClickListener btnOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int iComPlay = (int)(Math.random()*3 + 1);
            int player = 0;
            mTxtComPlay.setText(res.get(iComPlay));
            switch (view.getId()){
                case R.id.btnScissors:
                    player = 1;
                    break;
                case R.id.btnStone:
                    player = 2;
                    break;
                case R.id.btnPaper:
                    player = 3;
                    break;
            }
            String end = "判定輸贏：";
            switch (result.getResult(player,iComPlay)){
                case "lose":
                    end += getString(R.string.player_lose);
                    break;
                case "draw":
                    end += getString(R.string.player_draw);
                    break;
                case "win":
                    end += getString(R.string.player_win);
                    break;
            }
            mTxtResult.setText(end);
        }
    };
}

package com.gameusingdynamicfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResultActivity extends AppCompatActivity {

    private EditText mEdtCountSet,mEdtCountPlayerWin,mEdtCountDraw,mEdtCountComWin;

    private Button mBtnCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game_result);

        mEdtCountSet = (EditText) findViewById(R.id.edtCountSet);
        mEdtCountPlayerWin = (EditText) findViewById(R.id.edtCountPlayerWin);
        mEdtCountDraw = (EditText) findViewById(R.id.edtCountDraw);
        mEdtCountComWin = (EditText) findViewById(R.id.edtCountComWin);

        mBtnCallBack = (Button) findViewById(R.id.btnCallBack);

        mBtnCallBack.setOnClickListener(btnCallBackOnClick);

        showResult();
    }

    private View.OnClickListener btnCallBackOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void showResult(){
        Bundle bundle = getIntent().getExtras();

        int iCountSet = bundle.getInt("KEY_SET");
        int iCountPlayerWin = bundle.getInt("KEY_WIN");
        int iCountDraw = bundle.getInt("KEY_DRAW");
        int iCountComWin = bundle.getInt("KEY_LOSE");

        mEdtCountSet.setText(Integer.toString(iCountSet));
        mEdtCountPlayerWin.setText(Integer.toString(iCountPlayerWin));
        mEdtCountDraw.setText(Integer.toString(iCountDraw));
        mEdtCountComWin.setText(Integer.toString(iCountComWin));
    }
}

package edu.ntut.user.myhw3;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CheckBox;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadGrp;
    private RadioButton mRadBtnMale;
    private RadioButton mRadBtnFemale;

    private Spinner mspnAge;

    private ArrayList<CheckBox> listCbHabbit;
    private Button mBtnOK;
    private TextView mTxtSug;
    private TextView mTxtHabbit;

    private ArrayAdapter<CharSequence> maleAge;
    private ArrayAdapter<CharSequence> femaleAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maleAge = ArrayAdapter.createFromResource(MainActivity.this,R.array.male_age_list,R.layout.support_simple_spinner_dropdown_item);
        femaleAge = ArrayAdapter.createFromResource(MainActivity.this ,R.array.female_age_list ,R.layout.support_simple_spinner_dropdown_item);

        mRadGrp = (RadioGroup) findViewById(R.id.radGrpSex);
        mRadBtnMale = (RadioButton) findViewById(R.id.radBtnSex1);
        mRadBtnFemale = (RadioButton) findViewById(R.id.radBtnSex2);
        mspnAge = (Spinner) findViewById(R.id.spnAge);
        mspnAge.setAdapter(maleAge);
        listCbHabbit = new ArrayList<>();
        listCbHabbit.add((CheckBox) findViewById(R.id.cbmusic));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbsing));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbdance));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbtravel));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbreading));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbwriting));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbclimbing));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbswim));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbeating));
        listCbHabbit.add((CheckBox) findViewById(R.id.cbdrawing));


        mBtnOK = (Button) findViewById(R.id.btnOK);
        mTxtSug = (TextView) findViewById(R.id.txtSug);
        mTxtHabbit = (TextView) findViewById(R.id.txtHabbit);

        mRadGrp.setOnCheckedChangeListener(radSex_OnCheckChange);
        mBtnOK.setOnClickListener(btnOKOnClick);
    }

    private RadioGroup.OnCheckedChangeListener radSex_OnCheckChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(i == R.id.radBtnSex1)
                mspnAge.setAdapter(maleAge);
            else if (i == R.id.radBtnSex2)
                mspnAge.setAdapter(femaleAge);

        }
    };


    private View.OnClickListener btnOKOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            MarriageSuggestion ms = new MarriageSuggestion();

            String strSex="" ;

            switch (mRadGrp.getCheckedRadioButtonId()){
                case R.id.radBtnSex1:
                    strSex = "male";
                    break;
                case R.id.radBtnSex2:
                    strSex = "female";
                    break;
            }

            int iAgeRange = 0;

                switch (mspnAge.getSelectedItem().toString()) {
                    case "小於30歲":
                        iAgeRange = 1;
                        break;
                    case "30~40歲":
                        iAgeRange = 2;
                        break;
                    case "大於40歲":
                        iAgeRange = 3;
                        break;
                    case "小於28歲":
                        iAgeRange = 1;
                        break;
                    case "28~35歲":
                        iAgeRange = 2;
                        break;
                    case "大於35歲":
                        iAgeRange = 3;
                        break;
                }

            int size = listCbHabbit.size();
            String habbit = "興趣：";
            for(int i = 0;i<size;i++){
                if(listCbHabbit.get(i).isChecked()){
                    habbit += listCbHabbit.get(i).getText();
                    habbit += " ";
                }
            }

            mTxtSug.setText(ms.getSuggestion(strSex, iAgeRange));
            mTxtHabbit.setText(habbit);
        }
    };
}

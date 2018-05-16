package com.example.gging.hw8;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String Year, Mon, Day;
    private ArrayList<String> datalist;

    private Spinner mspnMeal;
    private EditText mtxtDate, mtxtAmount;
    private DatePicker mdataPicker;
    private Button mbtnInput, mbtnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datalist = new ArrayList<>();

        mspnMeal = (Spinner) findViewById(R.id.spnMeal);
        mtxtDate = (EditText) findViewById(R.id.txtDate);
        mdataPicker =(DatePicker) findViewById(R.id.datap);
        mtxtAmount = (EditText) findViewById(R.id.txtAmount);
        mbtnInput= (Button) findViewById(R.id.btnInput);
        mbtnRecord = (Button) findViewById(R.id.btnRecord);

        mbtnInput.setOnClickListener(btnSubmitOnClick);
        mbtnRecord.setOnClickListener(btnRecordOnClick);
        mdataPicker.setOnDateChangedListener(dataPickerChang);

        mdataPicker.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),dataPickerChang);
    }

    private DatePicker.OnDateChangedListener dataPickerChang = new DatePicker.OnDateChangedListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Year = String.valueOf(year);
            Mon = String.valueOf(monthOfYear);
            Day = String.valueOf(dayOfMonth);

            mtxtDate.setText(Year+"/"+Mon+"/"+Day);
        }
    };

    private View.OnClickListener btnSubmitOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String data;
            data = "項目" + String.valueOf(datalist.size()) +"        "+
                    mtxtDate.getText() + "      " +
                    mspnMeal.getSelectedItem().toString() + "        "+
                    mtxtAmount.getText();

            datalist.add(data);

            Toast toast = Toast.makeText(MainActivity.this,mtxtAmount.getText(),Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private View.OnClickListener btnRecordOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,RecordsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("DataList",datalist);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    };

}

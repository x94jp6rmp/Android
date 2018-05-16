package com.example.gging.hw8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        listview = (ListView) findViewById(R.id.listItemData);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> data = bundle.getStringArrayList("DataList");



        ArrayAdapter<String> dataArr= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);

        listview.setAdapter(dataArr);
    }
}

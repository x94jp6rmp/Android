package com.example.hw10;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class SearchContactFragment extends Fragment {

    private ListView lsvContactList;
    private HighlightAdapter<String> dataAdapter;

    public SearchContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.searchcontact, container, false);
    }

    public void addDataToList(String data){
        dataAdapter.add(data);
        dataAdapter.notifyDataSetChanged();
        lsvContactList.clearChoices();
        lsvContactList.requestLayout();
    }

    public void setListHighlight() {
        dataAdapter.setHighlightList();
    }

    public void setListHighlight(ArrayList<String> list) {
        ArrayList<Integer> indexList = new ArrayList<>();
        int length = dataAdapter.getCount();

        for (int i = 0; i < length; i++) {
            if (list.contains(dataAdapter.getItem(i))) {
                indexList.add(i);
            }
        }

        dataAdapter.setHighlightList(indexList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        lsvContactList = (ListView) view.findViewById(R.id.lsv_contactList);
        lsvContactList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lsvContactList.clearChoices();

        dataAdapter = new HighlightAdapter<>(this.getContext(),android.R.layout.simple_list_item_1);
        lsvContactList.setAdapter(dataAdapter);

        Cursor cursor = MainActivity.sdbContact.rawQuery("select * from " + MainActivity.DB_TABLE,null);
        if(cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String data = "Name: " + cursor.getString(1) + "\nPhoneNumber: " + cursor.getString(2) + "\nPhoneType: " + cursor.getString(3);
                dataAdapter.add(data);
                cursor.moveToNext();
            }
            cursor.close();
        }
        dataAdapter.notifyDataSetChanged();
    }
}

class HighlightAdapter<T> extends ArrayAdapter<T> {

    private ArrayList<Integer> indexList;

    public HighlightAdapter(Context context, int resource) {
        super(context, resource);
        indexList = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View renderer = super.getView(position, convertView, parent);
        if (indexList.contains(position)) {
            renderer.setBackgroundResource(R.color.selected);
        } else {
            renderer.setBackgroundResource(R.color.normal);
        }
        return renderer;
    }

    public void setHighlightList() {
        indexList.clear();
    }

    public void setHighlightList(ArrayList<Integer> newIndexList) {
        indexList = newIndexList;
    }
}

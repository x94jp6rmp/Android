package com.example.hw11;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class SearchContactFragment extends Fragment {

    private ListView lsvContactList;
    private HighlightAdapter<String> dataAdapter;

    AlertDialog actionAskingDialog;
    AlertDialog confirmDeleteDialog;
    AlertDialog dataModifyDialog;

    EditText edtName_Modify, edtPhoneNumber_Modify;
    Spinner spinPhoneType_Modify;

    public SearchContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("確認刪除");
        builder.setMessage("確認刪除此筆資料？");
        builder.setPositiveButton("刪除", eventDeleteEntry);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        confirmDeleteDialog = builder.create();

        // Construct dataModifyDialog
        View view = View.inflate(getContext(), R.layout.data_modify, null);
        edtName_Modify = view.findViewById(R.id.edt_name_madify);
        edtPhoneNumber_Modify = view.findViewById(R.id.edt_phoneNumber_madify);
        spinPhoneType_Modify = view.findViewById(R.id.spin_phoneType_madify);
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("修改資料");
        builder.setView(view);
        builder.setPositiveButton("完成", eventModifyData);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dataModifyDialog = builder.create();

        // Construct actionAskingDialog
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("資料動作");
        builder.setMessage("請選擇要對資料進行的動作。");
        builder.setPositiveButton("修改", eventModifyAction);
        builder.setNeutralButton("刪除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmDeleteDialog.show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        actionAskingDialog = builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_contact, container, false);
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

    public void setListHighlight(ArrayList<Integer> list) {

        dataAdapter.setHighlightList(list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        lsvContactList = (ListView) view.findViewById(R.id.lsv_contactList);
        lsvContactList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lsvContactList.clearChoices();
        lsvContactList.setOnItemLongClickListener(lsvContactList_OnLongClick);

        dataAdapter = new HighlightAdapter<>(this.getContext(),android.R.layout.simple_list_item_1);
        lsvContactList.setAdapter(dataAdapter);

        Cursor cursor = MainActivity.mContRes.query(ContactProvider.CONTENT_URI, null, null, null, null);
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

    private final DialogInterface.OnClickListener eventModifyData = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            HighlightAdapter.DataModel data = dataAdapter.getLongPressedData();
            data.name = edtName_Modify.getText().toString();
            data.phoneNumber = edtPhoneNumber_Modify.getText().toString();
            data.phoneType = spinPhoneType_Modify.getSelectedItem().toString();
            MainActivity.mContRes.update(ContactProvider.CONTENT_URI, data.getContentValues(),
                    "_id = " + String.valueOf(data.id), null);
            dataAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "資料更新成功！", Toast.LENGTH_LONG).show();
        }
    };

    private final DialogInterface.OnClickListener eventModifyAction = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            HighlightAdapter.DataModel data = dataAdapter.getLongPressedData();
            edtName_Modify.setText(data.name);
            edtPhoneNumber_Modify.setText(data.phoneNumber);
            String[] phoneType = getResources().getStringArray(R.array.type_array);
            if (data.phoneType.equals(phoneType[0])) {
                spinPhoneType_Modify.setSelection(0);
            }
            else if (data.phoneType.equals(phoneType[1])) {
                spinPhoneType_Modify.setSelection(1);
            }
            else {
                spinPhoneType_Modify.setSelection(2);
            }
            dataModifyDialog.show();
        }
    };

    private final DialogInterface.OnClickListener eventDeleteEntry = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            HighlightAdapter.DataModel data = dataAdapter.getLongPressedData();
            MainActivity.mContRes.delete(ContactProvider.CONTENT_URI, "_id = " + data.id, null);
            dataAdapter.removeLongPressedData();
            Toast.makeText(getContext(), "資料刪除成功！", Toast.LENGTH_LONG).show();
        }
    };

    private final ListView.OnItemLongClickListener lsvContactList_OnLongClick = new ListView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            actionAskingDialog.show();
            return true;
        }
    };

    public void updateListData() {
        Cursor cursor = MainActivity.mContRes.query(ContactProvider.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            dataAdapter.clearAllData();
            // Get data iterator
            while (!cursor.isAfterLast()) {
                dataAdapter.addData(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                cursor.moveToNext();
            }
            cursor.close();
            dataAdapter.notifyDataSetChanged();
            dataAdapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(getContext(), "無法更新清單！", Toast.LENGTH_LONG).show();
        }
    }
}


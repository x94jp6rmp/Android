package com.example.hw10;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;


public class AddNewContactFragment extends Fragment {

    private EditText edtName;
    private EditText edtPhone;
    private Spinner spnType;

    public AddNewContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addnewcontact, container, false);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        edtName = (EditText) view.findViewById(R.id.edt_name);
        edtPhone = (EditText) view.findViewById(R.id.edt_phoneNumber);
        spnType = (Spinner) view.findViewById(R.id.spin_phoneType);
    }

    public ContentValues getContentValues(){
        ContentValues data = new ContentValues();
        data.put("name",edtName.getText().toString());
        data.put("phoneNumber",edtPhone.getText().toString());
        data.put("phoneType",spnType.getSelectedItem().toString());
        return data;
    }

}

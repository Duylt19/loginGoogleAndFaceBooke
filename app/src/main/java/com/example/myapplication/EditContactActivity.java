package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.ContactModel;

public class EditContactActivity extends AppCompatActivity {
    Button btnCancel;
    Button btnSave;
    EditText edtName;
    EditText edtPhone;
    EditText edtAddress;
    String mName = "";
    String mPhone = "";
    String mAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        onInit();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateFrom()){
                    int id = ContactActivity.modelList.get(ContactActivity.modelList.size() - 1).getId() + 1;
                    ContactModel model = new ContactModel(id,mName,mPhone,mAddress);
                    ContactActivity.adapter.add(model);
                    ContactActivity.adapter.notifyDataSetChanged();
                    Toast.makeText(EditContactActivity.this,"Add item succes",Toast.LENGTH_LONG).show();
                    onClearFrom();
                }
            }
        });

    }

    private boolean ValidateFrom(){
        mName = edtName.getText().toString();
        mPhone = edtPhone.getText().toString();
        mAddress = edtAddress.getText().toString();
        if(mName.length() < 1){
            edtName.setError("Name cannot be null");
            return false;
        }
        if(mPhone.length() < 1){
            edtPhone.setError("Phone cannot be null");
            return false;
        }
        if(mAddress.length() < 1){
            edtAddress.setError("Address cannot be null");
            return false;
        }
        return true;
    }

    private void onClearFrom(){
        edtName.clearComposingText();
        edtPhone.clearComposingText();
        edtAddress.clearComposingText();
    }

    private void onInit(){
        btnCancel = findViewById(R.id.Contact_edit_btnCancel);
        btnSave = findViewById(R.id.Contact_edit_btnSave);
        edtName = findViewById(R.id.contact_edit_edtName);
        edtPhone = findViewById(R.id.contact_edit_edtPhone);
        edtAddress = findViewById(R.id.contact_edit_edtAddress);
    }
}
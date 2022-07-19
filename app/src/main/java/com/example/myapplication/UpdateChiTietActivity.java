package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Model.ContactModel;

public class UpdateChiTietActivity extends AppCompatActivity {
    EditText editName;
    EditText editPhone;
    EditText editAddress;
    Button btnUpdate;
    Button btnBack;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chi_tiet);
        onInit();
        onGetValue();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactModel model = onValidateForm();
                if (model != null) {
                    ContactActivity.modelList.add(position, model);
                    ContactActivity.adapter.notifyDataSetChanged();
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private ContactModel onValidateForm(){
        String errorText="This Field Cannot Be Blank";
        String Name="",Address="",Phone="";
        Name=editName.getText().toString();
        if (Name.length()<1){
            editName.setError(errorText);
            return null;
        }
        Address=editAddress.getText().toString();
        if (Address.length()<1){
            editAddress.setError(errorText);
            return null;
        }
        Phone=editPhone.getText().toString();
        if (Phone.length()<1){
            editPhone.setError(errorText);
            return null;
        }
        int id= ContactActivity.modelList.get(ContactActivity.modelList.size() -1).getId()+1;
        ContactModel model = new ContactModel (id,Name,Phone,Address);
        return model;
    }
    private void onGetValue(){
        Intent intent= getIntent();
        ContactModel model = (ContactModel) intent.getSerializableExtra("ContactModel");
        position=intent.getIntExtra("POS",0);
        //Toast.makeText(this,"model",Toast.LENGTH_LONG).show();
        if(model != null) {
            //Toast.makeText(this,model.toString(),Toast.LENGTH_LONG).show();
            editName.setText(model.getName());
            editPhone.setText(model.getPhoneNumber());
            editAddress.setText(model.getAddress());
        }

    }
    private void onInit(){
        editName=findViewById(R.id.update_edit_Name);
        editPhone=findViewById(R.id.update_edit_phone);
        editAddress=findViewById(R.id.update_edit_address);
        btnUpdate=findViewById(R.id.update_btn_update);
        btnBack=findViewById(R.id.update_btn_back);
    }
}


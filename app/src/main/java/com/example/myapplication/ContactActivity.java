package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {
    ListView listView;
    static List<ContactModel> modelList;
    static ContactAdapter adapter;
    Button btnAddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        onInit();
        onSetData();

        adapter = new ContactAdapter(ContactActivity.this,R.layout.contact_item,modelList);
        listView.setAdapter(adapter);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this,EditContactActivity.class);
                startActivity(intent);
            }
        });
    }
    private void onSetData(){
        modelList = new ArrayList();
        modelList.add(new ContactModel(1,"John0","0124578223","TP.HCM"));
        modelList.add(new ContactModel(2,"John2","0123456789","TP.HCM"));
        modelList.add(new ContactModel(3,"John3","0123456789","TP.HCM"));
        modelList.add(new ContactModel(4,"John4","0123456789","TP.HCM"));
        modelList.add(new ContactModel(5,"John5","0123456789","TP.HCM"));
        modelList.add(new ContactModel(6,"John6","0125458771","TP.HCM"));
        modelList.add(new ContactModel(7,"John7","5415452454","TP.HCM"));

    }
    private void onInit(){
        listView = findViewById(R.id.lv_Contact);
        btnAddItem = findViewById(R.id.contact_Add);
    }
}


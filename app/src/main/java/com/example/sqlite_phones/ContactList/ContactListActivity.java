package com.example.sqlite_phones.ContactList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_phones.DB.ModelContact;
import com.example.sqlite_phones.DB.MyDbHelper;
import com.example.sqlite_phones.R;
import com.example.sqlite_phones.Update.UpdateActivity;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<ModelContact> mList;
    ContactListAdapter mAdapter = null;
    String id, name, phone, category, email, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        initViews();
        getAllContacts();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactListActivity.this, UpdateActivity.class);
                ModelContact model = (ModelContact)mAdapter.getItem(position);
                intent.putExtra("model", model);
                startActivityForResult(intent, 200);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    private void initViews(){
        mListView = findViewById(R.id.listSelect);
        mList = new ArrayList<>();
        mAdapter = new ContactListAdapter(this, R.layout.row, mList);
        mListView.setAdapter(mAdapter);
    }

    private void getAllContacts(){
        final MyDbHelper helper = new MyDbHelper(this);
        helper.createSchema();

        //get all data from table phones
        final Cursor cursor = helper.getData("SELECT * FROM PHONES ");
        mList.clear();

        while (cursor.moveToNext()){
            id = cursor.getString(0);
            name = cursor.getString(1);
            phone = cursor.getString(2);
            category = cursor.getString(3);
            email = cursor.getString(4);
            address = cursor.getString(5);

            //add contact to list
            mList.add(new ModelContact(id, name, phone, category, email, address));
        }
        mAdapter.notifyDataSetChanged();
        if (mList.size()==0){
            //if there is no contacts in table of database phones, which means listview is empty
            Toast.makeText(this, getString(R.string.msg_no_contacts_found), Toast.LENGTH_SHORT).show();
        }
    }
}
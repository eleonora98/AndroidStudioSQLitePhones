package com.example.sqlite_phones.Update;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_phones.ContactList.ContactListActivity;
import com.example.sqlite_phones.DB.ModelContact;
import com.example.sqlite_phones.DB.MyDbHelper;
import com.example.sqlite_phones.R;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDbHelper helper;
    private RelativeLayout wrap;
    private EditText editName, editPhone,editCategory, editEmail, editAddress;
    private Button btnUpdate, btnDelete, btnMap;
    private ImageButton btnCall;
    String ID = "";
    ModelContact model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initViews();
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        wrap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUpdate:
                updateContact();
                break;
            case R.id.btnDelete:
                deleteContact();
                break;
            case R.id.btnMap:
                goToAddress();
                break;
            case R.id.btnCall:
                makePhoneCall();
                break;
            case R.id.wrap:
                hideKeyboard(v);
                break;
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        goToContactList();
    }

    private void initViews(){
        wrap = findViewById(R.id.wrap);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editCategory = findViewById(R.id.editCategory);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnMap = findViewById(R.id.btnMap);
        btnCall = findViewById(R.id.btnCall);

        model = (ModelContact) getIntent().getSerializableExtra("model");
        ID = model.getId();
        editName.setText(model.getName());
        editPhone.setText(model.getPhone());
        editCategory.setText(model.getCategory());
        editEmail.setText(model.getEmail());
        editAddress.setText(model.getAddress());
    }

    private void updateContact(){
        try {
            helper = new MyDbHelper(UpdateActivity.this);
            String Name = editName.getText().toString();
            String Phone = editPhone.getText().toString();
            String Category = editCategory.getText().toString();
            String Email = editEmail.getText().toString();
            String Address = editAddress.getText().toString();
            if (Name.equals("") || Phone.equals("") || Category.equals("")) {
                throw new Exception(getString(R.string.msg_input_all_data));
            }

            helper.update(ID, Name, Phone, Category, Email, Address);
            Toast.makeText(getApplicationContext(), getString(R.string.msg_update_success), Toast.LENGTH_LONG)
                    .show();

            goToContactList();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        } finally {
            if (helper != null)
                helper.closeDb();
            helper = null;
        }
    }

    private void deleteContact(){
        try {
            helper = new MyDbHelper(UpdateActivity.this);

            helper.delete(ID);
            Toast.makeText(getApplicationContext(), getString(R.string.msg_delete_success), Toast.LENGTH_LONG)
                    .show();

            goToContactList();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        } finally {
            if (helper != null)
                helper.closeDb();
            helper = null;
        }
    }

    private void goToContactList(){
        Intent i = new Intent(UpdateActivity.this, ContactListActivity.class);
        startActivity(i);
        finish();
    }

    private void goToAddress(){
        String url = "http://maps.google.com/maps?q=" + model.getAddress();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void makePhoneCall(){
        String url = "tel:" + model.getPhone();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void hideKeyboard(View v) {
        //hide keyboard when user clicks outside of editText
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}
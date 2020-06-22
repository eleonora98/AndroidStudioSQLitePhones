package com.example.sqlite_phones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_phones.ContactList.ContactListActivity;
import com.example.sqlite_phones.DB.MyDbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static MyDbHelper helper;
    private RelativeLayout wrap;
    private EditText editName, editPhone, editCategory, editEmail, editAddress;
    private Button btnInsert, btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        btnInsert.setOnClickListener(this);
        btnList.setOnClickListener(this);
        wrap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsert:
                insertContact();
                break;
            case R.id.btnList:
                viewContacts();
                break;
            case R.id.wrap:
                hideKeyboard(v);
                break;
        }
    }

    private void initViews() {
        wrap = findViewById(R.id.wrap);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editCategory = findViewById(R.id.editCategory);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        btnInsert = findViewById(R.id.btnInsert);
        btnList = findViewById(R.id.btnList);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            editName.setText(b.getString("name"));
            editPhone.setText(b.getString("tel"));
            editCategory.setText(b.getString("category"));
            editEmail.setText(b.getString("email"));
            editAddress.setText(b.getString("address"));
        }

    }

    private void insertContact() {
        try {
            helper = new MyDbHelper(MainActivity.this);
            String Name = editName.getText().toString();
            String Phone = editPhone.getText().toString();
            String Category = editCategory.getText().toString();
            String Email = editEmail.getText().toString();
            String Address = editAddress.getText().toString();
            if (Name.isEmpty() || Phone.isEmpty() || Category.isEmpty()) {
                throw new Exception(getString(R.string.msg_input_all_data));
            }
            helper.insertContact(Name, Phone, Category, Email, Address);
            Toast.makeText(getApplicationContext(), getString(R.string.msg_save_success), Toast.LENGTH_LONG)
                    .show();

            viewContacts();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        } finally {
            if (helper != null)
                helper.closeDb();
            helper = null;
        }
    }

    private void viewContacts() {
        startActivity(new Intent(MainActivity.this, ContactListActivity.class));
    }

    private void hideKeyboard(View v) {
        //hide keyboard when user clicks outside of editText
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

}
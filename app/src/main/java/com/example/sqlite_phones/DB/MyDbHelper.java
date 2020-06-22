package com.example.sqlite_phones.DB;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlite_phones.ContactList.ContactListActivity;
import com.example.sqlite_phones.MainActivity;
import com.example.sqlite_phones.Update.UpdateActivity;

public class MyDbHelper {

    private String TableName="Phones";
    SQLiteDatabase db;

    private final String CreateQuery="Create Table " +" if not exists  "+  TableName +
            "(" +
            "ID integer primary key  AUTOINCREMENT, "+
            "Name text not null, "+
            "Phone text not null, "+
            "Category text not null, "+
            "Email text, "+
            "Address text "+
            ")";

    public MyDbHelper(MainActivity context) throws SQLException {
        db=SQLiteDatabase.openOrCreateDatabase(context.getFilesDir()+"/"+"contacts.db", null);

    }
    public MyDbHelper(UpdateActivity context) throws SQLException {
        db=SQLiteDatabase.openOrCreateDatabase(context.getFilesDir()+"/"+"contacts.db", null);

    }
    public MyDbHelper(ContactListActivity context) throws SQLException {
        db=SQLiteDatabase.openOrCreateDatabase(context.getFilesDir()+"/"+"contacts.db", null);

    }
    public void createSchema() throws  SQLException{
        //db.rawQuery(CreateQuery, null);
        db.execSQL(CreateQuery);
//        db.execSQL("ALTER TABLE " + TableName + " ADD COLUMN "+" Address ");
    }
    public void insertContact(String Name, String Phone, String Category, String Email, String Address) throws  SQLException{
        //db.beginTransaction();
        String q="INSERT INTO "+TableName+"(Name, Phone, Category, Email, Address) values(?, ?, ?, ?, ?);";
        db.execSQL(q, new String[]{Name, Phone, Category, Email, Address});
        //db.endTransaction();
    }
    public void update(String ID, String Name, String Phone, String Category, String Email, String Address) throws  SQLException {
        String q = "UPDATE " + TableName + " SET Name=?, Phone=?, Category=?, Email=?, Address=? WHERE ID=?";
        db.execSQL(q, new String[]{Name, Phone, Category, Email, Address, ID});
    }
    public void delete (String ID) throws SQLException {
            String q = "DELETE FROM " + TableName + " WHERE ID=?";
            db.execSQL(q, new String[]{ID});
    }
    public Cursor getData(String sql){
        return db.rawQuery(sql, null);
    }

    public void closeDb() {
            db.close();
            db = null;
    }

}
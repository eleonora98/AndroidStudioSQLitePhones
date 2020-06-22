package com.example.sqlite_phones.ContactList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlite_phones.DB.ModelContact;
import com.example.sqlite_phones.R;

import java.util.ArrayList;

public class ContactListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<ModelContact> contactList;

    public ContactListAdapter(Context context, int layout, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.layout = layout;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView txtName, txtPhone, txtCategory, txtEmail, txtAddress;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtName = row.findViewById(R.id.txtName);
            holder.txtPhone = row.findViewById(R.id.txtPhone);
            holder.txtCategory = row.findViewById(R.id.txtCategory);
            holder.txtEmail = row.findViewById(R.id.txtEmail);
            holder.txtAddress = row.findViewById(R.id.txtAddress);

            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }

        ModelContact model = contactList.get(i);

        holder.txtName.setText(model.getName());
        holder.txtPhone.setText(model.getPhone());
        holder.txtCategory.setText(model.getCategory());
        holder.txtEmail.setText(model.getEmail());
        holder.txtAddress.setText(model.getAddress());

        return row;
    }
}

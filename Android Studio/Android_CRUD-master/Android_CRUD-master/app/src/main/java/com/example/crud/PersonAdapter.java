package com.example.crud;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class PersonAdapter extends ArrayAdapter<Person> {
    public PersonAdapter(Context context, ArrayList<Person> persons) {
        super(context, 0, persons);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_person, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);

        tvName.setText(person.getName());
        tvEmail.setText(person.getEmail());

        return convertView;
    }
}

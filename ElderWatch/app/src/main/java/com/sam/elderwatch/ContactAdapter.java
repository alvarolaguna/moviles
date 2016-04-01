package com.sam.elderwatch;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by SAM on 19/2/2016.
 */
public class ContactAdapter extends BaseAdapter {
    // container we need to adapt
    MyDatabase database;

    // to attach interface we need a reference to an activity
    Activity activity;

    public ContactAdapter(MyDatabase database, Activity activity){

        this.database = database;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        Log.e("Numero de rows", database.getProfilesCount()+"");
        return database.getProfilesCount();
        //students.size();
    }

    @Override
    public String[] getItem(int position) {
        return database.findRecord(position+"");
        //students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // this is where we actually build the row UI
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            convertView = activity.getLayoutInflater().inflate(R.layout.rowcontacts, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.textView13);
        TextView number = (TextView) convertView.findViewById(R.id.textView23);

        name.setText(database.findRecord(position + 1 + "")[0]);
        number.setText(database.findRecord(position + 1 + "")[1]);

        return convertView;
    }
}



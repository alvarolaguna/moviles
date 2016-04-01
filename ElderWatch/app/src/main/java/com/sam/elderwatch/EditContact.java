package com.sam.elderwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class EditContact extends AppCompatActivity {

    String id, type;
    EditText name, phone;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        name = (EditText)findViewById(R.id.nameEt);
        phone = (EditText)findViewById(R.id.phoneEt);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        count = Integer.parseInt(intent.getStringExtra("count"));
        System.out.println(intent.getStringExtra("name"));
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));


        //intent.getStringExtra("phone")
    }

    public void cancelContactClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            Toast.makeText(getApplicationContext(), "Contact Unmodified" + id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("result", "3");
            //intent.putExtra("count",count+"");
            startActivity(intent);
        }

    }

    public void eraseContactClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            //Toast.makeText(getApplicationContext(), "Contact Erased", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("result", "1");
            intent.putExtra("id", id);

            startActivity(intent);

        }

    }

    public void saveContactClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){

           // Toast.makeText(getApplicationContext(), "Contact Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("result", "2");
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("phone", phone.getText().toString());
            intent.putExtra("type", type);
            Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
            intent.putExtra("id", id);
            intent.putExtra("count",count+"");
            startActivity(intent);
        }

    }
}

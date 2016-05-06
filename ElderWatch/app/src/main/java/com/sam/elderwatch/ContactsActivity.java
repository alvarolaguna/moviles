package com.sam.elderwatch;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    MyDatabase db;
    ContactAdapter myAdapter;
    ListView list;
    String name, phone, user;
    ArrayList<Contact> contacts;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        contacts = new ArrayList<Contact>();
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://elderwatch.firebaseio.com/contacts");


        Firebase contact1 = ref.child("contact1");

        contact1.child("name").setValue("Dr. Carlos Malvida");
        contact1.child("phone").setValue("33 44 12 34");
        contact1.child("imageURL").setValue("https://raw.githubusercontent.com/alvarolaguna/moviles/master/drcarlos.PNG");
        contacts.add(new Contact());

        Firebase contact2 = ref.child("contact2");

        contact2.child("name").setValue("Dr. Andrés Plascencia");
        contact2.child("phone").setValue("32 76 45 98");
        contact2.child("imageURL").setValue("https://raw.githubusercontent.com/alvarolaguna/moviles/master/drAndres.PNG");
        contacts.add(new Contact());

        Firebase contact3 = ref.child("contact3");

        contact3.child("name").setValue("Dr. Mike Brown");
        contact3.child("phone").setValue("36 84 51 28");
        contact3.child("imageURL").setValue("https://raw.githubusercontent.com/alvarolaguna/moviles/master/drmike.PNG");
        contacts.add(new Contact());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                count = (int) snapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        db = new MyDatabase(this);
        myAdapter = new ContactAdapter(db, this);
        list = (ListView)findViewById(R.id.listView2);


        Intent myIntent = getIntent();
        name = myIntent.getStringExtra("name");
        phone = myIntent.getStringExtra("phone");
        user = myIntent.getStringExtra("user");
        System.out.println("primer prueba " + name);
        if(myIntent.getStringExtra("result").equals("1")){
            String id = myIntent.getStringExtra("id");
            db.deleteRecord(id);
            int id2 = Integer.parseInt(id);
            String temp = "contact"+(id2);
            Firebase contact = ref.child(temp);
            contact.removeValue();
            Toast.makeText(getApplicationContext(), "Contact was erased"+id, Toast.LENGTH_SHORT).show();
        } else if (myIntent.getStringExtra("result").equals("2")){


            if(myIntent.getStringExtra("type").equals("add")){
                db.saveRecord(name, phone);
                int getCount = Integer.parseInt(myIntent.getStringExtra("count"));
                String temp = "contact"+(getCount+1);
                Firebase contact = ref.child(temp);
                contact.child("name").setValue(name);
                contact.child("phone").setValue(phone);
                contact.child("imageURL").setValue("https://raw.githubusercontent.com/alvarolaguna/moviles/master/drmike.PNG");
                Toast.makeText(getApplicationContext(), "Contact was created"+name+" "+phone, Toast.LENGTH_SHORT).show();
            } else{
                String id = myIntent.getStringExtra("id");
                db.modifyRecord(id, name, phone);
                int id2 = Integer.parseInt(id);
                String temp = "contact"+(id2);
                Firebase contact = ref.child(temp);
                contact.child("name").setValue(name);
                contact.child("phone").setValue(phone);
                contact.child("imageURL").setValue("https://raw.githubusercontent.com/alvarolaguna/moviles/master/drmike.PNG");
                Toast.makeText(getApplicationContext(), "Contact was modified"+id+" "+name+" "+phone, Toast.LENGTH_SHORT).show();
            }
        }
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(this);
        for(int i= 1+1; i < db.getProfilesCount()+1+1; i++){
            System.out.println("Impresion de db "+ db.findRecord(i+"")[0] + " " + db.findRecord(i+"")[1]);
        }
    }


    public void goLiveStreamClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            Toast.makeText(getApplicationContext(), "Going to Livestream", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LiveStreamActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }

    }

    public void addContactClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            Toast.makeText(getApplicationContext(), "Add contact", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EditContact.class);
            intent.putExtra("type","add");
            intent.putExtra("count",count+"");
            intent.putExtra("user",user);
            startActivity(intent);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(true){
            Toast.makeText(getApplicationContext(), "Edit Customer Info", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EditContact.class);
            intent.putExtra("id",position+1+"");
            intent.putExtra("type", "click");
            String[] tmp = myAdapter.getItem(position+1);
            System.out.println(tmp[0]+tmp[1]);
            intent.putExtra("name", tmp[0]);
            intent.putExtra("phone", tmp[1]);
            intent.putExtra("count",count+"");
            intent.putExtra("user",user);
            startActivity(intent);
        }
    }
}

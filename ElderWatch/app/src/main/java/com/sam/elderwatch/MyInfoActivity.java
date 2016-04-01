package com.sam.elderwatch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class MyInfoActivity extends AppCompatActivity {
    EditText name, blood, compName, compNo, phone;
    EditText myUser, myPass;
    private final String FILE = "myProperties.xml";
    Properties properties;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        name = (EditText)findViewById(R.id.editText3);
        blood = (EditText)findViewById(R.id.editText4);
        compName = (EditText)findViewById(R.id.editText5);
        compNo = (EditText)findViewById(R.id.editText6);
        phone = (EditText)findViewById(R.id.editText7);

        File file = new File(getFilesDir(), FILE);
        properties = new Properties();

        try {

            if(file.exists()){

                // properties file exist
                FileInputStream fis = openFileInput(FILE);
                properties.loadFromXML(fis);

                Toast.makeText(getApplicationContext(),
                        "Loaded properties from device",
                        Toast.LENGTH_SHORT).show();

            } else {

                try {

                    // or not - create it!
                    FileOutputStream fos = openFileOutput(FILE, Context.MODE_PRIVATE);
                    properties.storeToXML(fos, null);
                    fos.close();

                    Toast.makeText(getApplicationContext(),
                            "File created on device",
                            Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }catch(IOException ioe) {

            Log.e("MAIN", ioe.toString());
        }
    }

    public void savePersonalInfo(){
        properties.setProperty("user", name.getText().toString());
        properties.setProperty("blood", blood.getText().toString());
        properties.setProperty("compNo", compNo.getText().toString());
        properties.setProperty("compName", compName.getText().toString());
        properties.setProperty("phone", phone.getText().toString());
    }

    public void loadFromRawFile(View v){

        try{

            InputStream is = getResources().openRawResource(R.raw.example);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            Toast.makeText(getApplicationContext(),
                    br.readLine(),
                    Toast.LENGTH_SHORT).show();

        }catch(IOException ioe){

            ioe.printStackTrace();
        }
    }

    public void goLiveStreamClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            savePersonalInfo();
            Toast.makeText(getApplicationContext(), "Going to Livestream", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LiveStreamActivity.class);
            startActivity(intent);
        }

    }
}

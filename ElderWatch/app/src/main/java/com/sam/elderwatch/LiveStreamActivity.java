package com.sam.elderwatch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveStreamActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    VideoAdapter myAdapter;
    ListView list;
    Boolean visited = false;
    int count = 0, videoCount = 0;
    ProgressDialog pDialog;
    VideoView videoview;
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    ImageView imageView, testView;
    ArrayList<String> videoURL;
    ArrayList<MyVideo> videos;
    private boolean videoPressed, streamPressed;
    private VideoFragment videoFragment;
    private StreamFragment streamFragment;
    private android.support.v4.app.FragmentManager fm;
    Firebase ref;
    String user = "";

    /*
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();
            */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stream);
        Firebase.setAndroidContext(this);


        fm = getSupportFragmentManager();
        streamPressed();
        //testView = (ImageView)findViewById(R.id.imagePrueba);

        videoURL = new ArrayList<String>();
        videos = new ArrayList<MyVideo>();
         /*
        videos.add(new MyVideo());


        videos.add(new MyVideo());
        ;
        */

        videos.add(new MyVideo());
        videos.add(new MyVideo());
        videos.add(new MyVideo());
        videos.add(new MyVideo());

        Firebase authRef = new Firebase("https://elderwatch.firebaseio.com/");
        AuthData authData = authRef .getAuth();
        if (authData != null) {
            System.out.println("Authenticated user with uid:" +  authData.getUid());
        }

        Intent myIntent = getIntent();
        user = myIntent.getStringExtra("user");

        System.out.println("user is" + user);

        if(user!= null){
            if(user.equals("a"))
            {
                ref = new Firebase("https://elderwatch.firebaseio.com/users/user1/videos");
            }
            else if(user.equals("b")){
                ref = new Firebase("https://elderwatch.firebaseio.com/users/user2/videos");
            }
            else{
                ref = new Firebase("https://elderwatch.firebaseio.com/users/userDefault/videos");
            }
        }




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


        Firebase video1 = ref.child("video1");
        Firebase video2 = ref.child("video2");

        // Attach an listener to read the data at our posts reference
        video1.child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(0).setUrl(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        video1.child("thumbnail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(0).setThumbnail(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        video1.child("causa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(0).setCause(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        video1.child("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(0).setDate(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        video2.child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(1).setUrl(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        video2.child("thumbnail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(1).setThumbnail(snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        video2.child("causa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(1).setCause(snapshot.getValue().toString());

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        video2.child("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(1).setDate(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        videos.get(2).setUrl("http://i.istockimg.com/video_passthrough/60847888/153/60847888.mp4");
        videos.get(2).setThumbnail("https://raw.githubusercontent.com/alvarolaguna/moviles/master/capture1.PNG");
        videos.get(2).setCause("caida");
        videos.get(2).setDate("19-02-2015");

        videos.get(3).setUrl("http://techslides.com/demos/sample-videos/small.mp4");
        videos.get(3).setThumbnail("https://raw.githubusercontent.com/alvarolaguna/moviles/master/Capture3.PNG");
        videos.get(3).setCause("desmayo");
        videos.get(3).setDate("19-02-2015");

        videoview = (VideoView) findViewById(R.id.videoView);
        System.out.println(videos);
        myAdapter = new VideoAdapter(videos, this);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(this);
    }

    public void streamPressed(View v){
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        if(!streamPressed){
            streamPressed = true;
            videoPressed = false;
            ft.replace(R.id.frameLayout, streamFragment, "streamFragment");
            ft.commit();
        }
    }

    public void streamPressed(){
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        if(!streamPressed){
            streamPressed = true;
            videoPressed = false;
            streamFragment = streamFragment.newInstance();
            ft.add(R.id.frameLayout, streamFragment ,"hobbyFragment");
            ft.commit();
        }
    }

    public void videoPressed(String url){
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        videoPressed = true;
        streamPressed = false;
        videoFragment = videoFragment.newInstance(url);
        ft.replace(R.id.frameLayout, videoFragment, "videoFragment");
        ft.commit();
    }

    public void editMyInfoClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            Toast.makeText(getApplicationContext(), "Edit Customer Info", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MyInfoActivity.class);
            startActivity(intent);
        }

    }

    public void goContactsClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            Toast.makeText(getApplicationContext(), "Contacts Info", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("result", "0");
            startActivity(intent);
        }

    }

    public void logoutClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            Toast.makeText(getApplicationContext(), "Logout successfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        videoPressed(videos.get(position).getUrl()+"");
        //loadVideo(position);
    }
}



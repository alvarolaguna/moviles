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


    VideoDatabase videoDatabase;
    VideoAdapter myAdapter;
    ListView list;
    Boolean visited = false;
    int count = 0, videoCount = 0;
    ProgressDialog pDialog;
    VideoView videoview;
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    WebView wv;
    static String streamUrl = "http://192.168.1.68:8080/browserfs.html";
    ImageView imageView, testView;
    ArrayList<String> videoURL;
    ArrayList<MyVideo> videos;
    /*
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();
            */




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stream);
        Firebase.setAndroidContext(this);
        wv = (WebView)findViewById(R.id.webView);
        wv.loadUrl(streamUrl);
        videoDatabase = new VideoDatabase(this);

        //testView = (ImageView)findViewById(R.id.imagePrueba);
        videoDatabase.saveRecord("Caida", "Hace 4 horas");
        videoDatabase.saveRecord("Desmayo", "Hace 3 días");
        videoDatabase.saveRecord("Caida", "Hace 2 semanas");

        videoURL = new ArrayList<String>();
        videos = new ArrayList<MyVideo>();
        Firebase ref = new Firebase("https://elderwatch.firebaseio.com/videos");
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

        /*
        alansRef.child("fullName").setValue("Alan Turing");
        alansRef.child("birthYear").setValue(1912);
        */
        video1.child("url").setValue("http://techslides.com/demos/sample-videos/small.mp4");
        video1.child("thumbnail").setValue("https://raw.githubusercontent.com/alvarolaguna/moviles/master/capture1.PNG");
        video1.child("causa").setValue("caida");
        video1.child("fecha").setValue("19-02-2015");
        videos.add(new MyVideo());

        Firebase video2 = ref.child("video2");
        video2.child("url").setValue("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
        video2.child("thumbnail").setValue("https://raw.githubusercontent.com/alvarolaguna/moviles/master/capture2.PNG");
        video2.child("causa").setValue("desmayo");
        video2.child("fecha").setValue("24-04-2015");
        videos.add(new MyVideo());

        // Attach an listener to read the data at our posts reference
        video1.child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                videos.get(0).setUrl(snapshot.getValue().toString());
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));

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
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));
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
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));

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
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));

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
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));

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
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));
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
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));

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
                videoURL.add(snapshot.getValue().toString());
                //testView.setImageBitmap(BitmapFactory.decodeByteArray(snapshot.getValue()));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        videoview = (VideoView) findViewById(R.id.videoView);
        System.out.println(videos);
        myAdapter = new VideoAdapter(videos, this);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(this);
    }

    public void loadVideo(View v){

        //System.out.println("videoURL "+videos.get(count).getUrl());
        pDialog = new ProgressDialog(LiveStreamActivity.this);
        // Set progressbar title
        pDialog.setTitle("Cargando Streaming de Cámaras");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    LiveStreamActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse( videos.get(videoCount).getUrl());
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });
        if(videoCount == videoURL.size()-1) videoCount = 0;
        else{
            videoCount++;
        }
    }

    public void eraseData(){
        videoDatabase.deleteRecord("1");
        videoDatabase.deleteRecord("2");
        videoDatabase.deleteRecord("3");
    }

    public void editMyInfoClick(View v){
        /*
        Validar usuario registrado en la BD
        */
        if(true){
            Toast.makeText(getApplicationContext(), "Edit Customer Info", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MyInfoActivity.class);
            eraseData();
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
            eraseData();
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
            eraseData();
            startActivity(intent);
        }

    }

    public void loadVideo(int pos){
        //provicional
        pos %= 2;
        System.out.println("count "+count+"");
        System.out.println("videoURL ARRAY"+videoURL.get(0));
        pDialog = new ProgressDialog(LiveStreamActivity.this);
        // Set progressbar title
        pDialog.setTitle("Cargando Streaming de Cámaras");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    LiveStreamActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse( videos.get(pos).getUrl());
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadVideo(position);
    }
}



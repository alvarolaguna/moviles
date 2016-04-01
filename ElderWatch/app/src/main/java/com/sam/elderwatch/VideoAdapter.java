package com.sam.elderwatch;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by SAM on 19/2/2016.
 */
public class VideoAdapter extends BaseAdapter {
    // container we need to adapt
    ArrayList<MyVideo> videos;
    // to attach interface we need a reference to an activity
    Activity activity;
    ImageView imageView;
    Firebase ref = new Firebase("https://elderwatch.firebaseio.com/videos");

    public VideoAdapter(ArrayList<MyVideo> videos, Activity activity){
            //ArrayList<Student> students,
        this.videos = videos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public MyVideo getItem(int position) {
        return videos.get(position + 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // this is where we actually build the row UI
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            convertView = activity.getLayoutInflater().inflate(R.layout.row, null);
        }
        TextView event = (TextView) convertView.findViewById(R.id.textView6);
        TextView time = (TextView) convertView.findViewById(R.id.textView7);
       // ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbnailView);
        //https://raw.githubusercontent.com/alvarolaguna/moviles/master/capture1.PNG
        //http://192.168.1.74:8080/shot.jpg

        new DownloadImageTask((ImageView) convertView.findViewById(R.id.thumbnailView)).execute(videos.get(position).getThumbnail());
        System.out.println("Current pos "+position);
        videos.get(position).printVideo();
        event.setText(videos.get(position).getCause());
        time.setText(videos.get(position).getDate());



        return convertView;
    }
}

package com.example.adolfo.rssyoutube;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private Activity actividad;

    private ArrayList<Videos> listVideo;

    public Adapter(Activity actividad, ArrayList<Videos> listVideo) {
        this.actividad = actividad;
        this.listVideo = listVideo;
    }


    @Override
    public int getCount() {
        return listVideo.size();
    }

    @Override
    public Object getItem(int i) {
        return listVideo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) actividad.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.videos_list_item, null);
        }

        Videos videos = listVideo.get(i);

        TextView title = v.findViewById(R.id.textViewTitle);
        title.setText(videos.getTitle());

        TextView views = v.findViewById(R.id.textViewVisits);
        views.setText("Visitas: "+videos.getViews());

        ImageView miniatura = v.findViewById(R.id.imageVideoPic);

        Picasso.with(v.getContext()).load(videos.getMiniaturaURL()).into(miniatura);

        return v;
    }
}

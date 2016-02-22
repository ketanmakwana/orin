package com.developermaniax.orin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.developermaniax.orin.R;
import com.developermaniax.orin.component.FontView;
import com.developermaniax.orin.model.SongListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * #CCFF0000
 * Created by DEVELOPERMANIAX on 2/3/2016.
 */
public class SongsListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<SongListModel> mSongList;
    int resourceId;

    public SongsListAdapter(Context mContext, int resourceId, ArrayList<SongListModel> mSongList) {
        this.mContext = mContext;
        this.resourceId = resourceId;
        this.mSongList = mSongList;
    }

    @Override
    public int getCount() {
        return mSongList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_songs_items, parent, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearSong);
        ImageView imgUserPhoto = (ImageView) view.findViewById(R.id.imgArtistPhoto);
        TextView txtSongName = (TextView) view.findViewById(R.id.txtSongName);
        TextView txtSongDescription = (TextView) view.findViewById(R.id.txtSongDescription);
        FontView txtMore = (FontView) view.findViewById(R.id.txtMore);
        ImageView txtAdd = (ImageView) view.findViewById(R.id.txtAdd);
        if (mSongList != null) {
            Picasso.with(mContext).load(mSongList.get(position).getImageUrl()).into(imgUserPhoto);
            txtSongName.setText(mSongList.get(position).getSongName());
            txtSongDescription.setText(mSongList.get(position).getArtistName());
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}

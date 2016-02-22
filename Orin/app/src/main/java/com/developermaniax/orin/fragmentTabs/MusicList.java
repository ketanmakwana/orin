package com.developermaniax.orin.fragmentTabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.developermaniax.orin.R;
import com.developermaniax.orin.component.FontView;
import com.developermaniax.orin.model.SongListModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by DEVELOPERMANIAX on 2/9/2016.
 */
public class MusicList extends Fragment {
    ArrayList<SongListModel> mSongsModel;
    ListView lstMusics;
    MusicListAdapter adapter;
    final String imageUrl = "http://www.usingjava.com/app_pod_123/images/adv_2342e04feb93c6066a811080cda84992.jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.music_list, container, false);

        lstMusics = (ListView) view.findViewById(R.id.lstMusicList);
        mSongsModel = new ArrayList<>();

        adapter = new MusicListAdapter(getContext(), R.layout.list_songs_items, mSongsModel, this, getChildFragmentManager());
        lstMusics.setAdapter(adapter);

        return view;
    }

    public void changeFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager
                .beginTransaction();
        transaction.replace(R.id.musicFragment, new MusicFragment()).commit();

    }

    class MusicListAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<SongListModel> mSongList;
        int resourceId;
        FragmentManager fragmentManager;
        Fragment fragment;

        public MusicListAdapter(Context mContext, int resourceId, ArrayList<SongListModel> mSongList, Fragment fragment, FragmentManager fragmentManager) {
            this.mContext = mContext;
            this.resourceId = resourceId;
            this.mSongList = mSongList;
            this.fragmentManager = fragmentManager;
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
            ImageView imgUserPhoto = (ImageView) view.findViewById(R.id.imgArtistPhoto);
            TextView txtSongName = (TextView) view.findViewById(R.id.txtSongName);
            TextView txtSongDescription = (TextView) view.findViewById(R.id.txtSongDescription);
            FontView txtMore = (FontView) view.findViewById(R.id.txtMore);
            txtMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragment();
                }
            });
            ImageView txtAdd = (ImageView) view.findViewById(R.id.txtAdd);
            if (mSongList != null) {
//                Picasso.with(mContext).load(mSongList.get(position).getArtistPhoto()).into(imgUserPhoto);
//                txtSongName.setText(mSongList.get(position).getSongName());
//                txtSongDescription.setText(mSongList.get(position).getSongDescription());

            }
            return view;
        }
    }
}
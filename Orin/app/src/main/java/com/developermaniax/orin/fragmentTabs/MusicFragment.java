package com.developermaniax.orin.fragmentTabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developermaniax.orin.R;
import com.developermaniax.orin.Scoreboard;

/**
 * Created by DEVELOPERMANIAX on 2/6/2016.
 */
public class MusicFragment extends Fragment {

    ImageView txtBack;
    ImageView txtRatingStar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_with_details_fragment, container, false);
        txtBack = (ImageView) view.findViewById(R.id.txtMDBack);
        txtRatingStar = (ImageView) view.findViewById(R.id.txtRatingStar);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment();
            }
        });
        txtRatingStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Scoreboard.class));
            }
        });
        return view;
    }

    public void changeFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager
                .beginTransaction();
        transaction.replace(R.id.musicFragment, new MusicFragment()).commit();

    }
}

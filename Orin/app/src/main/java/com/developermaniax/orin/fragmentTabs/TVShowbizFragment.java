package com.developermaniax.orin.fragmentTabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developermaniax.orin.R;

/**
 * Created by DEVELOPERMANIAX on 2/6/2016.
 */
public class TVShowbizFragment  extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tv_showbiz_fragment,container,false);

        return view;
    }
}

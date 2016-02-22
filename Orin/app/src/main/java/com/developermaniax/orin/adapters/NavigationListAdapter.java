package com.developermaniax.orin.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developermaniax.orin.R;
import com.developermaniax.orin.model.DrawerHeaderListModel;
import com.developermaniax.orin.model.DrawerListItemModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by DEVELOPERMANIAX on 1/27/2016.
 */
public class NavigationListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<DrawerHeaderListModel> mainElements;
    private HashMap<Integer, List<DrawerListItemModel>> childElements;
    private LayoutInflater vi;

    public NavigationListAdapter(Context context, List<DrawerHeaderListModel> mainElements, HashMap<Integer, List<DrawerListItemModel>> childElements) {
        this.context = context;
        this.mainElements = mainElements;
        this.childElements = childElements;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return this.mainElements.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(this.childElements.get(groupPosition) == null)
            return 0;
        return this.childElements.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mainElements.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childElements.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        DrawerHeaderListModel headerTitle = (DrawerHeaderListModel) getGroup(groupPosition);
        DrawerHeaderListModel headerIcon = (DrawerHeaderListModel) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) context ).getLayoutInflater();

            if(groupPosition == 3 || groupPosition == 4){
                convertView = inflater.inflate(R.layout.nav_header_without_image, null);
                TextView txtText = (TextView) convertView.findViewById(R.id.txtNavWithoutImageHeader);
                txtText.setText(headerTitle.getHeader());
            }else{
                convertView = inflater.inflate(R.layout.nav_drawer_list_header_item, null);
                TextView txtHeader = (TextView) convertView.findViewById(R.id.txtHeader);
                ImageView imgHeaderIcon = (ImageView) convertView.findViewById(R.id.navHeaderIcon);

                txtHeader.setText(headerTitle.getHeader());
                imgHeaderIcon.setImageResource(headerIcon.getNavHeaderIcon());
            }
        }

         return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        DrawerListItemModel childText = (DrawerListItemModel) getChild(groupPosition, childPosition);
        DrawerListItemModel imgChileIcon = (DrawerListItemModel) getChild(groupPosition,childPosition);


            LayoutInflater infalInflater = ((Activity) context ).getLayoutInflater();

            view = infalInflater.inflate(R.layout.nav_drawer_list_item, null);
            TextView txtListChild = (TextView) view.findViewById(R.id.navText);

            if(groupPosition == 4 && childPosition == 0){
                view = infalInflater.inflate(R.layout.share_with_list_item, null);
                ImageView imgFaceBook = (ImageView) view.findViewById(R.id.imgFacebook);
                ImageView imgTwitter = (ImageView) view.findViewById(R.id.imgTwitter);
                ImageView imgGplus= (ImageView) view.findViewById(R.id.imgGPlus);

                imgFaceBook.setImageResource(R.drawable.facebook);
                imgTwitter.setImageResource(R.drawable.twitter_round_color);
                imgGplus.setImageResource(R.drawable.google_plus_icon);

                imgFaceBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Facebook Clicked!",Toast.LENGTH_SHORT).show();
                    }
                });
                imgTwitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"Twitter Clicked!",Toast.LENGTH_SHORT).show();
                    }
                });
                imgGplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"GPlus Clicked!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            txtListChild.setText(childText.getMenuName());
            ImageView imgChildNavIcon = (ImageView) view.findViewById(R.id.navIcon);
            //Picasso.with(context).load(imgChileIcon.getImageView()).into(imgChildNavIcon);
           return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


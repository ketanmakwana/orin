package com.developermaniax.orin.model;

/**
 * Created by DEVELOPERMANIAX on 1/27/2016.
 */
public class DrawerListItemModel {

    String imageView;
    String MenuName;


    public DrawerListItemModel(String imageView, String menuName) {
        this.imageView = imageView;
        MenuName = menuName;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }
}

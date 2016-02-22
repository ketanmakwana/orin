package com.developermaniax.orin.model;

/**
 * Created by Ketan on 20/02/16.
 */
public class DrawerHeaderListModel {

    String Header;
    int NavHeaderIcon;

    public DrawerHeaderListModel(String header, int navHeaderIcon) {
        Header = header;
        NavHeaderIcon = navHeaderIcon;
    }

    public DrawerHeaderListModel() {
    }


    public int getNavHeaderIcon() {
        return NavHeaderIcon;
    }

    public void setNavHeaderIcon(int navHeaderIcon) {
        NavHeaderIcon = navHeaderIcon;
    }


    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }
}

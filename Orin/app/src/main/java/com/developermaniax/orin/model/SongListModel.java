package com.developermaniax.orin.model;

/**
 * Created by DEVELOPERMANIAX on 2/4/2016.
 */
public class SongListModel {

    String Id;
    String PreviewUrl;
    String ImageUrl;
    String Duration;
    String ArtistName;
    String SongName;


    public SongListModel() {
    }

    public SongListModel(String id, String previewUrl, String imageUrl, String duration, String artistName, String songName, String genrename) {
        Id = id;
        PreviewUrl = previewUrl;
        ImageUrl = imageUrl;
        Duration = duration;
        ArtistName = artistName;
        SongName = songName;
        Genrename = genrename;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPreviewUrl() {
        return PreviewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        PreviewUrl = previewUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getGenrename() {
        return Genrename;
    }

    public void setGenrename(String genrename) {
        Genrename = genrename;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    String Genrename;
}

package com.siokagami.beansauce.bean;

import java.util.ArrayList;

/**
 * Created by SiOKagami on 2016/5/5.
 */
public class Attrs
{
    ArrayList<String> publisher;
    ArrayList<String> singler;
    ArrayList<String> pubdate;
    ArrayList<String> tracks;
    ArrayList<String> media;

    public ArrayList<String> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<String> media) {
        this.media = media;
    }

    public ArrayList<String> getPublisher() {
        return publisher;
    }

    public void setPublisher(ArrayList<String> publisher) {
        this.publisher = publisher;
    }

    public ArrayList<String> getSingler() {
        return singler;
    }

    public void setSingler(ArrayList<String> singler) {
        this.singler = singler;
    }

    public ArrayList<String> getPubdate() {
        return pubdate;
    }

    public void setPubdate(ArrayList<String> pubdate) {
        this.pubdate = pubdate;
    }

    public ArrayList<String> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<String> tracks) {
        this.tracks = tracks;
    }
}

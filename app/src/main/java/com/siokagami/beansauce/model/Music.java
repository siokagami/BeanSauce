package com.siokagami.beansauce.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SiOKagami on 2016/5/5.
 */
public class Music
{
    int count;
    int total;
    List<Musics> musics = new ArrayList<Musics>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Musics> getMusics() {
        return musics;
    }

    public void setMusics(List<Musics> musics) {
        this.musics = musics;
    }
}

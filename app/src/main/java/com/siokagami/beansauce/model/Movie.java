package com.siokagami.beansauce.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SiOKagami on 2016/5/5.
 */
public class Movie
{
    int count;
    int total;
    List<Subjects> subjects = new ArrayList<>();

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

    public List<Subjects> getSubjectses() {
        return subjects;
    }

    public void setSubjectses(List<Subjects> subjectses) {
        this.subjects = subjectses;
    }
}

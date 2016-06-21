package com.siokagami.beansauce.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SiOKagami on 2016/5/5.
 */
public class Book
{
    int count;
    int total;
    List<Books> books = new ArrayList<>();

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

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Books> books) {
        this.books = books;
    }
}


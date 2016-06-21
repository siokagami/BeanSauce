package com.siokagami.beansauce.bean;

import java.util.List;

/**
 * Created by SiOKagami on 2016/5/5.
 */
public class Subjects
{
    String id;
    String title;
    String year;
    Rating rating;
    String summary;
    Images images;
    List<String> countries;
    List<Casts> casts;
    List<String> genres;
    List<Directors> directors;

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Directors> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Directors> directors) {
        this.directors = directors;
    }

    public List<Casts> getCasts() {
        return casts;
    }

    public void setCasts(List<Casts> casts) {
        this.casts = casts;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getTitle() {
        if(title.equals(""))
        {
         return "片名：暂无信息" ;
        }
        else {
            return "片名："+title;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        if(year.equals(""))
        {
            return "年份：暂无信息";
        }
        else {
            return "年份："+year;
        }
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Rating getRating() {

        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}

package com.siokagami.beansauce.bean;

import java.util.ArrayList;

public class Books
{
    String pubdate;
    String title;
    String price;
    String publisher;
    String id;
    String summary;
    ArrayList<String> author;
    Images images;

    String pages;
    String binding;

    public String getPages() {
        if(pubdate.equals(""))
        {
            return "页数：暂无信息";
        }
        else {
            return "页数：" + pages;
        }
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getBinding() {
        if(binding.equals(""))
        {
            return "版本：暂无信息";
        }
        else {
            return "版本：" + binding;
        }
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        if(author.size()==0)
        {
            return "作者：暂无信息";
        }
        else {
            return "作者："+author.toString();
        }

    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getPubdate() {
        if(pubdate.equals(""))
        {
            return "出版日期：暂无信息";
        }
        else
        {
            return "出版日期：" + pubdate;
        }
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getTitle() {
        return "书名："+title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice()
    {
        if(price.contains("元")) {
            String[] s = price.split("元");
            return "RMB "+s[0];
        }
        else if(price.contains("USD"))
        {
            return price;
        }
        else if(price.equals(""))
        {
            return "暂无价格";
        }
        else
        {
            return price;
        }

    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        if (publisher.equals(""))
        {
            return "出版社：暂无信息";
        } else
        {
            return "出版社：" + publisher;
        }
    }


    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSummary() {
        if(summary.equals(""))
        {
            return "暂无简介";
        }
        else {
            return "    "+summary;
        }
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

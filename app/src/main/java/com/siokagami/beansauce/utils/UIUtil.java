package com.siokagami.beansauce.utils;

import android.content.Context;
import android.content.Intent;

import com.siokagami.beansauce.search.activity.BookProfileActivity;
import com.siokagami.beansauce.search.activity.MovieProfileActivity;
import com.siokagami.beansauce.search.activity.MusicProfileActivity;
import com.siokagami.beansauce.search.activity.SearchActivity;

/**
 * Created by SiOKagami on 2016/5/4.
 */
public class UIUtil
{
    public static void showSearchActivity(Context context)
    {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
    public static void showBookProfileActivity(Context context,String id)
    {
        Intent intent = new Intent(context, BookProfileActivity.class);
        intent.putExtra(BookProfileActivity.BOOK_ID,id);
        context.startActivity(intent);
    }
    public static void showMusicProfileActivity(Context context,String id)
    {
        Intent intent = new Intent(context, MusicProfileActivity.class);
        intent.putExtra(MusicProfileActivity.MUSIC_ID,id);
        context.startActivity(intent);
    }
    public static void showMovieProfileActivity(Context context,String id)
    {
        Intent intent = new Intent(context, MovieProfileActivity.class);
        intent.putExtra(MovieProfileActivity.MOVIE_ID,id);
        context.startActivity(intent);
    }
}

package com.siokagami.beansauce.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by SiOKagami on 2016/5/4.
 */
public class SearchApi {
    public static void getSearchBookList(Context context,String keyWords,int start,AsyncHttpResponseHandler handler)
    {
        RequestParams params = new RequestParams();
        params.add("q",keyWords);
        params.put("start",start);
        Client.get(Client.getAppClient(),context,"/v2/book/search",params,handler);
    }
    public static void getBookProfile(Context context,String id,AsyncHttpResponseHandler handler)
    {
        RequestParams params = new RequestParams();
        Client.get(Client.getAppClient(), context, "/v2/book/"+id, params, handler);
    }
    public static void getSearchMusicList(Context context,String keyWords,int start,AsyncHttpResponseHandler handler)
    {
        RequestParams params = new RequestParams();
        params.add("q",keyWords);
        params.put("start",start);
        Client.get(Client.getAppClient(),context,"/v2/music/search",params,handler);
    }
    public static void getMusicProfile(Context context,String id,AsyncHttpResponseHandler handler)
    {
        RequestParams params = new RequestParams();
        Client.get(Client.getAppClient(), context, "/v2/music/"+id, params, handler);
    }
    public static void getSearchMovieList(Context context,String keyWords,int start,AsyncHttpResponseHandler handler)
    {
        RequestParams params = new RequestParams();
        params.add("q",keyWords);
        params.put("start",start);
        Client.get(Client.getAppClient(),context,"/v2/movie/search",params,handler);
    }
    public static void getMovieProfile(Context context,String id,AsyncHttpResponseHandler handler)
    {
        RequestParams params = new RequestParams();
        Client.get(Client.getAppClient(), context, "/v2/movie/subject/"+id, params, handler);
    }

}

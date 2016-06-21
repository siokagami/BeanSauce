package com.siokagami.beansauce.api;

import com.siokagami.beansauce.bean.Musics;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by leuxun on 2016/6/21.
 */
public interface RetrofitApiService
{
    @GET("/v2/music/")
    Observable<Musics> getMusicProfile(@Query("id") String id);
}

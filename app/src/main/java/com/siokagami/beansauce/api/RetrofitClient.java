package com.siokagami.beansauce.api;


import com.siokagami.beansauce.bean.Musics;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


public class RetrofitClient
{
    private static final String BASE_URL ="https://api.douban.com";
    private static final Retrofit mRetorfit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static final RetrofitApiService apiManager = mRetorfit.create(RetrofitApiService.class);
    public static Observable<Musics> getMusicProfile(String id) {
        return apiManager.getMusicProfile(id);
    }
}

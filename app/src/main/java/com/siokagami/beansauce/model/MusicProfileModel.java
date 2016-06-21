package com.siokagami.beansauce.model;


import com.siokagami.beansauce.api.RetrofitClient;
import com.siokagami.beansauce.bean.Musics;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MusicProfileModel implements MusicProfileModelInf {

    private onMusicProfileListener tonMusicProfileListener;
    public MusicProfileModel(onMusicProfileListener monMusicProfileListener)
    {
        this.tonMusicProfileListener = monMusicProfileListener;
    }

    @Override
    public void getMusicProfile(String id) {
        RetrofitClient.getMusicProfile(id).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Musics>() {
                    @Override
                    public void call(Musics musics) {
                        tonMusicProfileListener.onSuccess(musics);


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        tonMusicProfileListener.onFailure(throwable);
                    }
                });
    }


    public interface onMusicProfileListener
    {
        void onSuccess(Musics musics);
        void onFailure(Throwable e);
    }
}



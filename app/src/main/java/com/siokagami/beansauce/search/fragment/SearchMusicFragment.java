package com.siokagami.beansauce.search.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.siokagami.beansauce.R;
import com.siokagami.beansauce.api.SearchApi;
import com.siokagami.beansauce.model.Book;
import com.siokagami.beansauce.model.Music;
import com.siokagami.beansauce.model.Musics;
import com.siokagami.beansauce.search.adapter.SearchMusicListAdapter;
import com.siokagami.beansauce.utils.DeviceUtil;
import com.siokagami.beansauce.view.GeneralListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMusicFragment extends Fragment {

    private static String KEYWORDS = "musicKeywords";
    private String keyWords ="いけないボーダーライン";
    private Music musicList;
    List<Musics> listpath = new ArrayList<Musics>();
    private GeneralListView listViewSearchMusic;
    private SearchMusicListAdapter searchMusicListAdapter;

    public SearchMusicFragment() {
        // Required empty public constructor
    }
    public static SearchMusicFragment newInstance(String arg){
        SearchMusicFragment fragment = new SearchMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString( KEYWORDS, arg);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_music, container, false);
        initView(rootView);
        return rootView;
    }
    
    public void reLoad()
    {
        listpath.clear();
        keyWords = getKeyWords();
        if(listViewSearchMusic!=null) {
            listViewSearchMusic.reloadData();
        }
    }
    public void loadListData(final int page)
    {
        int start = (page-1)*20;
        if(!DeviceUtil.isNetConnected(getContext()))
        {
            Toast.makeText(getContext(), "没有联网呢···", Toast.LENGTH_SHORT).show();
            listViewSearchMusic.setLoadState(GeneralListView.STATE_FAIL);
        }
        else {
            SearchApi.getSearchMusicList(getContext(), keyWords, start, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    musicList = new Gson().fromJson(response.toString(), Music.class);
                    if (page == 1) {
                        listpath.clear();
                    }
                    if (musicList.getTotal() == 0) {
                        listViewSearchMusic.setLoadState(GeneralListView.STATE_EMPTY);
                        return;
                    }
                    listpath.addAll(musicList.getMusics());
                    searchMusicListAdapter.notifyDataSetChanged();
                    listViewSearchMusic.setLoadState(GeneralListView.STATE_SUCCESS);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    listViewSearchMusic.setLoadState(GeneralListView.STATE_FAIL);
                }
            });
        }

    }
    
    private void initView(View view)
    {
        listViewSearchMusic = (GeneralListView)view.findViewById(R.id.list_view_search_music);
        listViewSearchMusic.setLayoutManager(new LinearLayoutManager(getContext()));
        searchMusicListAdapter = new SearchMusicListAdapter(getContext(),listpath);
        listViewSearchMusic.setAdapter(searchMusicListAdapter);
        listViewSearchMusic.setOnLoadDataListener(new GeneralListView.OnLoadDataListener() {
            @Override
            public void onLoadData(int page) {
                loadListData(page);
            }
        });
        listViewSearchMusic.startLoadData();
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}

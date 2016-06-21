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
import com.siokagami.android.goodrecyclerview.GoodRecyclerView;
import com.siokagami.beansauce.R;
import com.siokagami.beansauce.api.SearchApi;
import com.siokagami.beansauce.bean.Movie;
import com.siokagami.beansauce.bean.Subjects;
import com.siokagami.beansauce.search.adapter.SearchMovieListAdapter;
import com.siokagami.beansauce.utils.DeviceUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovieFragment extends Fragment {
    private static String KEYWORDS = "movieKeywords";
    private String keyWords ="宫崎骏";
    private Movie movieList;
    List<Subjects> listpath = new ArrayList<Subjects>();
    private GoodRecyclerView listViewSearchMovie;
    private SearchMovieListAdapter searchMovieListAdapter;


    public SearchMovieFragment() {
        // Required empty public constructor
    }
    public static SearchMovieFragment newInstance(String arg){
        SearchMovieFragment fragment = new SearchMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putString( KEYWORDS, arg);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_movie, container, false);
        initView(rootView);
        return rootView;
    }
    public void reLoad()
    {
        listpath.clear();
        keyWords = getKeyWords();
        if(listViewSearchMovie!=null) {
            listViewSearchMovie.reloadData();
        }
    }
    public void loadListData(final int page)
    {
        int start = (page-1)*20;
        if(!DeviceUtil.isNetConnected(getContext()))
        {
            Toast.makeText(getContext(), "没有联网呢···", Toast.LENGTH_SHORT).show();
            listViewSearchMovie.setLoadState(GoodRecyclerView.STATE_FAIL);
        }
        else {
            SearchApi.getSearchMovieList(getContext(), keyWords, start, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    movieList = new Gson().fromJson(response.toString(), Movie.class);
                    if (page == 1) {
                        listpath.clear();
                    }
                    if (movieList.getTotal() == 0) {
                        listViewSearchMovie.setLoadState(GoodRecyclerView.STATE_EMPTY);
                        return;
                    }
                    listpath.addAll(movieList.getSubjectses());
                    searchMovieListAdapter.notifyDataSetChanged();
                    listViewSearchMovie.setLoadState(GoodRecyclerView.STATE_SUCCESS);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    listViewSearchMovie.setLoadState(GoodRecyclerView.STATE_FAIL);
                }
            });
        }

    }
    private void initView(View view)
    {
        listViewSearchMovie = (GoodRecyclerView)view.findViewById(R.id.list_view_search_movie);
        listViewSearchMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        searchMovieListAdapter = new SearchMovieListAdapter(getContext(),listpath);
        listViewSearchMovie.setAdapter(searchMovieListAdapter);
        listViewSearchMovie.setOnLoadDataListener(new GoodRecyclerView.OnLoadDataListener() {
            @Override
            public void onLoadData(int page) {
                loadListData(page);
            }
        });
        listViewSearchMovie.startLoadData();
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

}

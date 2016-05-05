package com.siokagami.beansauce.search.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.siokagami.beansauce.R;
import com.siokagami.beansauce.api.SearchApi;
import com.siokagami.beansauce.model.Movie;
import com.siokagami.beansauce.model.Subjects;
import com.siokagami.beansauce.search.adapter.SearchMovieListAdapter;
import com.siokagami.beansauce.view.GeneralListView;

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
    private GeneralListView listViewSearchMovie;
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
        listViewSearchMovie.reloadData();
    }
    public void loadListData(final int page)
    {
        int start = (page-1)*20;
        SearchApi.getSearchMovieList(getContext(), keyWords, start, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                movieList = new Gson().fromJson(response.toString(), Movie.class);
                if (page == 1) {
                    listpath.clear();
                }
                if (movieList.getTotal() == 0) {
                    listViewSearchMovie.setLoadState(GeneralListView.STATE_EMPTY);
                    return;
                }
                listpath.addAll(movieList.getSubjectses());
                searchMovieListAdapter.notifyDataSetChanged();
                listViewSearchMovie.setLoadState(GeneralListView.STATE_SUCCESS);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }
    private void initView(View view)
    {
        listViewSearchMovie = (GeneralListView)view.findViewById(R.id.list_view_search_movie);
        listViewSearchMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        searchMovieListAdapter = new SearchMovieListAdapter(getContext(),listpath);
        listViewSearchMovie.setAdapter(searchMovieListAdapter);
        listViewSearchMovie.setOnLoadDataListener(new GeneralListView.OnLoadDataListener() {
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

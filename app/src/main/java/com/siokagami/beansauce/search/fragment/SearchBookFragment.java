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
import com.siokagami.beansauce.model.Book;
import com.siokagami.beansauce.model.Books;
import com.siokagami.beansauce.search.adapter.SearchBookListAdapter;
import com.siokagami.beansauce.utils.LogUtil;
import com.siokagami.beansauce.view.GeneralListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchBookFragment extends Fragment {
    private String keyWords = "奋斗吧系统工程师";
    private Book bookList;
    List<Books> listpath = new ArrayList<Books>();
    private static String KEYWORDS = "bookKeywords";
    private GeneralListView listViewSearchBook;
    private SearchBookListAdapter searchBookListAdapter;


    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public SearchBookFragment() {
        // Required empty public constructor
    }

    public static SearchBookFragment newInstance(String arg){
        SearchBookFragment fragment = new SearchBookFragment();
        Bundle bundle = new Bundle();
        bundle.putString( KEYWORDS, arg);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void reLoad()
    {
        listpath.clear();
        keyWords = getKeyWords();
        listViewSearchBook.reloadData();
    }
    public void loadListData(final int page)
    {
        int start = (page-1)*20;

        SearchApi.getSearchBookList(getContext(), keyWords,start, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                bookList = new Gson().fromJson(response.toString(), Book.class);
                if (page==1) {
                    listpath.clear();
                }
                if (bookList.getTotal() == 0) {
                    listViewSearchBook.setLoadState(GeneralListView.STATE_EMPTY);
                    return;
                }
                listpath.addAll(bookList.getBooks());
                searchBookListAdapter.notifyDataSetChanged();
                listViewSearchBook.setLoadState(GeneralListView.STATE_SUCCESS);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_search_book, container, false);
        initView(rootView);
        return rootView;
    }
    private void initView(View view)
    {
        listViewSearchBook = (GeneralListView)view.findViewById(R.id.list_view_search_book);
        listViewSearchBook.setLayoutManager(new LinearLayoutManager(getContext()));
        searchBookListAdapter = new SearchBookListAdapter(getContext(),listpath);
        listViewSearchBook.setAdapter(searchBookListAdapter);
        listViewSearchBook.setOnLoadDataListener(new GeneralListView.OnLoadDataListener() {
            @Override
            public void onLoadData(int page) {
                loadListData(page);
            }
        });
        listViewSearchBook.startLoadData();
    }

}

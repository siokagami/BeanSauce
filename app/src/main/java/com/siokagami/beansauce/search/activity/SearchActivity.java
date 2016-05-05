package com.siokagami.beansauce.search.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siokagami.beansauce.R;
import com.siokagami.beansauce.base.BaseActivity;
import com.siokagami.beansauce.search.fragment.SearchBookFragment;
import com.siokagami.beansauce.search.fragment.SearchMovieFragment;
import com.siokagami.beansauce.search.fragment.SearchMusicFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private List<Fragment> mDatas = new ArrayList<Fragment>();
    private FragmentPagerAdapter fragmentPagerAdapter;
    private SearchBookFragment  searchBookFragment;
    private SearchMusicFragment searchMusicFragment;
    private SearchMovieFragment searchMovieFragment;
    private EditText searchEdittextKeyword;
    private LinearLayout searchLayoutBook;
    private LinearLayout searchLayoutMusic;
    private LinearLayout searchLayoutMovie;
    private ImageView searchTab;
    private ViewPager searchViewpager;
    private TextView searchTwBook;
    private TextView searchTwMusic;
    private TextView searchTwMovie;

    private int screen1_3;
    private int mCurrentPageIndex;
    private String keyword = "平凡之路";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        initFragment();
        setTabSwipe();
    }
    private void initFragment()
    {
        searchBookFragment = SearchBookFragment.newInstance(keyword);
        searchMovieFragment = SearchMovieFragment.newInstance(keyword);
        searchMusicFragment = SearchMusicFragment.newInstance(keyword);
        mDatas.add(searchBookFragment);
        mDatas.add(searchMusicFragment);
        mDatas.add(searchMovieFragment);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };
        searchViewpager.setAdapter(fragmentPagerAdapter);
        searchViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) searchTab.getLayoutParams();
                if (mCurrentPageIndex == 0 && position == 0)
                {
                    lp.leftMargin = (int) (positionOffset * screen1_3 + mCurrentPageIndex * screen1_3);
                }
                else if (mCurrentPageIndex == 1 && position == 0)
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * screen1_3 + (positionOffset - 1) * screen1_3);
                }
                else if (mCurrentPageIndex == 1 && position == 1)
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * screen1_3 + positionOffset  * screen1_3);
                }
                else if (mCurrentPageIndex == 2 && position == 1)
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * screen1_3 + (positionOffset-1)  * screen1_3);
                }

                searchTab.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        searchTwBook.setTextColor(0xFFE8792E);
                        break;
                    case 1:
                        searchTwMusic.setTextColor(0xFFE8792E);
                        break;
                    case 2:
                        searchTwMovie.setTextColor(0xFFE8792E);
                }
                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void setTabSwipe()
    {
        searchTab = (ImageView)findViewById(R.id.search_tab);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        screen1_3 = outMetrics.widthPixels / 3;
        ViewGroup.LayoutParams lp = searchTab.getLayoutParams();
        lp.width = screen1_3;
        searchTab.setLayoutParams(lp);
    }
    private void resetTextView()
    {
        searchTwBook.setTextColor(0xFF999999);
        searchTwMovie.setTextColor(0xFF999999);
        searchTwMusic.setTextColor(0xFF999999);
    }
    public void init()
    {
        searchEdittextKeyword = (EditText) findViewById(R.id.search_edittext_keyword);
        searchLayoutBook = (LinearLayout) findViewById(R.id.search_layout_book);
        if (searchLayoutBook != null) {
            searchLayoutBook.setOnClickListener(this);
        }
        searchLayoutMusic = (LinearLayout) findViewById(R.id.search_layout_music);
        if (searchLayoutMusic != null) {
            searchLayoutMusic.setOnClickListener(this);
        }
        searchLayoutMovie = (LinearLayout) findViewById(R.id.search_layout_movie);
        if (searchLayoutMovie != null) {
            searchLayoutMovie.setOnClickListener(this);
        }
        searchTab = (ImageView) findViewById(R.id.search_tab);
        searchViewpager = (ViewPager) findViewById(R.id.search_viewpager);
        searchTwBook = (TextView) findViewById(R.id.search_tw_book);
        searchTwMusic = (TextView) findViewById(R.id.search_tw_music);
        searchTwMovie = (TextView) findViewById(R.id.search_tw_movie);
        searchEdittextKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    keyword = searchEdittextKeyword.getText().toString();
                    searchBookFragment.setKeyWords(keyword);
                    searchMusicFragment.setKeyWords(keyword);
                    searchMovieFragment.setKeyWords(keyword);
                    searchBookFragment.reLoad();
                    searchMusicFragment.reLoad();
                    searchMovieFragment.reLoad();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_layout_book:
                searchViewpager.setCurrentItem(0);
                break;
            case R.id.search_layout_music:
                searchViewpager.setCurrentItem(1);
                break;
            case R.id.search_layout_movie:
                searchViewpager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
}

package com.siokagami.beansauce.search.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.siokagami.beansauce.R;
import com.siokagami.beansauce.api.SearchApi;
import com.siokagami.beansauce.base.BaseActivity;
import com.siokagami.beansauce.bean.Casts;
import com.siokagami.beansauce.bean.Subjects;
import com.siokagami.beansauce.search.fragment.BookSummaryFragment;
import com.siokagami.beansauce.search.fragment.MovieProfileFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieProfileActivity extends BaseActivity  implements View.OnClickListener{
    String id;
    String summary;
    String casts = "演员名：";
    String directors = "导演：";
    Subjects subjects_profile;
    List<Casts> casts_profile;
    public static String MOVIE_ID = "MOVIE_ID";
    private LinearLayout movieProfileLayoutHead;
    private ImageView movieProfileImg;
    private TextView movieProfileTwTitle;
    private TextView movieProfileTwAuthor;
    private LinearLayout movieProfileLayoutMed;
    private LinearLayout movieProfileLayoutProfile;
    private TextView movieProfileTwProfile;
    private LinearLayout movieProfileLayoutSummary;
    private TextView movieProfileTwSummary;
    private ImageView movieProfileTab;
    private ViewPager movieProfileViewpager;
    FragmentPagerAdapter moviePagerAdapter;
    BookSummaryFragment movieSummaryFragment;
    MovieProfileFragment movieProfileFragment;
    private List<Fragment> movie_datas = new ArrayList<Fragment>();
    private int half_screen;
    private int mCurrentPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_profile);
        id = getIntent().getStringExtra(MOVIE_ID);
        init();
        setData();
        setTabSwipe();
    }
    public void init()
    {


        movieProfileLayoutHead = (LinearLayout) findViewById(R.id.movie_profile_layout_head);
        movieProfileImg = (ImageView) findViewById(R.id.movie_profile_img);
        movieProfileTwTitle = (TextView) findViewById(R.id.movie_profile_tw_title);
        movieProfileTwAuthor = (TextView) findViewById(R.id.movie_profile_tw_author);
        movieProfileLayoutMed = (LinearLayout) findViewById(R.id.movie_profile_layout_med);
        movieProfileLayoutProfile = (LinearLayout) findViewById(R.id.movie_profile_layout_profile);
        movieProfileLayoutProfile.setOnClickListener(this);
        movieProfileTwProfile = (TextView) findViewById(R.id.movie_profile_tw_profile);
        movieProfileLayoutSummary = (LinearLayout) findViewById(R.id.movie_profile_layout_summary);
        movieProfileLayoutSummary.setOnClickListener(this);
        movieProfileTwSummary = (TextView) findViewById(R.id.movie_profile_tw_summary);
        movieProfileTab = (ImageView) findViewById(R.id.movie_profile_tab);
        movieProfileViewpager = (ViewPager) findViewById(R.id.movie_profile_viewpager);

    }
    private void setTabSwipe()
    {
        movieProfileTab = (ImageView)findViewById(R.id.movie_profile_tab);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        half_screen = outMetrics.widthPixels / 2;
        ViewGroup.LayoutParams lp = movieProfileTab.getLayoutParams();
        lp.width = half_screen;
        movieProfileTab.setLayoutParams(lp);
    }
    private void resetTextView()
    {
        movieProfileTwProfile.setTextColor(0xFF999999);
        movieProfileTwSummary.setTextColor(0xFF999999);
    }
    private void initFragment()
    {
        movieProfileFragment = MovieProfileFragment.newInstance(subjects_profile.getCountries().toString(),casts,directors);
        movieSummaryFragment = BookSummaryFragment.newInstance(summary);
        movie_datas.add(movieProfileFragment);
        movie_datas.add(movieSummaryFragment);
        moviePagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return movie_datas.get(position);
            }

            @Override
            public int getCount() {
                return movie_datas.size();
            }
        };
        movieProfileViewpager.setAdapter(moviePagerAdapter);
        movieProfileViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) movieProfileTab.getLayoutParams();
                if (mCurrentPageIndex == 0 && position == 0) {
                    lp.leftMargin = (int) (positionOffset * half_screen + mCurrentPageIndex * half_screen);
                } else if (mCurrentPageIndex == 1 && position == 0) {
                    lp.leftMargin = (int) (mCurrentPageIndex * half_screen + (positionOffset - 1) * half_screen);
                }

                movieProfileTab.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        movieProfileTwProfile.setTextColor(0xFFE8792E);
                        break;
                    case 1:
                        movieProfileTwSummary.setTextColor(0xFFE8792E);
                        break;
                }
                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setData()
    {
        SearchApi.getMovieProfile(MovieProfileActivity.this, id, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                subjects_profile = new Gson().fromJson(response.toString(), Subjects.class);
                casts_profile = subjects_profile.getCasts();
                movieProfileTwTitle.setText(subjects_profile.getTitle());
                movieProfileTwAuthor.setText(subjects_profile.getYear());
                summary = subjects_profile.getSummary();


                for (int i = 0; i < subjects_profile.getCasts().size(); i++) {
                    if (subjects_profile.getCasts().get(i).getName() == null) {
                        casts = "演员名：暂无信息";
                    } else {
                        casts += subjects_profile.getCasts().get(i).getName() + " ";
                    }
                }
                for (int i = 0; i < subjects_profile.getDirectors().size(); i++) {
                    if (subjects_profile.getDirectors().get(i).getNames() == null) {
                        directors = "导演：暂无信息";
                    } else {
                        directors += subjects_profile.getDirectors().get(i).getNames() + " ";
                    }

                }


                Glide.with(MovieProfileActivity.this).load(subjects_profile.getImages().getMedium())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_data_empty)
                        .centerCrop()
                        .into(movieProfileImg);
                initFragment();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.movie_profile_layout_profile:
                movieProfileViewpager.setCurrentItem(0);
                break;
            case R.id.movie_profile_layout_summary:
                movieProfileViewpager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }
}

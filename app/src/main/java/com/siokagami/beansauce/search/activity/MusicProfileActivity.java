package com.siokagami.beansauce.search.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
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
import com.siokagami.beansauce.bean.Attrs;
import com.siokagami.beansauce.bean.Musics;
import com.siokagami.beansauce.search.fragment.BookSummaryFragment;
import com.siokagami.beansauce.search.fragment.MusicProfileFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MusicProfileActivity extends BaseActivity  implements View.OnClickListener{
    public static String MUSIC_ID = "id";
    String id;
    String summary;
    Attrs attr_profile;
    Musics musics_profile;
    private LinearLayout musicProfileLayoutHead;
    private ImageView musicProfileImg;
    private TextView musicProfileTwTitle;
    private TextView musicProfileTwAuthor;
    private LinearLayout musicProfileLayoutMed;
    private LinearLayout musicProfileLayoutProfile;
    private TextView musicProfileTwProfile;
    private LinearLayout musicProfileLayoutSummary;
    private TextView musicProfileTwSummary;
    private ImageView musicProfileTab;
    private ViewPager musicProfileViewpager;
    private int half_screen;
    private int mCurrentPageIndex;
    private List<Fragment> music_datas = new ArrayList<Fragment>();
    BookSummaryFragment musicSummaryFragment;
    MusicProfileFragment musicProfileFragment;
    FragmentPagerAdapter musicPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra(MUSIC_ID);
        setContentView(R.layout.activity_music_profile);
        init();
        setData();
        setTabSwipe();
    }
    private void init()
    {
        musicProfileLayoutHead = (LinearLayout) findViewById(R.id.music_profile_layout_head);
        musicProfileImg = (ImageView) findViewById(R.id.music_profile_img);
        musicProfileTwTitle = (TextView) findViewById(R.id.music_profile_tw_title);
        musicProfileTwAuthor = (TextView) findViewById(R.id.music_profile_tw_author);
        musicProfileLayoutMed = (LinearLayout) findViewById(R.id.music_profile_layout_med);
        musicProfileLayoutProfile = (LinearLayout) findViewById(R.id.music_profile_layout_profile);
        musicProfileLayoutProfile.setOnClickListener(this);
        musicProfileTwProfile = (TextView) findViewById(R.id.music_profile_tw_profile);
        musicProfileLayoutSummary = (LinearLayout) findViewById(R.id.music_profile_layout_summary);
        musicProfileLayoutSummary.setOnClickListener(this);
        musicProfileTwSummary = (TextView) findViewById(R.id.music_profile_tw_summary);
        musicProfileTab = (ImageView) findViewById(R.id.music_profile_tab);
        musicProfileViewpager = (ViewPager) findViewById(R.id.music_profile_viewpager);

    }
    private void resetTextView()
    {
        musicProfileTwProfile.setTextColor(0xFF999999);
        musicProfileTwSummary.setTextColor(0xFF999999);
    }
    private void initFragment()
    {
        musicProfileFragment = MusicProfileFragment.newInstance(attr_profile.getTracks().toString(), attr_profile.getMedia().toString(), attr_profile.getPubdate().toString(), attr_profile.getPublisher().toString());
        musicSummaryFragment = BookSummaryFragment.newInstance(summary);
        music_datas.add(musicProfileFragment);
        music_datas.add(musicSummaryFragment);
        musicPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return music_datas.get(position);
            }

            @Override
            public int getCount() {
                return music_datas.size();
            }
        };
        musicProfileViewpager.setAdapter(musicPagerAdapter);
        musicProfileViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) musicProfileTab.getLayoutParams();
                if (mCurrentPageIndex == 0 && position == 0) {
                    lp.leftMargin = (int) (positionOffset * half_screen + mCurrentPageIndex * half_screen);
                } else if (mCurrentPageIndex == 1 && position == 0) {
                    lp.leftMargin = (int) (mCurrentPageIndex * half_screen + (positionOffset - 1) * half_screen);
                }

                musicProfileTab.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        musicProfileTwProfile.setTextColor(0xFFE8792E);
                        break;
                    case 1:
                        musicProfileTwSummary.setTextColor(0xFFE8792E);
                        break;
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
        musicProfileTab = (ImageView)findViewById(R.id.music_profile_tab);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        half_screen = outMetrics.widthPixels / 2;
        ViewGroup.LayoutParams lp = musicProfileTab.getLayoutParams();
        lp.width = half_screen;
        musicProfileTab.setLayoutParams(lp);
    }

    private void setData()
    {
        SearchApi.getMusicProfile(MusicProfileActivity.this, id, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                musics_profile = new Gson().fromJson(response.toString(), Musics.class);
                attr_profile = musics_profile.getAttrs();
                musicProfileTwTitle.setText(musics_profile.getTitle());
                musicProfileTwAuthor.setText(musics_profile.getAuthor().get(0).getName());
                summary = musics_profile.getSummary();
                Glide.with(MusicProfileActivity.this).load(musics_profile.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_data_empty)
                        .centerCrop()
                        .into(musicProfileImg);
                initFragment();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.music_profile_layout_profile:
                musicProfileViewpager.setCurrentItem(0);
                break;
            case R.id.music_profile_layout_summary:
                musicProfileViewpager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }
}

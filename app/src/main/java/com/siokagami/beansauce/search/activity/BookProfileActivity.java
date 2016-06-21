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
import com.siokagami.beansauce.bean.Books;
import com.siokagami.beansauce.search.fragment.BookProfileFragment;
import com.siokagami.beansauce.search.fragment.BookSummaryFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class BookProfileActivity extends BaseActivity implements View.OnClickListener {
    public static String BOOK_ID = "id";
    private String id;
    private String summary;
    private Books book_profile;
    FragmentPagerAdapter bookPagerAdapter;
    BookProfileFragment bookProfileFragment;
    BookSummaryFragment bookSummaryFragment;
    private List<Fragment> book_datas = new ArrayList<Fragment>();
    private LinearLayout bookProfileLayoutHead;
    private ImageView bookProfileImg;
    private TextView bookProfileTwTitle;
    private TextView bookProfileTwAuthor;
    private TextView bookProfileTwPrice;
    private LinearLayout bookProfileLayoutMed;
    private LinearLayout bookProfileLayoutProfile;
    private TextView bookProfileTwProfile;
    private LinearLayout bookProfileLayoutSummary;
    private TextView bookProfileTwSummary;
    private ImageView bookProfileTab;
    private ViewPager bookProfileViewpager;
    private int half_screen;
    private int mCurrentPageIndex;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);
        id = getIntent().getStringExtra(BOOK_ID);
        init();
        setData();
        setTabSwipe();
    }


    private void init()
    {
        bookProfileLayoutHead = (LinearLayout) findViewById(R.id.book_profile_layout_head);
        bookProfileImg = (ImageView) findViewById(R.id.book_profile_img);
        bookProfileTwTitle = (TextView) findViewById(R.id.book_profile_tw_title);
        bookProfileTwAuthor = (TextView) findViewById(R.id.book_profile_tw_author);
        bookProfileTwPrice = (TextView) findViewById(R.id.book_profile_tw_price);
        bookProfileLayoutMed = (LinearLayout) findViewById(R.id.book_profile_layout_med);
        bookProfileLayoutProfile = (LinearLayout) findViewById(R.id.book_profile_layout_profile);
        bookProfileLayoutProfile.setOnClickListener(this);
        bookProfileTwProfile = (TextView) findViewById(R.id.book_profile_tw_profile);
        bookProfileLayoutSummary = (LinearLayout) findViewById(R.id.book_profile_layout_summary);
        bookProfileLayoutSummary.setOnClickListener(this);
        bookProfileTwSummary = (TextView) findViewById(R.id.book_profile_tw_summary);
        bookProfileTab = (ImageView) findViewById(R.id.book_profile_tab);
        bookProfileViewpager = (ViewPager) findViewById(R.id.book_profile_viewpager);

    }
    private void setData()
    {
        SearchApi.getBookProfile(BookProfileActivity.this,id,new JsonHttpResponseHandler()
        {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                book_profile = new Gson().fromJson(response.toString(), Books.class);
                bookProfileTwTitle.setText(book_profile.getTitle());
                bookProfileTwPrice.setText(book_profile.getPrice());
                bookProfileTwAuthor.setText(book_profile.getAuthor());
                summary = book_profile.getSummary();
                Glide.with(BookProfileActivity.this).load(book_profile.getImages().getMedium())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_data_empty)
                        .centerCrop()
                        .into(bookProfileImg);

                initFragment();
            }
        });
    }
    private void initFragment()
    {
        bookProfileFragment = BookProfileFragment.newInstance(book_profile.getPages(),book_profile.getBinding(),book_profile.getPubdate(),book_profile.getPublisher());
        bookSummaryFragment = BookSummaryFragment.newInstance(summary);
        book_datas.add(bookProfileFragment);
        book_datas.add(bookSummaryFragment);
        bookPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return book_datas.get(position);
            }

            @Override
            public int getCount() {
                return book_datas.size();
            }
        };
        bookProfileViewpager.setAdapter(bookPagerAdapter);
        bookProfileViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bookProfileTab.getLayoutParams();
                if (mCurrentPageIndex == 0 && position == 0)
                {
                    lp.leftMargin = (int) (positionOffset * half_screen + mCurrentPageIndex * half_screen);
                }
                else if (mCurrentPageIndex == 1 && position == 0)
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * half_screen + (positionOffset - 1) * half_screen);
                }

                bookProfileTab.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        bookProfileTwProfile.setTextColor(0xFFE8792E);
                        break;
                    case 1:
                        bookProfileTwSummary.setTextColor(0xFFE8792E);
                        break;
                }
                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void resetTextView()
    {
        bookProfileTwProfile.setTextColor(0xFF999999);
        bookProfileTwSummary.setTextColor(0xFF999999);
    }
    private void setTabSwipe()
    {
        bookProfileTab = (ImageView)findViewById(R.id.book_profile_tab);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        half_screen = outMetrics.widthPixels / 2;
        ViewGroup.LayoutParams lp = bookProfileTab.getLayoutParams();
        lp.width = half_screen;
        bookProfileTab.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.book_profile_layout_profile:
                bookProfileViewpager.setCurrentItem(0);
                break;
            case R.id.book_profile_layout_summary:
                bookProfileViewpager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }
}

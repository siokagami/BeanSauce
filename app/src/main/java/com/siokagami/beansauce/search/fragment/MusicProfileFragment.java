package com.siokagami.beansauce.search.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siokagami.beansauce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicProfileFragment extends Fragment {
    private static String MUSIC_TRACK = "MUSIC_TRACK";
    private static String MUSIC_BINDING = "MUSIC_BINDING";
    private static String MUSIC_PUBDATE = "MUSIC_PUBDATE";
    private static String MUSIC_PUBLISHER = "MUSIC_PUBLISHER";
    
    
    private TextView musicProfileTwTrack;
    private TextView musicProfileTwBinding;
    private TextView musicProfileTwPubdate;
    private TextView musicProfileTwPublisher;


    public MusicProfileFragment() {
        // Required empty public constructor
    }
    public static MusicProfileFragment newInstance(String track,String binding,String publishdate,String publisher){
        MusicProfileFragment fragment = new MusicProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MUSIC_TRACK, track);
        bundle.putString(MUSIC_BINDING, binding);
        bundle.putString(MUSIC_PUBDATE, publishdate);
        bundle.putString(MUSIC_PUBLISHER, publisher);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music_profile, container, false);
        initView(rootView);
        return rootView;
    }
    private void initView(View rootView)
    {
        musicProfileTwTrack = (TextView) rootView.findViewById(R.id.music_profile_tw_track);
        musicProfileTwTrack.setText("专辑："+getArguments().getString(MUSIC_TRACK));
        musicProfileTwBinding = (TextView) rootView.findViewById(R.id.music_profile_tw_binding);
        musicProfileTwBinding.setText("版本："+getArguments().getString(MUSIC_BINDING));
        musicProfileTwPubdate = (TextView) rootView.findViewById(R.id.music_profile_tw_pubdate);
        musicProfileTwPubdate.setText("出版日期："+getArguments().getString(MUSIC_PUBDATE));
        musicProfileTwPublisher = (TextView) rootView.findViewById(R.id.music_profile_tw_publisher);
        musicProfileTwPublisher.setText("出版商：" +getArguments().getString(MUSIC_PUBLISHER));

    }

}

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
public class MovieProfileFragment extends Fragment {
    private static String MOVIE_COUNTRIES = "MOVIE_COUNTRIES";
    private static String MOVIE_CASTS = "MOVIE_CASTS";
    private static String MOVIE_DIRESTOR = "MOVIE_DIRESTOR";


    private TextView movieProfileTwCountrues;
    private TextView movieProfileTwDirector;
    private TextView movieProfileTwCasts;



    public MovieProfileFragment() {
        // Required empty public constructor
    }

    public static MovieProfileFragment newInstance(String countries,String casts,String direstor){
        MovieProfileFragment fragment = new MovieProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_COUNTRIES, countries);
        bundle.putString(MOVIE_CASTS, casts);
        bundle.putString(MOVIE_DIRESTOR, direstor);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_profile, container, false);
        initView(rootView);
        return rootView;
    }
    public void initView(View rootView)
    {

        movieProfileTwCountrues = (TextView) rootView.findViewById(R.id.movie_profile_tw_countrues);
        movieProfileTwCountrues.setText("国家："+getArguments().getString(MOVIE_COUNTRIES));
        movieProfileTwDirector = (TextView) rootView.findViewById(R.id.movie_profile_tw_director);
        movieProfileTwDirector.setText(getArguments().getString(MOVIE_DIRESTOR));
        movieProfileTwCasts = (TextView) rootView.findViewById(R.id.movie_profile_tw_casts);
        movieProfileTwCasts.setText(getArguments().getString(MOVIE_CASTS));

    }

}

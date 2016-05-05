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
public class BookSummaryFragment extends Fragment {
    private static String KEYWORDS = "bookSummary";
    private TextView fgBookSummary;



    public BookSummaryFragment() {
        // Required empty public constructor
    }
    public static BookSummaryFragment newInstance(String arg){
        BookSummaryFragment fragment = new BookSummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString( KEYWORDS, arg);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book_summary, container, false);
        fgBookSummary = (TextView) rootView.findViewById(R.id.fg_book_summary);
        fgBookSummary.setText(getArguments().getString(KEYWORDS));

        return rootView;
    }

}

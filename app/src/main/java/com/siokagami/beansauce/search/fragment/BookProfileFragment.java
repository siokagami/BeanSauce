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
public class BookProfileFragment extends Fragment {
    private static String BOOK_PUBLISHER = "bookPublisher";
    private static String BOOK_BINDING = "bookBinding";
    private static String BOOK_PUBDATE = "bookDate";
    private static String BOOK_PAGE = "bookPage";


    private TextView bookProfileTwPage;
    private TextView bookProfileTwBinding;
    private TextView bookProfileTwPubdate;
    private TextView bookProfileTwPublisher;


    public BookProfileFragment() {
        // Required empty public constructor
    }
    public static BookProfileFragment newInstance(String page,String binding,String publishdate,String publisher){
        BookProfileFragment fragment = new BookProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BOOK_PUBDATE, publishdate);
        bundle.putString(BOOK_PUBLISHER, publisher);
        bundle.putString(BOOK_BINDING, binding);
        bundle.putString(BOOK_PAGE, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book_profile, container, false);
        bookProfileTwPage = (TextView) rootView.findViewById(R.id.book_profile_tw_page);
        bookProfileTwBinding = (TextView) rootView.findViewById(R.id.book_profile_tw_binding);
        bookProfileTwPubdate = (TextView) rootView.findViewById(R.id.book_profile_tw_pubdate);
        bookProfileTwPublisher = (TextView) rootView.findViewById(R.id.book_profile_tw_publisher);
        bookProfileTwPage.setText(getArguments().getString(BOOK_PAGE));
        bookProfileTwBinding.setText(getArguments().getString(BOOK_BINDING));
        bookProfileTwPubdate.setText(getArguments().getString(BOOK_PUBDATE));
        bookProfileTwPublisher.setText(getArguments().getString(BOOK_PUBLISHER));
        return rootView;
    }

}

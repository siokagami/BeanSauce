package com.siokagami.beansauce.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.siokagami.beansauce.R;
import com.siokagami.beansauce.model.Book;
import com.siokagami.beansauce.model.Books;
import com.siokagami.beansauce.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SiOKagami on 2016/5/4.
 */
public class SearchBookListAdapter extends RecyclerView.Adapter<SearchBookListAdapter.ViewHolder>
{
    Context context;
    List<Books> listpath;
    private LayoutInflater mInflater;
    public SearchBookListAdapter(Context context, List<Books> listpath) {
        this.context = context;
        this.listpath = listpath;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_search_book_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.itemBookTitle.setText(listpath.get(position).getTitle());
        holder.itemBookPrice.setText(listpath.get(position).getPrice());
        holder.itemBookPubdate.setText(listpath.get(position).getPubdate());
        holder.itemBookPublisher.setText(listpath.get(position).getPublisher());
        holder.itemBookAuthor.setText(listpath.get(position).getAuthor());
        Glide.with(context).load(listpath.get(position).getImages().getMedium())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_data_empty)
                .centerCrop()
                .into(holder.itemBookPic);
        holder.itemSearchBookRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showBookProfileActivity(context,listpath.get(position).getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listpath.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutInflater mInflater;
        private TextView itemBookTitle;
        private TextView itemBookAuthor;
        private TextView itemBookPublisher;
        private TextView itemBookPubdate;
        private TextView itemBookPrice;
        private ImageView itemBookPic;
        private LinearLayout itemSearchBookRoot;






        public ViewHolder(View itemView) {
            super(itemView);
            itemBookTitle = (TextView) itemView.findViewById(R.id.item_book_title);
            itemBookAuthor = (TextView) itemView.findViewById(R.id.item_book_author);
            itemBookPublisher = (TextView) itemView.findViewById(R.id.item_book_publisher);
            itemBookPubdate = (TextView) itemView.findViewById(R.id.item_book_pubdate);
            itemBookPrice = (TextView) itemView.findViewById(R.id.item_book_price);
            itemBookPic = (ImageView) itemView.findViewById(R.id.item_book_pic);
            itemSearchBookRoot = (LinearLayout) itemView.findViewById(R.id.item_search_book_root);

        }

    }
}

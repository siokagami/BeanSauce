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
import com.siokagami.beansauce.model.Books;
import com.siokagami.beansauce.model.Music;
import com.siokagami.beansauce.model.Musics;
import com.siokagami.beansauce.utils.UIUtil;

import java.util.List;

/**
 * Created by SiOKagami on 2016/5/5.
 */
public class SearchMusicListAdapter extends RecyclerView.Adapter<SearchMusicListAdapter.ViewHolder> {

    Context context;
    List<Musics> listpath;
    private LayoutInflater mInflater;

    public SearchMusicListAdapter(Context context, List<Musics> listpath) {
        this.context = context;
        this.listpath = listpath;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_search_music_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemSearchMusicRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showMusicProfileActivity(context,listpath.get(position).getId());
            }
        });
        holder.itemMusicTitle.setText(listpath.get(position).getTitle());
        holder.itemMusicAuthor.setText(listpath.get(position).getAuthor().get(0).getName());
        Glide.with(context).load(listpath.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_data_empty)
                .centerCrop()
                .into(holder.itemMusicPic);

    }

    @Override
    public int getItemCount() {
        return listpath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemSearchMusicRoot;
        private ImageView itemMusicPic;
        private TextView itemMusicTitle;
        private TextView itemMusicAuthor;
        public ViewHolder(View itemView) {
            super(itemView);
            itemSearchMusicRoot = (LinearLayout) itemView.findViewById(R.id.item_search_music_root);
            itemMusicPic = (ImageView) itemView.findViewById(R.id.item_music_pic);
            itemMusicTitle = (TextView) itemView.findViewById(R.id.item_music_title);
            itemMusicAuthor = (TextView) itemView.findViewById(R.id.item_music_author);
        }
    }
}

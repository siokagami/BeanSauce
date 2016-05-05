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
import com.siokagami.beansauce.model.Musics;
import com.siokagami.beansauce.model.Rating;
import com.siokagami.beansauce.model.Subjects;
import com.siokagami.beansauce.utils.UIUtil;

import java.util.List;

/**
 * Created by SiOKagami on 2016/5/5.
 */
public class SearchMovieListAdapter extends RecyclerView.Adapter<SearchMovieListAdapter.ViewHolder>
{
    Context context;
    List<Subjects> listpath;
    private LayoutInflater mInflater;

    public SearchMovieListAdapter(Context context, List<Subjects> listpath) {
        this.context = context;
        this.listpath = listpath;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public SearchMovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_search_movie_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchMovieListAdapter.ViewHolder holder, final int position) {
        String casts = "演员名：";
        String directors = "导演：";
        for(int i = 0;i<listpath.get(position).getCasts().size();i++)
        {
            if(listpath.get(position).getCasts().get(i).getName()==null)
            {
                casts = "演员名：暂无信息";
            }
            else {
                casts += listpath.get(position).getCasts().get(i).getName() + " ";
            }
        }
        for(int i = 0;i<listpath.get(position).getDirectors().size();i++)
        {
            if(listpath.get(position).getDirectors().get(i).getNames()==null)
            {
                directors = "导演：暂无信息";
            }
            else {
                directors+=listpath.get(position).getDirectors().get(i).getNames()+" ";
            }

        }
        holder.itemMovieTitle.setText(listpath.get(position).getTitle());
        holder.itemMovieYear.setText(listpath.get(position).getYear());
        holder.itemMovieRating.setText(listpath.get(position).getRating().getAverage()+"分");
        holder.itemMovieCasts.setText(casts);
        holder.itemMovieDirectors.setText(directors);
        Glide.with(context).load(listpath.get(position).getImages().getMedium())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_data_empty)
                .centerCrop()
                .into(holder.itemMoviePic);

        holder.itemSearchMovieRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtil.showMovieProfileActivity(context,listpath.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listpath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout itemSearchMovieRoot;
        private ImageView itemMoviePic;
        private TextView itemMovieTitle;
        private TextView itemMovieDirectors;
        private TextView itemMovieYear;
        private TextView itemMovieCasts;



        private TextView itemMovieRating;
        public ViewHolder(View itemView) {
            super(itemView);
            itemSearchMovieRoot = (LinearLayout) itemView.findViewById(R.id.item_search_movie_root);
            itemMoviePic = (ImageView) itemView.findViewById(R.id.item_movie_pic);
            itemMovieTitle = (TextView) itemView.findViewById(R.id.item_movie_title);
            itemMovieDirectors = (TextView) itemView.findViewById(R.id.item_movie_directors);
            itemMovieYear = (TextView) itemView.findViewById(R.id.item_movie_year);
            itemMovieCasts = (TextView) itemView.findViewById(R.id.item_movie_casts);
            itemMovieRating = (TextView) itemView.findViewById(R.id.item_movie_rating);
        }
    }
}

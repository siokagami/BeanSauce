package com.siokagami.beansauce.view;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siokagami.beansauce.R;

public class GeneralListViewAdapter extends RecyclerView.Adapter {
    private static final int TYPE_MORE = Integer.MAX_VALUE;
    private RecyclerView.Adapter adapter;
    private int loadState = GeneralListView.STATE_SUCCESS;
    private LayoutInflater inflater;
    public GeneralListViewAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }
    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 0 && position == getItemCount() - 1) return TYPE_MORE;
        else return adapter.getItemViewType(position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_MORE) {
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            return new MoreLoadViewHolder(inflater.inflate(R.layout.list_more_load, parent, false));
        } else return adapter.onCreateViewHolder(parent, viewType);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemCount() > 0 && position == getItemCount() - 1) {
            MoreLoadViewHolder moreLoadViewHolder = (MoreLoadViewHolder) holder;
            if (loadState == GeneralListView.STATE_LOADING) {
                moreLoadViewHolder.moreLoadProgress.setVisibility(View.VISIBLE);
                moreLoadViewHolder.moreLoadTextView.setVisibility(View.VISIBLE);
                moreLoadViewHolder.moreLoadTextView.setText("加载中，请稍后...");
                loadState = GeneralListView.STATE_SUCCESS;
            } else if (loadState == GeneralListView.STATE_FAIL) {
                moreLoadViewHolder.moreLoadProgress.setVisibility(View.GONE);
                moreLoadViewHolder.moreLoadTextView.setVisibility(View.VISIBLE);
                moreLoadViewHolder.moreLoadTextView.setText("加载失败");
                loadState = GeneralListView.STATE_SUCCESS;
            } else if (loadState == GeneralListView.STATE_EMPTY) {
                moreLoadViewHolder.moreLoadProgress.setVisibility(View.GONE);
                moreLoadViewHolder.moreLoadTextView.setVisibility(View.VISIBLE);
                moreLoadViewHolder.moreLoadTextView.setText("没有数据啦");
                loadState = GeneralListView.STATE_SUCCESS;
            } else {
                moreLoadViewHolder.moreLoadProgress.setVisibility(View.GONE);
                moreLoadViewHolder.moreLoadTextView.setVisibility(View.GONE);
                loadState = GeneralListView.STATE_SUCCESS;
            }
        } else adapter.onBindViewHolder(holder, position);
    }
    @Override
    public int getItemCount() {
        if (adapter != null && adapter.getItemCount() > 0) return adapter.getItemCount() + 1;
        else return 0;
    }
    public int getLoadState() {
        return loadState;
    }
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }
    private class MoreLoadViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar moreLoadProgress;
        private TextView moreLoadTextView;
        public MoreLoadViewHolder(View itemView) {
            super(itemView);
            moreLoadProgress = (ProgressBar) itemView.findViewById(R.id.list_more_load_progress);
            moreLoadTextView = (TextView) itemView.findViewById(R.id.list_more_load_txt);
        }
    }
}
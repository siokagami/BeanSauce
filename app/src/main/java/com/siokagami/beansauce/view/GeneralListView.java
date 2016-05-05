package com.siokagami.beansauce.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.siokagami.beansauce.R;

public class GeneralListView extends FrameLayout {
  public static final int STATE_LOADING = 0x01;
  public static final int STATE_FAIL = 0x02;
  public static final int STATE_EMPTY = 0x03;
  public static final int STATE_SUCCESS = 0x04;
  private int loadState = STATE_SUCCESS;
  private View initLoadingView;
  private View initFailView;
  private View initEmptyView;
  private SwipeRefreshLayout refreshLayout;
  private RecyclerView recyclerView;
  private GeneralListViewAdapter adapter;
  private LinearLayoutManager layoutManager;
  private RecyclerView.LayoutManager manager;
  private int currentPage = 0;
  private OnLoadDataListener onLoadDataListener;
  private boolean isPaging = true;
  public GeneralListView(Context context) {
    super(context);
    init();
  }
  public GeneralListView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }
  public GeneralListView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }
  private void init() {
    createView();
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      private int lastVisibleItem = 0;
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (isPaging
            && newState == RecyclerView.SCROLL_STATE_IDLE
            && layoutManager.findLastCompletelyVisibleItemPosition()
            == layoutManager.getItemCount() - 1) {
          System.gc();
          if (onLoadDataListener != null
              && adapter != null
              && adapter.getLoadState() == STATE_SUCCESS) {
            adapter.setLoadState(STATE_LOADING);
            onLoadDataListener.onLoadData(currentPage + 1);
          }
        }
      }
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = layoutManager.findLastVisibleItemPosition();
      }
    });
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        if (onLoadDataListener != null) {
          currentPage = 0;
          onLoadDataListener.onLoadData(currentPage + 1);
        }
      }
    });
  }
  private void createView() {
    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    layoutManager = new LinearLayoutManager(getContext());
    refreshLayout = new SwipeRefreshLayout(getContext());
    refreshLayout.setLayoutParams(params);
    refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
        android.R.color.holo_red_light, android.R.color.holo_orange_light,
        android.R.color.holo_green_light);
    recyclerView = new RecyclerView(getContext());
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutParams(params);
    recyclerView.setLayoutManager(layoutManager);
    refreshLayout.addView(recyclerView);
    addView(refreshLayout);
    setInitLoadingView(R.layout.list_init_loading);
    setInitFailView(R.layout.list_init_fail);
    setInitEmptyView(R.layout.list_init_empty);
  }
  public void setAdapter(RecyclerView.Adapter adapter) {
    this.adapter = new GeneralListViewAdapter(adapter);
    recyclerView.setAdapter(this.adapter);
  }
  public View setInitLoadingView(int resId) {
    View view = LayoutInflater.from(getContext()).inflate(resId, this, false);
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    view.setLayoutParams(params);
    addView(view);
    initLoadingView = view;
    return view;
  }
  public View setInitFailView(int resId) {
    View view = LayoutInflater.from(getContext()).inflate(resId, this, false);
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    view.setLayoutParams(params);
    addView(view);
    initFailView = view;
    return view;
  }
  public View setInitEmptyView(int resId) {
    View view = LayoutInflater.from(getContext()).inflate(resId, this, false);
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    view.setLayoutParams(params);
    addView(view);
    initEmptyView = view;
    return view;
  }
  public void setOnLoadDataListener(OnLoadDataListener onLoadDataListener) {
    this.onLoadDataListener = onLoadDataListener;
  }
  public void setLoadState(int loadState) {
    refreshLayout.setRefreshing(false);
    this.loadState = loadState;
    if (adapter != null) {
      if (loadState == STATE_SUCCESS) {
        currentPage = currentPage + 1;
      }
      if (adapter.getItemCount() == 0) {
        if (loadState == STATE_LOADING) {
          recyclerView.setVisibility(GONE);
          initLoadingView.setVisibility(VISIBLE);
          initFailView.setVisibility(GONE);
          initEmptyView.setVisibility(GONE);
        } else if (loadState == STATE_FAIL) {
          recyclerView.setVisibility(GONE);
          initLoadingView.setVisibility(GONE);
          initFailView.setVisibility(VISIBLE);
          initEmptyView.setVisibility(GONE);
        } else if (loadState == STATE_EMPTY) {
          recyclerView.setVisibility(GONE);
          initLoadingView.setVisibility(GONE);
          initFailView.setVisibility(GONE);
          initEmptyView.setVisibility(VISIBLE);
        } else {
          recyclerView.setVisibility(VISIBLE);
          initLoadingView.setVisibility(GONE);
          initFailView.setVisibility(GONE);
          initEmptyView.setVisibility(GONE);
        }
      } else {
        recyclerView.setVisibility(VISIBLE);
        initLoadingView.setVisibility(GONE);
        initFailView.setVisibility(GONE);
        initEmptyView.setVisibility(GONE);
        adapter.setLoadState(loadState);
      }
    }
  }
  public void setLayoutManager(final RecyclerView.LayoutManager layoutManager) {
    if (layoutManager instanceof GridLayoutManager) {
      ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          if (position == adapter.getItemCount() - 1) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
          } else {
            return 1;
          }
        }
      });
      recyclerView.setLayoutManager(layoutManager);
    } else if (layoutManager instanceof LinearLayoutManager) {
      recyclerView.setLayoutManager(layoutManager);
    }
  }
  public void startLoadData() {
    if (onLoadDataListener != null) {
      setLoadState(STATE_LOADING);
      currentPage = 0;
      onLoadDataListener.onLoadData(currentPage + 1);
    }
  }
  public void reloadData() {
    refreshLayout.setRefreshing(true);
    currentPage = 0;
    onLoadDataListener.onLoadData(currentPage + 1);
  }
  public void setPaging(boolean isPaging) {
    this.isPaging = isPaging;
  }
  public GeneralListViewAdapter getWrapper() {
    return adapter;
  }
  public RecyclerView getRecyclerView() {
    return recyclerView;
  }
  public interface OnLoadDataListener {
    void onLoadData(int page);
  }
}
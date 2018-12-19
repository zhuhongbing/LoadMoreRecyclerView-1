package com.zss.library;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.zss.library.adapter.CommonAdapter;


/**
 * Created by zm on 2017/9/26.
 */

public class LoadMoreRecyclerView extends RecyclerView{

    protected LayoutType LayoutType;

    private int[] lastPositions;

    private int lastVisibleItemPosition;

    private int currentScrollState = 0;

    private LoadingFooter loadingFooter;

    private OnLoadMoreListener listener;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = getLayoutManager();
                if (LayoutType == null) {
                    if (layoutManager instanceof LinearLayoutManager) {
                        LayoutType = LayoutType.LinearLayout;
                    } else if (layoutManager instanceof GridLayoutManager) {
                        LayoutType = LayoutType.GridLayout;
                    } else {
                        LayoutType = LayoutType.StaggeredGridLayout;
                    }
                }
                switch (LayoutType) {
                    case LinearLayout:
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        break;
                    case GridLayout:
                        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        break;
                    case StaggeredGridLayout:
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        if (lastPositions == null) {
                            lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                        }
                        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                        lastVisibleItemPosition = findLastPosition(lastPositions);
                        break;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                currentScrollState = newState;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
                    if(listener != null){
                        if(loadingFooter != null && loadingFooter.getState() == LoadingFooter.State.LoadComplete){
                            loadingFooter.setState(LoadingFooter.State.Loading);
                            listener.onLoadMore();
                        }
                    }
                }
            }
        });
    }

    private int findLastPosition(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public enum LayoutType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.listener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter instanceof CommonAdapter){
            CommonAdapter commonAdapter = (CommonAdapter)adapter;
            RecyclerAdapterWrapper adapterWrapper = new RecyclerAdapterWrapper(commonAdapter);
            loadingFooter = new LoadingFooter(getContext());
            loadingFooter.setState(LoadingFooter.State.LoadComplete);
            adapterWrapper.addFooterView(loadingFooter);
            super.setAdapter(adapterWrapper);
        }else{
            super.setAdapter(adapter);
        }
    }

    public void setState(LoadingFooter.State status ) {
        loadingFooter.setState(status);
    }

    public void setState(LoadingFooter.State status, boolean showView) {
        loadingFooter.setState(status, showView);
    }

    public void setNoMoreData(boolean showView){
        loadingFooter.setState(LoadingFooter.State.TheEnd, showView);
    }

    public void setNetWorkError(View.OnClickListener listener){
        loadingFooter.setOnClickListener(listener);
        loadingFooter.setState(LoadingFooter.State.NetWorkError);
    }

    public void setLoadComplete(){
        loadingFooter.setState(LoadingFooter.State.LoadComplete);
    }
}

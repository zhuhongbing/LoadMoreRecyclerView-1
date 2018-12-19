package com.zss.loadmore;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.zss.library.LoadMoreRecyclerView;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 网格布局用法
 */
public class GridActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private CommonAdapter adapter;
    private int pageIndex = 0;
    private int pageSize = 30;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore_recyclerview);
        initView();
        initData();
    }

    public void initView() {

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        //改变加载显示的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        //设置向下拉多少出现刷新
        swipeRefreshLayout.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipeRefreshLayout.setProgressViewEndTarget(false, 200);

        recyclerView = (LoadMoreRecyclerView)findViewById(R.id.recyclerView);
        //设置网络布局
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    public void initData() {
        adapter = new CommonAdapter<String>(R.layout.item_recyclerview) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView title = viewHolder.findViewById(R.id.title);
                title.setText(item);
            }

            @Override
            protected void onItemClick(View view, String item, int position) {
                // TODO: 点击列表项
            }
        };
        recyclerView.setAdapter(adapter);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() { //延时1秒，模拟数据
                    @Override
                    public void run() {
                        pageIndex = 0;
                        adapter.replaceAll(getData());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });

        //加载更多
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(pageIndex >= 2){
                    recyclerView.setNoMoreData(true); //已经到底了
                }else{
                    mHandler.postDelayed(new Runnable() { //延时1秒，模拟数据
                        @Override
                        public void run() {
                            pageIndex++;
                            adapter.addAll(getData());
                            recyclerView.setLoadComplete(); //加载完成
                        }
                    }, 1000);
                }

            }
        });

        adapter.addAll(getData()); //进入界面加载数据
    }

    public List<String> getData(){ //模拟分页
        List<String> list = new ArrayList<>();
        int start = (pageIndex * pageSize);
        int end = (pageIndex + 1) * pageSize;
        while (start < end){
            start++;
            list.add("张三水"+start);
        }
        return list;
    }
}

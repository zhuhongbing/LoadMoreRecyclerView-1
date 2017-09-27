package com.zss.loadmore;


import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zss.library.LoadMoreRecyclerView;
import com.zss.library.LoadingFooter;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CommonAdapter adapter;
    private int index = 0;
    private int count = 30;
    private Button linearBtn, gridBtn, staggeredBtn;
    private FrameLayout frameLayout;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    public void initView() {
        linearBtn = (Button) findViewById(R.id.linearBtn);
        gridBtn = (Button) findViewById(R.id.gridBtn);
        staggeredBtn = (Button) findViewById(R.id.staggeredBtn);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        //改变加载显示的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        //设置向下拉多少出现刷新
        swipeRefreshLayout.setDistanceToTriggerSync(100);
        //设置刷新出现的位置
        swipeRefreshLayout.setProgressViewEndTarget(false, 200);
    }

    public void initData() {
        linearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = new LoadMoreRecyclerView(MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);
                frameLayout.removeAllViews();
                frameLayout.addView(recyclerView);
                addEventListener();
                index = 0;
                adapter.replaceAll(getData());
            }
        });

        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = new LoadMoreRecyclerView(MainActivity.this);
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                recyclerView.setAdapter(adapter);
                frameLayout.removeAllViews();
                frameLayout.addView(recyclerView);
                addEventListener();
                index = 0;
                adapter.replaceAll(getData());
            }
        });

        staggeredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView = new LoadMoreRecyclerView(MainActivity.this);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                recyclerView.setAdapter(adapter);
                frameLayout.removeAllViews();
                frameLayout.addView(recyclerView);
                addEventListener();
                index = 0;
                adapter.replaceAll(getData());
            }
        });

        adapter = new CommonAdapter<String>(R.layout.item_recyclerview) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView title = viewHolder.findViewById(R.id.title);
                title.setText(item);
                if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){ // 动态高度
                    viewHolder.getView().getLayoutParams().height = position%2 == 0 ? 300 : 200;
                }
            }

            @Override
            protected void onItemClick(View view, String item, int position) {

            }
        };

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index = 0;
                        adapter.replaceAll(getData());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });
    }

    public void addEventListener(){
        //加载更多
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(index == 2){
                    recyclerView.setState(LoadingFooter.State.TheEnd); // 已经到底了
                }else{
                    mHandler.postDelayed(new Runnable() { //模拟数据
                        @Override
                        public void run() {
                            index++;
                            adapter.addAll(getData());
                            recyclerView.setState(LoadingFooter.State.LoadComplete); //加载完成
                        }
                    }, 1000);
                }
            }
        });
    }

    public List<String> getData(){
        List<String> list = new ArrayList<>();
        int i = index * 10;
        int total = i + count;
        for(; i< total; i++){
            list.add("张三水"+i);
        }
        return list;
    }
}

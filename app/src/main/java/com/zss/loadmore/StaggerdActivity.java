package com.zss.loadmore;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.zss.library.LoadMoreRecyclerView;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 交错网格布局用法
 */
public class StaggerdActivity extends AppCompatActivity {

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

        //设置交错网络布局
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

    }

    public void initData() {
        adapter = new CommonAdapter<String>(R.layout.item_recyclerview) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView title = viewHolder.findViewById(R.id.title);
                title.setText(item);
                //设置动态高度
                if(position % 2 == 0){
                    viewHolder.getView().getLayoutParams().height = 300;
                    viewHolder.getView().setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.line));
                }else if(position % 3 == 0){
                    viewHolder.getView().getLayoutParams().height = 250;
                    viewHolder.getView().setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
                } else{
                    viewHolder.getView().getLayoutParams().height = 200;
                    viewHolder.getView().setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.blue));
                }
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
                    recyclerView.setTheEnd(); //已经到底了
                }else{
                    mHandler.postDelayed(new Runnable() { //延时1秒，模拟数据
                        @Override
                        public void run() {
                            pageIndex++;
                            adapter.addAll(getData());
                            recyclerView.setLoadingComplete(); //加载完成
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

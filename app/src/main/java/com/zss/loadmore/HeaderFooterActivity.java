package com.zss.loadmore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zss.library.RecyclerAdapterWrapper;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HeaderFooterActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initView();
    }

    public void initView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //设置线性布局
        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        CommonAdapter<MsgModel> adapter = new CommonAdapter<MsgModel>(R.layout.item_adapter) {
            @Override
            protected void convert(ViewHolder viewHolder, MsgModel item, int position) {
                TextView title = viewHolder.findViewById(R.id.tv_title);
                TextView desc = viewHolder.findViewById(R.id.tv_desc);
                title.setText(item.title);
                desc.setText(item.desc);
            }

            @Override
            protected void onItemClick(View view, MsgModel item, int position) {

            }
        };
        RecyclerAdapterWrapper adapterWrapper = new RecyclerAdapterWrapper(adapter);
        adapterWrapper.addHeaderView(new ItemHeader(this));
        adapterWrapper.addFooterView(new ItemFooter(this));
        recyclerView.setAdapter(adapterWrapper);
        adapter.addAll(getData());
    }

    public List<MsgModel> getData(){ //模拟数据
        List<MsgModel> list = new ArrayList<>();
        int start = 0;
        int end = 8;
        while (start < end){
            start++;
            MsgModel msg = new MsgModel();
            msg.title = "title" + start;
            msg.desc = "desc" + start;
            list.add(msg);
        }
        return list;
    }

}

package com.zss.loadmore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button linearBtn, gridBtn, staggeredBtn, adapterBtn, headerFooterBtn;

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
        adapterBtn = (Button) findViewById(R.id.adapterBtn);
        headerFooterBtn = (Button) findViewById(R.id.headerFooterBtn);
    }

    public void initData() {

        linearBtn.setOnClickListener(this);

        gridBtn.setOnClickListener(this);

        staggeredBtn.setOnClickListener(this);

        adapterBtn.setOnClickListener(this);

        headerFooterBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.linearBtn:
                intent = new Intent(getBaseContext(), LinearActivity.class);
                break;
            case R.id.gridBtn:
                intent = new Intent(getBaseContext(), GridActivity.class);
                break;
            case R.id.staggeredBtn:
                intent = new Intent(getBaseContext(), StaggerdActivity.class);
                break;
            case R.id.adapterBtn:
                intent = new Intent(getBaseContext(), AdapterActivity.class);
                break;
            case R.id.headerFooterBtn:
                intent = new Intent(getBaseContext(), HeaderFooterActivity.class);
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }

}

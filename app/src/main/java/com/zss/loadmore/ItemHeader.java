package com.zss.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by zm on 2017/9/28.
 */

public class ItemHeader extends LinearLayout{
    public ItemHeader(Context context) {
        super(context);
        init();
    }

    public ItemHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.item_header, this);
    }
}

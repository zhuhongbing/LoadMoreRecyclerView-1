android系统自带下拉刷新和加载更多、公用RecyclerView适配器，RecyclerView添加Header、footer

1. 线性布局用法
recyclerView = new LoadMoreRecyclerView(MainActivity.this);
recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
recyclerView.setAdapter(adapter);

2. 网格布局用法
recyclerView = new LoadMoreRecyclerView(MainActivity.this);
recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
recyclerView.setAdapter(adapter);

3. 交错的网格布局用法
recyclerView = new LoadMoreRecyclerView(MainActivity.this);
recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
recyclerView.setAdapter(adapter);

//下拉刷新
swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
     @Override
     public void onRefresh() {
     }
});

//加载更多
recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
     @Override
     public void onLoadMore() {
     }
});

更多安卓代码：https://github.com/zss945/android-zframe
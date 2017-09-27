android系统自带下拉刷新和加载更多

1. 线性布局用法
recyclerView = new LoadMoreRecyclerView(MainActivity.this);
recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
recyclerView.setAdapter(adapter);

2. 网格布局用法
recyclerView = new LoadMoreRecyclerView(MainActivity.this);
recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
recyclerView.setAdapter(adapter);

3. 交错的网格布局用法
recyclerView = new LoadMoreRecyclerView(MainActivity.this);
recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
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

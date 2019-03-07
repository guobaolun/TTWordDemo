package com.storm.common.mvp;

import java.util.ArrayList;

/**
 * @author guobaolun
 * @since 2018/5/12
 */
public interface BaseListView<T> {
    void setListData(T t);
    void showRecyclerView();
    void showReloadView();
    void showProgressView();
    void setRecyclerRefreshing(boolean loadingMore);
    void setPage(int page);

}

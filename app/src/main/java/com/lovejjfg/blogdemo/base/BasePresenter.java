package com.lovejjfg.blogdemo.base;

/**
 * Created by zhangjun on 2016-03-10.
 * There are some base methods for Presenter in MVP.
 * At most,it's more about lifeRecycle.
 * You should make your PresenterInterface Extend this.
 *
 */
public interface BasePresenter {
    void onStart();

    void onResume();

    void onDestroy();

    void onBackClick();
}

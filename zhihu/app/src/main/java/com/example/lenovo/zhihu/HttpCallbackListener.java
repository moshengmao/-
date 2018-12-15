package com.example.lenovo.zhihu;

/**
 * Created by lenovo on 2018/12/15.
 */

public interface HttpCallbackListener {
    void onFinish (String response);
    void onError(Exception e);
}

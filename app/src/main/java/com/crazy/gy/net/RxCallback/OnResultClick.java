package com.crazy.gy.net.RxCallback;

/**
 * Created by jqs on 2018/1/3.
 */

public interface OnResultClick<T> {
    void success(T t);
    void fail(Throwable throwable);
}

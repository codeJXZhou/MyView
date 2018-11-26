package cn.zjx.myview.mvp;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

/**
 * @author zjx on 2018/9/10.
 */
public abstract class IBasePresenter<T> {
    protected Reference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new SoftReference<T>(view);
    }

    protected T getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
    }

}

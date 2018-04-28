package com.example.guet.sharehotel.base;

/**
 * @auth ${user}
 * time 2018/4/24 10:09
 */
public class BasePresenter<V> {

   // protected WeakReference mViewRef;
    private V mView;

    //进行绑定
    public void attackView(V view){
        mView=view;
        //mViewRef=new WeakReference(view);
    }

    //进行解绑
    public void detackView(){
        this.mView=null;

        /*if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }*/
    }

    /**
     * 是否与View建立连接
     *
     * @return
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * 获取当前连接的View
     *
     * @return
     */
    public V getMvpView() {
        return mView;
    }


    /**
     * 每次调用业务请求的时候都要先调用方法检查是否与View建立连接，没有则抛出异常
     */
    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new BaseViewNotAttachedException();
        }
    }

    public static class BaseViewNotAttachedException extends RuntimeException {
        public BaseViewNotAttachedException() {
            super("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
        }
    }

}

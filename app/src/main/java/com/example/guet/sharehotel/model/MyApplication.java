package com.example.guet.sharehotel.model;

import android.app.Application;
import android.content.Context;

/**
 * 用做Toast，Log等的Context参数的使用。以及一些初始化。
 */
public class MyApplication extends Application {

    private static Context context;
    private Boolean isLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //在应用中配置ImageLoaderConfiguration参数（只能配置一次，如多次配置，则默认第一次的配置参数）
       /* File cacheDir = StorageUtils.getCacheDirectory(context);    //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
     //           .taskExecutor(...)
    //    .taskExecutorForCachedImages(...)
        .threadPoolSize(3) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
    //            .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder()) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建

        ImageLoader.getInstance().init(config);      //初始化
        ImageLoader imageLoader = ImageLoader.getInstance();   //得到实例，使用的是单例模式

        */

        //使用默认配置
        //创建默认的ImageLoader配置参数
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        //使用configuration初始化ImageLoader
//        ImageLoader.getInstance().init(configuration);


    }

    public static Context getContext() {
        return context;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }
}

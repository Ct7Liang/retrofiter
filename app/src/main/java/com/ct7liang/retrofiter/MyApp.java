package com.ct7liang.retrofiter;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018-12-20.
 *
 */
public class MyApp extends Application {

    public static Retrofit retrofit;

    @Override
    public void onCreate() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)    //设置连接最大时长
                .readTimeout(180, TimeUnit.SECONDS)     //设置读取最大时长
                .writeTimeout(180, TimeUnit.SECONDS)    //设置写入最大时长
                .retryOnConnectionFailure(true)     //默认重试一次，若需要重试N次，则要实现拦截器
                .cache(new Cache(this.getCacheDir(), 10*1024*1024)) //为OkHttpClient设置Cache，否则缓存不会生效（retrofit并未置默认缓存目录）
                .addInterceptor(new RetryInterceptor(3))
                .build();

//        addNetworkInterceptor添加的是网络拦截器，在网络畅通的时候会调用，而addInterceptor则都会调用。

        retrofit = new Retrofit.Builder()
                    //设置网络请求的网络路径,必须以"/"结束,否则会报错
                .baseUrl("http://47.96.3.246:3080/zzd_cp/")
                    //非必须设置,用于@Body注解和自定义请求返回值类型
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    //设置请求client, client可以设置超时时间和重试
                .client(client)
                .build();

        super.onCreate();
    }



    /**
     * 自定义拦截器 - 重试
     */
    private class RetryInterceptor implements Interceptor{

        private int current = 0;    //当前已经重试的次数
        private int max = 3;    //最大重试次数, 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        public RetryInterceptor(int max) {
            this.max = max;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            //在发出请求之前截获请求
            Request request = chain.request();

            //将截获到的请求做指定的处理

            //将处理过的请求再次发出, 并截获回应
            Response response = chain.proceed(request);

            //讲截获到的回应做处理

                //设置重试N次
            while (!response.isSuccessful() && current < max) {
                current++;
                response = chain.proceed(request);
            }

                //设置缓存, 拦截器里面设置为全局缓存
            response = response.newBuilder().header("Cache-Control", "public,max-age=120").build();

            //将处理后的回应返回给客户端
            return response;
        }
    }


    /**
     * 设置没有网络的情况下，
     *  的缓存时间
     *  通过：addInterceptor 设置
     */
    public static class CommonNoNetCache implements Interceptor {

//        在无网络的情况下读取缓存，而且网络下的缓存也有过期时间，有网络的情况下根据缓存的过期时间重新请求

        private int maxCacheTimeSecond = 0;
        private Context applicationContext;

        public CommonNoNetCache(int maxCacheTimeSecond, Context applicationContext) {
            this.maxCacheTimeSecond = maxCacheTimeSecond;
            this.applicationContext = applicationContext;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            if (!isConnected(applicationContext)) {
                CacheControl tempCacheControl = new CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(maxCacheTimeSecond, TimeUnit.SECONDS)
                        .build();
                request = request.newBuilder()
                        .cacheControl(tempCacheControl)
                        .build();
            }
            return chain.proceed(request);
        }
    }


    /**
     * 设置在有网络的情况下的缓存时间
     *  在有网络的时候，会优先获取缓存
     * 通过：addNetworkInterceptor 设置
     */
    public static class CommonNetCache implements Interceptor {

        private int maxCacheTimeSecond = 0;

        public CommonNetCache(int maxCacheTimeSecond) {
            this.maxCacheTimeSecond = maxCacheTimeSecond;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxCacheTimeSecond)
                    .build();
        }

//        有网络的情况下，缓存时间是：20秒。也就是在20秒内的请求都是获取本地的缓存。当网络断开后，会设置一个离线的缓存，为4周。
    }

    /**
     * 关于max-age和max-stale
     * maxAge ：设置最大失效时间，失效则不使用
     * maxStale ：设置最大失效时间，失效则不使用
     * max-stale在请求头设置有效，在响应头设置无效。
     * max-stale和max-age同时设置的时候，缓存失效的时间按最长的算。
     */


    /**
     * 判断网络是否连接
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @param context 上下文
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }
    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


}

package com.journey.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.journey.network.errorhandler.AppDataErrorHandler;
import com.journey.network.errorhandler.HttpErrorHandler;
import com.journey.network.gsonconverterfactory.DoubleDefaultAdapter;
import com.journey.network.gsonconverterfactory.GsonConverterFactory;
import com.journey.network.gsonconverterfactory.IntegerDefaultAdapter;
import com.journey.network.gsonconverterfactory.LongDefaultAdapter;
import com.journey.network.gsonconverterfactory.StringNullAdapter;
import com.journey.network.interceptor.RequestInterceptor;
import com.journey.network.interceptor.ResponseInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * 网络链接一般有多个域名，再次里面可以做些配置
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public abstract class ApiBase {
    protected Retrofit retrofit;
    protected static INetworkRequestInfo networkRequestInfo;
    private static ErrorTransformer sErrorTransformer = new ErrorTransformer();
    private static RequestInterceptor sHttpsRequestInterceptor;
    private static ResponseInterceptor sHttpsResponseInterceptor;
    private static Gson gson;
    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     *
     * @return
     */
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(Double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(Long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(String.class, new StringNullAdapter())
                    .create();
        }
        return gson;
    }

    protected ApiBase(String baseUrl) {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(buildGson())).build();//添加json转换框架buildGson()可加可不加
    }

    public static void setNetworkRequestInfo(INetworkRequestInfo requestInfo) {
        networkRequestInfo = requestInfo;
        //初始化网络拦截器
        sHttpsRequestInterceptor = new RequestInterceptor(requestInfo);
        //处理返回数据拦截器
        sHttpsResponseInterceptor = new ResponseInterceptor();
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);

        /*可以统一添加网络参数到请求头*/
        okHttpClient.addInterceptor(sHttpsRequestInterceptor);

        /*网络请求返回的时候的数据处理*/
        okHttpClient.addInterceptor(sHttpsResponseInterceptor);
        setLoggingLevel(okHttpClient);
        OkHttpClient httpClient = okHttpClient.build();
        httpClient.dispatcher().setMaxRequestsPerHost(20);
        return httpClient;
    }

    private void setLoggingLevel(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //BODY打印信息,NONE不打印信息
        logging.setLevel(networkRequestInfo.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
    }

    /**
     * 封装线程管理和订阅的过程
     */
    protected void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .unsubscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(sErrorTransformer)
                .subscribe(observer);
    }

    /**
     * 处理错误的变换
     * 网络请求的错误处理，其中网络错误分为两类：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；
     * 2、http请求正常，但是返回的应用数据里提示发生了异常，表明服务器已经接收到了来自客户端的请求，但是由于
     * 某些原因，服务器没有正常处理完请求，可能是缺少参数，或者其他原因；
     */
    private static class ErrorTransformer<T> implements ObservableTransformer {

        @Override
        public ObservableSource apply(io.reactivex.Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (io.reactivex.Observable<T>) upstream
                    .map(new AppDataErrorHandler())/*返回的数据统一错误处理*/
                    .onErrorResumeNext(new HttpErrorHandler<T>());/*Http 错误处理**/
        }
    }
}

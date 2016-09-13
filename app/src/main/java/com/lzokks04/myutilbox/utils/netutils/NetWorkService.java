package com.lzokks04.myutilbox.utils.netutils;

import com.lzokks04.myutilbox.bean.WeixinInfoBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Liu on 2016/9/12.
 */
public interface NetWorkService {

    @GET("weixin/query")
    Observable<WeixinInfoBean> getData(@Query("pno")int pno,
                                       @Query("ps") int ps,
                                       @Query("dtype") String dtype,
                                       @Query("key") String key) ;

}

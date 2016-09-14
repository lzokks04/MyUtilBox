package com.lzokks04.myutilbox.utils.netutils;

import com.lzokks04.myutilbox.bean.IdcardLeakBean;
import com.lzokks04.myutilbox.bean.IdcardLostBean;
import com.lzokks04.myutilbox.bean.IdcardSearchBean;
import com.lzokks04.myutilbox.bean.QqNumResultBean;
import com.lzokks04.myutilbox.bean.WeixinInfoBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Liu on 2016/9/12.
 */
public interface NetWorkService {

    @GET("weixin/query")
    Observable<WeixinInfoBean> getData(@Query("pno") int pno,
                                       @Query("ps") int ps,
                                       @Query("dtype") String dtype,
                                       @Query("key") String key);

    @GET("idcard/index")
    Observable<IdcardSearchBean> getIdSearchData(@Query("cardno") String cardno,
                                                 @Query("dtype") String dtype,
                                                 @Query("key") String key);

    @GET("idcard/leak")
    Observable<IdcardLeakBean> getIdLeak(@Query("cardno") String cardno,
                                         @Query("dtype") String dtype,
                                         @Query("key") String key);

    @GET("idcard/loss")
    Observable<IdcardLostBean> getIdLoss(@Query("cardno") String cardno,
                                         @Query("dtype") String dtype,
                                         @Query("key") String key);

    @GET("qqevaluate/qq")
    Observable<QqNumResultBean> getQqData(@Query("qq") String qq,
                                          @Query("key") String key);
}

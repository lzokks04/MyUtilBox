package com.lzokks04.myutilbox.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.WeiXinAdapter;
import com.lzokks04.myutilbox.bean.WeiXinDetailBean;
import com.lzokks04.myutilbox.bean.WeixinInfoBean;
import com.lzokks04.myutilbox.utils.netutils.ApiInfo;
import com.lzokks04.myutilbox.utils.netutils.NetWorkService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Liu on 2016/9/7.
 */
public class WeixinJingXuanActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    private static final int STATE_REFRESH = 0;
    private static final int STATE_LOAD_MORE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_target)
    RecyclerView recyclerview;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private WeiXinAdapter adapter;
    private List<WeiXinDetailBean> beanList;
    private List<WeiXinDetailBean> tempList;
    private int page = 1;
    private int state;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin);
        ButterKnife.bind(this);
        state = STATE_REFRESH;
        initToolbar();
        initSwipeToLoadLayout();
        initRecyclerView();
    }

    private void initSwipeToLoadLayout() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        onAutoRefresh();
    }


    private void initToolbar() {
        toolbar.setTitle("微信精选");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        beanList = new ArrayList<WeiXinDetailBean>();
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(llm);
        adapter = new WeiXinAdapter(WeixinJingXuanActivity.this, beanList);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickLitener(new WeiXinAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String targetUrl = beanList.get(position).getUrl();
                Intent intent = new Intent(WeixinJingXuanActivity.this, WebViewActivity.class);
                intent.putExtra("url", targetUrl);
                startActivity(intent);
            }
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = llm.findLastVisibleItemPosition();
                int totalItemCount = llm.getItemCount();
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    state = STATE_LOAD_MORE;
                } else {
                    state = STATE_REFRESH;
                }
            }
        });
    }

    private void getData(int pno, int ps, String dtype, String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInfo.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        service.getData(pno, ps, dtype, key)
                .map(new Func1<WeixinInfoBean, List<WeiXinDetailBean>>() {
                    @Override
                    public List<WeiXinDetailBean> call(WeixinInfoBean weixinInfoBean) {
                        return convert(weixinInfoBean);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WeiXinDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        swipeToLoadLayout.setRefreshing(false);
                        swipeToLoadLayout.setLoadingMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(WeixinJingXuanActivity.this, "网络错误,请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<WeiXinDetailBean> weiXinDetailBeen) {
                        if (state == STATE_REFRESH) {
                            beanList.clear();
                            beanList.addAll(weiXinDetailBeen);
                            adapter.notifyDataSetChanged();
                        } else if (state == STATE_LOAD_MORE) {
                            tempList = weiXinDetailBeen;
                            beanList.addAll(tempList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                getData(1 + page, 20, "json", ApiInfo.WEIXIN_KEY);
                page += 1;
            }
        }, 1000);

    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                getData(1, 20, "json", ApiInfo.WEIXIN_KEY);
                page = 1;
            }
        }, 1000);
    }

    private void onAutoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
                getData(1, 20, "json", ApiInfo.WEIXIN_KEY);
                page = 1;
            }
        });
    }

    private List<WeiXinDetailBean> convert(WeixinInfoBean bean) {
        List<WeiXinDetailBean> beanList = new ArrayList<WeiXinDetailBean>();
        for (int i = 0; i < bean.getResult().getList().size(); i++) {
            WeiXinDetailBean weiXinDetailBean = new WeiXinDetailBean();
            weiXinDetailBean.setFirstImg(bean.getResult().getList().get(i).getFirstImg());
            weiXinDetailBean.setSource(bean.getResult().getList().get(i).getSource());
            weiXinDetailBean.setTitle(bean.getResult().getList().get(i).getTitle());
            weiXinDetailBean.setUrl(bean.getResult().getList().get(i).getUrl());
            beanList.add(weiXinDetailBean);
        }
        return beanList;
    }
}

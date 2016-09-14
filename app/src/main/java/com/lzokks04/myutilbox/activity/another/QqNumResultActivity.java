package com.lzokks04.myutilbox.activity.another;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.bean.QqNumResultBean;
import com.lzokks04.myutilbox.utils.netutils.ApiInfo;
import com.lzokks04.myutilbox.utils.netutils.NetWorkService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Liu on 2016/9/14.
 */
public class QqNumResultActivity extends AppCompatActivity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.tv_conclusion)
    TextView tvConclusion;
    @BindView(R.id.tv_analysis)
    TextView tvAnalysis;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqnumresult);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle("QQ号码测试");
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

    @OnClick(R.id.btn_enter)
    public void onClick() {
        String result = etInput.getText().toString();
        if (!TextUtils.isEmpty(result)) {
            tvNum.setText("");
            llInfo.setVisibility(View.GONE);
            getData(result, ApiInfo.QQ_KEY);
        } else {
            showDialog();
        }
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("QQ号码不能为空")
                .setCancelable(false)
                .setPositiveButton("确定", null);
        builder.show();
    }

    private void getData(final String qq, String key) {
        progressbar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInfo.QQ_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        service.getQqData(qq, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QqNumResultBean>() {
                    @Override
                    public void onCompleted() {
                        progressbar.setVisibility(View.GONE);
                        llInfo.setVisibility(View.VISIBLE);
                        etInput.setText("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        tvConclusion.setText("QQ号码有误或网络环境错误");
                        tvAnalysis.setText("");
                        llInfo.setVisibility(View.VISIBLE);
                        etInput.setText("");
                        progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(QqNumResultBean qqNumResultBean) {
                        tvNum.setText("测试号码:" + qq);
                        tvConclusion.setText(qqNumResultBean.getResult().getData().getConclusion());
                        tvAnalysis.setText(qqNumResultBean.getResult().getData().getAnalysis());
                    }
                });
    }
}

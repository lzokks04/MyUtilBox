package com.lzokks04.myutilbox.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.bean.IdcardLeakBean;
import com.lzokks04.myutilbox.utils.netutils.ApiInfo;
import com.lzokks04.myutilbox.utils.netutils.NetWorkService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Liu on 2016/9/14.
 */
public class IdcardIsLeakFragment extends Fragment {
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_security)
    TextView tvSecurity;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    private Unbinder unBinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_idcard_isleak, container, false);
        unBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    @OnClick(R.id.btn_enter)
    public void onClick() {
        String result = etInput.getText().toString();
        if (!TextUtils.isEmpty(result)) {
            llInfo.setVisibility(View.GONE);
            getData(result, "json", ApiInfo.ID_KEY);
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("身份证号码不能为空")
                .setCancelable(false)
                .setPositiveButton("确定", null);
        builder.show();
    }

    private void getData(String id, String type, String key) {
        progressbar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInfo.ID_BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        service.getIdLeak(id, type, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IdcardLeakBean>() {
                    @Override
                    public void onCompleted() {
                        progressbar.setVisibility(View.GONE);
                        llInfo.setVisibility(View.VISIBLE);
                        etInput.setText("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        tvId.setText("身份证号码有误或网络环境错误");
                        tvSecurity.setText("");
                        llInfo.setVisibility(View.VISIBLE);
                        etInput.setText("");
                        progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(IdcardLeakBean idcardLeakBean) {
                        tvId.setText("身份证号码:" + idcardLeakBean.getResult().getCardno());
                        tvSecurity.setText("状态:" + idcardLeakBean.getResult().getTips());
                    }
                });
    }
}

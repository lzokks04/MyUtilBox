package com.lzokks04.myutilbox.activity.iptool;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.utils.IpUtils;
import com.lzokks04.myutilbox.utils.MatcheIpAddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Liu on 2016/9/9.
 */
public class PingActivity extends AppCompatActivity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.tv_host)
    TextView tvHost;
    @BindView(R.id.tv_target)
    TextView tvTarget;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_ipinfo)
    TextView tvIpinfo;

    private IpUtils ipUtils = IpUtils.getInstance();
    private String result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle("Ping延迟查询测试");
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
        result = etInput.getText().toString();
        disTv();
        resultClear();
        if (!TextUtils.isEmpty(result)) {
            progressbar.setVisibility(View.VISIBLE);
            //是ip的情况
            if (MatcheIpAddress.isIpAddress(result)) {
                tvTarget.setText("目标IP:" + result);
                new GetPingAsyncTask().execute(result);
                new IpInfoSearch().execute(result);
            } else {//是域名的情况
                tvHost.setText("域名:" + result);
                new HostToIpAsyncTask().execute(result);

            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请输入ip地址/域名")
                    .setCancelable(false)
                    .setPositiveButton("确定", null);
            builder.show();
        }
    }

    class HostToIpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ipUtils.HostToIp(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            tvTarget.setText("目标IP:" + s);
            new IpInfoSearch().execute(s);
            new GetPingAsyncTask().execute(s);
        }
    }

    class IpInfoSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ipUtils.getInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            tvIpinfo.setText(MatcheIpAddress.removeField(s));
        }
    }

    class GetPingAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ipUtils.Ping(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            result(s);
        }

        private void result(String s) {
            progressbar.setVisibility(View.GONE);
            tvResult.setText(s);
            tvTarget.setVisibility(View.VISIBLE);
            tvResult.setVisibility(View.VISIBLE);
            tvIpinfo.setVisibility(View.VISIBLE);
            if (!MatcheIpAddress.isIpAddress(result)) {
                tvHost.setVisibility(View.VISIBLE);
            }
            result = "";
        }
    }


    private void disTv() {
        tvIpinfo.setVisibility(View.GONE);
        tvTarget.setVisibility(View.GONE);
        tvResult.setVisibility(View.GONE);
        tvHost.setVisibility(View.GONE);
    }

    private void resultClear() {
        etInput.setText("");
        tvHost.setText("");
        tvResult.setText("");
        tvTarget.setText("");
        tvIpinfo.setText("");
    }

}

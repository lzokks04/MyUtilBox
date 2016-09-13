package com.lzokks04.myutilbox.activity.iptool;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.utils.iputils.IpUtils;
import com.lzokks04.myutilbox.utils.iputils.MatcheIpAddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Liu on 2016/9/7.
 */
public class IpSearchActivity extends AppCompatActivity {

    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.tv_ipaddress)
    TextView tvIpaddress;
    @BindView(R.id.tv_ipinfo)
    TextView tvIpinfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private IpUtils ipUtils = IpUtils.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipinfo);
        ButterKnife.bind(this);
        initToolbar();
        new LocalIpSearch().execute("http://ip.url.cn/");
    }

    private void initToolbar() {
        toolbar.setTitle("IP归属地查询");
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
        String str = etInput.getText().toString();
        if (!TextUtils.isEmpty(str) && MatcheIpAddress.isIpAddress(str)) {
            new IpInfoSearch().execute(str);
            tvIpaddress.setText(str);
            etInput.setText("");
        } else {
            showIpDialog();
        }

    }

    private void showIpDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(IpSearchActivity.this)
                .setTitle("提示")
                .setMessage("请输入正确的ip地址")
                .setCancelable(false)
                .setPositiveButton("确定", null);
        dialog.show();
        etInput.setText("");
    }

    /**
     * 执行本地ip查询操作
     * 由于是从网页源爬下来的
     * 所以就需要AsyncTask来获取网页来进行操作
     */
    class LocalIpSearch extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return ipUtils.getBody(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            tvIpaddress.setText("本地IP:" + s);
            new IpInfoSearch().execute(s);
        }
    }

    /**
     * 查询ip归属，非标准json只能直接提取
     */
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

}

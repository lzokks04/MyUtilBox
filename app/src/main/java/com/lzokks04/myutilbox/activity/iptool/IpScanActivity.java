package com.lzokks04.myutilbox.activity.iptool;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Liu on 2016/9/9.
 */
public class IpScanActivity extends AppCompatActivity {

    @BindView(R.id.et_start)
    EditText etStart;
    @BindView(R.id.et_end)
    EditText etEnd;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.rl_layout)
    RelativeLayout rlLayout;

    private ArrayAdapter<String> adapter;
    private List<String> ipList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipscan);
        ButterKnife.bind(this);
        initToolbar();
        initListView();
    }

    private void initToolbar() {
        toolbar.setTitle("IP扫描");
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

    private void initListView() {
        listview.setAdapter(adapter = new ArrayAdapter<String>
                (IpScanActivity.this, android.R.layout.simple_list_item_1,
                        ipList = new ArrayList<String>()));
    }

    @OnClick(R.id.btn_enter)
    public void onClick() {
        listview.setVisibility(View.GONE);
        String start = etStart.getText().toString();
        String end = etEnd.getText().toString();
        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            if (!Utils.validate(start) || !Utils.validate(end)) {
                showErrorDialog(this, "IP格式错误!");
                clearEditText();
                return;
            } else {
                int first = Integer.valueOf(start.split("\\.")[3]);
                int last = Integer.valueOf(end.split("\\.")[3]);
                if (first > last) {
                    showErrorDialog(this, "输入有误请重新输入!");
                    clearEditText();
                    return;
                } else {
                    showProgressBar();

                }
            }
        } else {
            showErrorDialog(this, "IP不能为空!");
            clearEditText();
        }

    }

    private void clearEditText() {
        etStart.setText("");
        etEnd.setText("");
    }

    private void showErrorDialog(Context context, String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(str)
                .setCancelable(false)
                .setPositiveButton("确定", null);
        builder.show();
    }

    private void showProgressBar() {
        rlLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        rlLayout.setVisibility(View.GONE);
    }

    private void responce() {

    }

}

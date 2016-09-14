package com.lzokks04.myutilbox.activity.another;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lzokks04.myutilbox.R;
import com.lzokks04.myutilbox.adapter.AnotherMenuAdapter;
import com.lzokks04.myutilbox.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Liu on 2016/9/14.
 */
public class AnotherMenuActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AnotherMenuAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_menu);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        toolbar.setTitle("其它");
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
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter = new AnotherMenuAdapter(this));
        adapter.OnItemClickListener(new AnotherMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClisk(View view, int position) {
                switch (position) {
                    case 0:
                        Utils.startIntent(AnotherMenuActivity.this, IdcardActivity.class);
                        break;
                    case 1:
                        Utils.startIntent(AnotherMenuActivity.this, QqNumResultActivity.class);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });
    }

}

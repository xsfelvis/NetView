package com.xsf.netview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import NetView.netView;

public class MainActivity extends AppCompatActivity {
    private netView netView;
    private String[] titles = {"android", "javascript", "java", "python", "c++", "ios"};
    private double[] percent = {1, 0.4, 0.6, 0.5, 0.8, 0.3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        netView = (NetView.netView) findViewById(R.id.netView);
        netView.setTitles(titles);
        netView.setPercent(percent);
    }


}

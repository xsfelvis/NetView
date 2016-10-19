package com.xsf.netview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import view.NetView;

public class MainActivity extends AppCompatActivity {
    private NetView netView;
    private String[] titles = {"android", "javascript", "java", "python", "c++"};
    private double[] percent = {1, 0.4, 0.6, 0.5, 0.7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        netView = (NetView) findViewById(R.id.netView);
        Map<String,Double> map = new HashMap<>();
        map.put("android",1.0);
        map.put("javascript",0.4);
        map.put("java",0.6);
        map.put("python",0.5);
        map.put("c++",0.7);
        netView.setData(map);
    }


}

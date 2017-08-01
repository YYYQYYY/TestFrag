package com.example.testfrag.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yuqinyidev.android.azaz.R;

import java.util.ArrayList;
import java.util.List;

public class TestFm extends Fragment {
    public static final String ARG_CONTENT = "content";
    public static final String ARG_PAGE = "page_num";

    private List<String> list = new ArrayList<>();

    private int currentPageNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            list = bundle.getStringArrayList(ARG_CONTENT);
            currentPageNum = bundle.getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_test, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        TextView tv;
        tv = view.findViewById(R.id.tv);
        tv.setText(list.get(currentPageNum));
    }

    public static TestFm newInstance(List<String> contentList, int pagerNum) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARG_CONTENT, (ArrayList<String>) contentList);
        bundle.putInt(ARG_PAGE, pagerNum);
        TestFm testFm = new TestFm();
        testFm.setArguments(bundle);
        return testFm;
    }

}

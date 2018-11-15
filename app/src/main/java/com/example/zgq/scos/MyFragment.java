package com.example.zgq.scos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    private  String title;
    private ListView content;
    Context mContext;
    View view;

    public ListView getContent() {
        return content;
    }
    public String getTitle() {
        return title;
    }
    public MyFragment(){

    }
    @SuppressLint("ValidFragment")
    public MyFragment(String title, ListView content){
        super();
        this.title = title;
        this.content = content;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //上下文
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建视图
        View view = inflater.inflate(R.layout.food_view, container, false);
//        View list_view = (ListView) view.findViewById(R.id.list_view);    //在foodview里写
//
////        ListViewAdapter myAdpater=new ListViewAdapter(mContext,FoodView.PutData());
////
////        content.setAdapter();
//
//        ViewGroup parent = (ViewGroup) list_view.getParent();
//        parent.removeAllViews();
        return view;  //已经有父容器

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置内容
//        textView.setText(content);
    }
}

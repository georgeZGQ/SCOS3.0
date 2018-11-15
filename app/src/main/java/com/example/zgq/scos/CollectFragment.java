//package com.example.zgq.scos;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.example.zgq.scos.R;
//
//import java.util.ArrayList;
//
//import es.source.code.adapter.CollectRecycleAdapter;
//import es.source.code.model.GoodsEntity;
//
//    private View view;//定义view用来设置fragment的layout
//    //定义RecyclerView
//    public RecyclerView mCollectRecyclerView;
//    //定义以goodsentity实体类为对象的数据集合
//    private ArrayList<GoodsEntity> goodsEntityList = new ArrayList<GoodsEntity>();
//    //自定义recyclerveiw的适配器
//    private CollectRecycleAdapter mCollectRecyclerAdapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //获取fragment的layout
//        view = inflater.inflate(R.layout.foods_fragment, container, false);
//        //对recycleview进行配置
//        initRecyclerView();
//        //模拟数据
//        initData();
//        return view;
//    }
//
//    /**
//     * TODO 模拟数据
//     */
//    private void initData() {
//        for (int i=0;i<10;i++){
//            GoodsEntity goodsEntity=new GoodsEntity();
//            goodsEntity.setGoodsName("模拟数据"+i);
//            goodsEntity.setGoodsPrice("100"+i);
//            goodsEntityList.add(goodsEntity);
//        }
//    }
//
//    /**
//     * TODO 对recycleview进行配置
//     */
//
//    private void initRecyclerView() {
//        //获取RecyclerView
//        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_view1);
//        //创建adapter
//        mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), goodsEntityList);
//        //给RecyclerView设置adapter
//        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
//        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
//        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        //设置item的分割线
//        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
//        mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClick(View view, GoodsEntity data) {
//                //此处进行监听事件的业务处理
//                Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//}

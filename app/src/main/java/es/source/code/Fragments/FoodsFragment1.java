package es.source.code.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.zgq.scos.R;
import java.util.ArrayList;
import es.source.code.Utils.Const;
import es.source.code.Activity.FoodDetailed;
import es.source.code.Activity.FoodView;
import es.source.code.adapter.ColdFoodRecycleAdapter;
import es.source.code.adapter.CollectRecycleAdapter;
import es.source.code.model.FoodsEntity;
import es.source.code.model.FoodsMenu;

public class FoodsFragment1 extends Fragment {



    private View view;        //定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;         //定义RecyclerView

    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<FoodsEntity> foods_List1 = new ArrayList<>();   //定义食物列表
    private FoodsMenu foods_menu;
    //自定义recyclerveiw的适配器
    private ColdFoodRecycleAdapter foodsRecyclerAdapter;

    private Bundle bundle = new Bundle();
    int mPage;

    public static FoodsFragment1 newInstance(int page) {
        Bundle pages = new Bundle();
        pages.putInt(Const.PAGE_INFO, page);      //将常数写入一个单独的class
        FoodsFragment1 fragment = new FoodsFragment1();
        fragment.setArguments(pages);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(Const.PAGE_INFO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取fragment的layout
        view = inflater.inflate(R.layout.foods_fragment, container, false);
        //对recycleview进行配置
        foods_menu = ((FoodView)getActivity()).getFoodsMenu();    //传值方式
        initRecyclerView();

//        initdata();

        return view;
    }


    /**
     * TODO 对recycleview进行配置
     */

    private void initRecyclerView() {

        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);

//        if(mPage==0){   //冷菜
//            foods_List1.addAll(foods_menu.getColdFoodsList());
//        }
        if(mPage == 1){
            foods_List1.clear();////先清空
            foods_List1.addAll(foods_menu.getHotFoodsList());
        }
//        else if(mPage == 2){
//            foods_List1.addAll(foods_menu.getSeaFoodsList());
//        }
        else if(mPage == 3){
            foods_List1.clear();////先清空
            foods_List1.addAll(foods_menu.getDrinkingsList());
        }

        //创建adapter
        foodsRecyclerAdapter = new ColdFoodRecycleAdapter(getActivity(), foods_List1);   //传入该goodsEntitylist
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(foodsRecyclerAdapter);

        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义


        //实现CollectRecycleAdapter中的OnItemClickListener接口,实现对食物数组的操作
        foodsRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {

            @Override
            public void OnItemClick(View view, FoodsEntity food) {   //点击跳转到食物详情页

//                此处进行监听事件的业务处理
                int index = foods_List1.indexOf(food);
                Bundle bundle = new Bundle();
//                bundle.putSerializable(Const.FOOD_LIST,foods_List1);
                bundle.putSerializable(Const.FOODS_MENU,foods_menu);           //直接传菜单
                bundle.putInt(Const.FOOD_INDEX,index);    //传入菜品位置
                bundle.putInt(Const.TAB_LOCATION,mPage);    //传入tab位置
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(), FoodDetailed.class);  //getActivity得到当前活动
                getActivity().startActivityForResult(intent,1);
            }
        });
    }
}


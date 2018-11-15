package es.source.code.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.zgq.scos.R;
import java.util.ArrayList;
import es.source.code.Fragments.FoodsFragment;
import es.source.code.Fragments.FoodsFragment1;
import es.source.code.adapter.MyFragmentPagerAdapter;
import es.source.code.Utils.Const;
import es.source.code.model.FoodsEntity;
import es.source.code.model.FoodsMenu;
import es.source.code.model.OrderItem;
import es.source.code.model.User;

public class FoodView extends AppCompatActivity {

    public static FoodsMenu foods_menu = new FoodsMenu();
//    public static ArrayList<FoodsEntity> OrderedFoodList = new ArrayList<>();
    public  static OrderItem order_item ;    //全局
    User user =null;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    int tab_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        getSupportActionBar().setTitle(R.string.order);
        order_item = (OrderItem)getApplication();
        initFoods();
//        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取Actionbar
        android.support.v7.app.ActionBar bar = this.getSupportActionBar();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        //资源文件添加菜单
        new MenuInflater(this).inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("已点菜品")){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();    //消息包
            bundle.putString("FROM","food_view");
            intent.putExtras(bundle);
            intent.setClass(FoodView.this,FoodOrderView.class);
            FoodView.this.startActivityForResult(intent,1);  //要求回传
        }
        else if(item.getTitle().equals("查看订单")){
            Intent intent = new Intent();
            Bundle bundle = new Bundle();    //消息包
            bundle.putString("FROM","food_view");
//            bundle.putSerializable(Const.ORDERED_LIST,OrderedFoodList);   //已点菜品列表
            intent.putExtras(bundle);
            intent.setClass(FoodView.this,FoodOrderView.class);
            FoodView.this.startActivity(intent);
        }
        else{
            Toast.makeText(this, "抱歉，" + item.getTitle() + "功能尚未完善",
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        order_item = (OrderItem)getApplication();
        user = order_item.getUser();
//        updateOrderStatus(order_item.getOrderedList());    //对菜单中已点菜进行更新
        initViews();

        mTabLayout.getTabAt(tab_position).select();
    }

//    分别从FoodDetailed（在fragment中跳转）和FoodOrderView返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        order_item = (OrderItem)data.getExtras().getSerializable(Const.ORDER_ITEM);//返回的数据
        if(resultCode==2){
//            OrderedFoodList=(ArrayList<FoodsEntity>)data.getExtras().getSerializable(Const.FOOD_LIST);
            foods_menu = (FoodsMenu)data.getExtras().getSerializable(Const.FOODS_MENU);
            tab_position = data.getExtras().getInt(Const.TAB_LOCATION);
        }
//        updateOrderStatus(order_item.getOrderedList());    //对菜单中已点菜进行更新
        initViews();
    }

    private void initViews() {

        String[] tab_Titles = new String[]{"冷菜" , "热菜","海鲜", "酒水"};
        Fragment [] foodsFragments = new Fragment[4];
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        for (String tab : tab_Titles){
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }

        for(int i=0;i<4;i++){
            if(i==0||i==2){
                foodsFragments[i]= FoodsFragment.newInstance(i);
            }
            else if(i==1||i==3){
                foodsFragments[i]= FoodsFragment1.newInstance(i);
            }
        }
        //使用适配器将ViewPager与Fragment绑定在一起
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),tab_Titles,foodsFragments);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);    //Tablayout与ViewPager的组合
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //设置Tablayout点击事件

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

//                if(tab.getPosition()==0 || tab.getPosition()== 2){
//                    foodsFragments[tab.getPosition()]=FoodsFragment.newInstance(tab.getPosition());
//                }
//                else {
//                    foodsFragments[tab.getPosition()]=FoodsFragment1.newInstance(tab.getPosition());
//                }
//                myFragmentPagerAdapter.notifyDataSetChanged();

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initFoods(){

        ArrayList<FoodsEntity> cold_foods_List = new ArrayList<FoodsEntity>();
        ArrayList<FoodsEntity> hot_foods_List = new ArrayList<FoodsEntity>();
        ArrayList<FoodsEntity> sea_foods_List = new ArrayList<FoodsEntity>();
        ArrayList<FoodsEntity> drinkings_List = new ArrayList<FoodsEntity>();

        int [] cold_food_img ={R.drawable.liangbanhuanggua1,R.drawable.liangpi1,R.drawable.liangbanhaibaicai1,
                R.drawable.qiaobanyecai1,R.drawable.shousiji1,R.drawable.hongyoumuer1,R.drawable.jiangxiangbanya1};
        String [] cold_foods={"凉拌黄瓜","凉皮","凉拌海白菜","巧拌野菜","手撕鸡","红油木耳","酱香板鸭"};
        String [] cold_foods_prices ={"10.00","15.00","15.00","20.00","25.00","18.00","24.00"};

        for (int i=0;i<cold_food_img.length;i++){
            FoodsEntity foodsEntity=new FoodsEntity();
            foodsEntity.setImg(cold_food_img[i]);
            foodsEntity.setName(cold_foods[i]);
            foodsEntity.setPrice(cold_foods_prices[i]);
            foodsEntity.setCategory("COLD_FOODS");
            foodsEntity.setStatus(-1);   //未点未下单
            cold_foods_List.add(foodsEntity);
        }

        int [] hot_food_img ={R.drawable.qingjiaorousi1,R.drawable.yuxiangqiezi1,R.drawable.gongbaojiding1,R.drawable.suancaiyu1,
                R.drawable.huiguorou1,R.drawable.shuizuroupian1,R.drawable.jiaoyanliji1};
        String[] hot_foods = {"青椒肉丝", "鱼香茄子", "宫保鸡丁", "酸菜鱼", "回锅肉", "水煮肉片","椒盐里脊"};
        String[] hot_foods_prices = {"20.00", "15.00", "25.00", "30.00", "18.00", "35.00","25.00"};
        for (int i=0;i<hot_food_img.length;i++){
            FoodsEntity goodsEntity=new FoodsEntity();
            goodsEntity.setImg(hot_food_img[i]);
            goodsEntity.setName(hot_foods[i]);
            goodsEntity.setPrice(hot_foods_prices[i]);
            goodsEntity.setCategory("HOT_FOODS");
            goodsEntity.setStatus(-1);   //未点未下单
            hot_foods_List.add(goodsEntity);
        }

        int []sea_food_img={R.drawable.youmendazhaxie1,R.drawable.baozhikouliaoshen1,R.drawable.chishengpinpan1,
                R.drawable.shengbanyouyu1,R.drawable.pijiuxiaolongxia1,R.drawable.kaoshenghao1,R.drawable.chaohuajia1};
        String[] sea_foods = {"油闷大闸蟹","鲍汁扣辽参", "刺身拼盘","生拌鱿鱼", "啤酒小龙虾", "烤生蚝", "炒花甲" };
        String[] sea_foods_prices = {"45.00", "65.00", "60.00", "50.00", "40.00", "35.00","35.00"};
        for (int i=0;i<sea_food_img.length;i++){
            FoodsEntity goodsEntity=new FoodsEntity();
            goodsEntity.setImg(sea_food_img[i]);
            goodsEntity.setName(sea_foods[i]);
            goodsEntity.setPrice(sea_foods_prices[i]);
            goodsEntity.setCategory("SEA_FOODS");
            goodsEntity.setStatus(-1);   //未点未下单
            sea_foods_List.add(goodsEntity);
        }

        int [] drinking_img ={R.drawable.baiwei1,R.drawable.qingdaochunsheng1,R.drawable.yenai1,R.drawable.wanglaoji1,
                R.drawable.luzhoulaojiao1,R.drawable.jackdaniels1,R.drawable.jacobscreek1};
        String[] drinking = {"百威", "青岛纯生", "椰奶", "王老吉", "泸州老窖","杰克丹尼", "杰卡斯梅洛干红"};
        String[] drinkings_prices = {"8.00", "8.00", "12.00", "8.00","158.00", "588.00", "218.00"};
        for (int i=0;i<drinking_img.length;i++){
            FoodsEntity goodsEntity=new FoodsEntity();
            goodsEntity.setImg(drinking_img[i]);
            goodsEntity.setName(drinking[i]);
            goodsEntity.setPrice(drinkings_prices[i]);
            goodsEntity.setCategory("DRINKING");
            goodsEntity.setStatus(-1);   //未点未下单
            drinkings_List.add(goodsEntity);
        }

        foods_menu.setColdFoodsList(cold_foods_List);
        foods_menu.setHotFoodsList(hot_foods_List);
        foods_menu.setSeaFoodsList(sea_foods_List);
        foods_menu.setDrinkings(drinkings_List);
    }

    public FoodsMenu getFoodsMenu(){        //与Fragment传值
        return foods_menu;
    }       //传给fragment

    public void setFoodsMenu(FoodsMenu foods_menu){
        this.foods_menu =foods_menu;
    }

    public OrderItem  getOrderItem(){        //向Fragment传值
        return order_item;
    }
    public void setOrderItem(OrderItem order_item){      //对oeder_item进行更新
        this.order_item = order_item;
    }

}

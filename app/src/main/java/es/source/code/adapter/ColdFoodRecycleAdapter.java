package es.source.code.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zgq.scos.R;
import java.util.ArrayList;
import es.source.code.Activity.FoodView;
import es.source.code.model.FoodsEntity;
import es.source.code.model.OrderItem;

public class ColdFoodRecycleAdapter extends RecyclerView.Adapter<ColdFoodRecycleAdapter.myViewHodler> {
    private Context context;
    private static ArrayList<FoodsEntity> cold_food_List;

    //创建构造函数
    public ColdFoodRecycleAdapter(Context context, ArrayList<FoodsEntity> cold_food_List) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.cold_food_List = cold_food_List;//实体类数据ArrayList
    }

    /**
     * 创建viewhodler，相当于listview中getview中的创建view和viewhodler
     *
     * @param parent
     * @param viewType
     * @return
     */

    @Override
    public myViewHodler onCreateViewHolder (ViewGroup parent, int viewType){  //Holder
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.list_item, null);
        return new myViewHodler(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder (ColdFoodRecycleAdapter.myViewHodler holder, int position){
        //根据点击位置绑定数据
        FoodsEntity food = cold_food_List.get(position);
        holder.mFoodsImg.setImageResource(food.image);
        holder.mFoodsName.setText(food.foodsName);//获取实体类中的name字段并设置
        holder.mFoodsPrice.setText(food.foodsPrice);//获取实体类中的price字段并设置
        holder.index =cold_food_List.indexOf(food);
        if(food.getStatus() == -1){
            holder.mbutton.setText("点菜");
        }
        else{
            holder.mbutton.setText("退点");
        }
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount () {
        return cold_food_List.size();
    }//

    //自定义viewhodler
    public class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mFoodsImg;
        private TextView mFoodsName;
        private TextView mFoodsPrice;
        private Button mbutton;
        private int index;   //菜品在列表中的位置

        public myViewHodler(View itemView) {
            super(itemView);
            mFoodsImg = (ImageView) itemView.findViewById(R.id.food_image);
            mFoodsName = (TextView) itemView.findViewById(R.id.food_name);
            mFoodsPrice = (TextView) itemView.findViewById(R.id.food_price);
            mbutton = (Button) itemView.findViewById(R.id.order_button);

            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            mbutton.setOnClickListener(new View.OnClickListener() {    //对button的修改与对食物状态的修改分开
                @Override
                public void onClick(View v) {
                    if (mbutton.getText().equals("点菜")) {
                        cold_food_List.get(index).setOrdered_Num(cold_food_List.get(index).getOrdered_Num()+1);  //点菜数量加一
                        cold_food_List.get(index).setStatus(0);
                        FoodView.order_item.add_Orderded(cold_food_List.get(index));   //向已点列表中添加菜品
                        Toast.makeText(context,"点菜成功",Toast.LENGTH_SHORT).show();
                        mbutton.setText("退点");

                    } else if (mbutton.getText().equals("退点")) {
                        cold_food_List.get(index).setOrdered_Num(cold_food_List.get(index).getOrdered_Num()-1);   //点菜数量减一
                        cold_food_List.get(index).setStatus(-1);
                        FoodView.order_item.remove_Orderded(cold_food_List.get(index));  //从已点列表中移除菜品
                        Toast.makeText(context,"成功退点",Toast.LENGTH_SHORT).show();
                        mbutton.setText("点菜");
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {   //已在foodview中重写！！！
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, cold_food_List.get(getAdapterPosition()));  //goodsEntityList复用错乱??
                        //将getLayoutPosition()改为getAdapterPosition!!!!!!!!!!!!!!!
                    }
                }
            });
        }
    }


    public interface OnItemClickListener {
        public void OnItemClick(View view, FoodsEntity data);
    }
    //需要外部访问，所以需要设置set方法，方便调用
    private CollectRecycleAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener (CollectRecycleAdapter.OnItemClickListener onItemClickListener){           //??????
        this.onItemClickListener = onItemClickListener;
    }
}


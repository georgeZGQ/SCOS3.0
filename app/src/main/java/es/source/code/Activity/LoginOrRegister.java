package es.source.code.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.zgq.scos.R;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import es.source.code.Utils.Const;
import es.source.code.Utils.UserLocalInfo;
import es.source.code.model.User;

public class LoginOrRegister extends AppCompatActivity {

    Map<String,String> user_info  = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginorregister);
        getSupportActionBar().setTitle(R.string.login_or_register);

        final EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);
        Button back = findViewById(R.id.back);

        user_info  = UserLocalInfo.getInfo(this);  //获取本地用户信息
        if(user_info .get(Const.USERNAME)!=null){
            register.setVisibility(View.GONE);
            username.setText(user_info.get(Const.USERNAME));
        }
        else{
            login.setVisibility(View.GONE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String regex = "^[A-Za-z0-9]+$";    //正则表达式
                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
                if (check(str_username, regex) == false) {
                    username.setFocusable(true);
                    username.setFocusableInTouchMode(true);
                    username.requestFocus();
                    username.setError("输入内容不符合规则");
                }
                else if(check(str_password,regex)==false){
                    password.setFocusable(true);
                    password.setFocusableInTouchMode(true);
                    password.requestFocus();
                    password.setError("输入内容不符合规则");
                }
                else {
                    final ProgressDialog dialog = ProgressDialog.show(LoginOrRegister.this, "",
                            "登陆中，请稍等 …", true, true);

                    //新建一个线程用于计时
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                //让它显示2秒后，取消ProgressDialog
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    });
                    t.start();

                    User loginUser = new User();
                    loginUser.setter(str_username,str_password,true);
                    //将成功登陆用户的信息存入SharedPreference
                    UserLocalInfo.saveInfo(LoginOrRegister.this,str_username,str_password);
                    UserLocalInfo.login_state = 1;  //登陆状态置1

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.USER_KEY,loginUser);
                    bundle.putString("sign", "LoginSuccess");
                    intent.putExtras(bundle);
                    intent.setClass(LoginOrRegister.this, MainScreen.class);
                    LoginOrRegister.this.startActivity(intent);

                }
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String regex = "^[A-Za-z0-9]+$";
                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
                if (check(str_username, regex) == false) {
                    username.setFocusable(true);
                    username.setFocusableInTouchMode(true);
                    username.requestFocus();
                    username.setError("输入内容不符合规则");
                }
                else if(check(str_password,regex)==false){
                    password.setFocusable(true);
                    password.setFocusableInTouchMode(true);
                    password.requestFocus();
                    password.setError("输入内容不符合规则");
                }
                else {

                    final ProgressDialog dialog = ProgressDialog.show(LoginOrRegister.this, "",
                            "注册中，请稍等 …", true, true);

                    //新建一个线程用于计时
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                //让它显示2秒后，取消ProgressDialog
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    });
                    t.start();

                    User loginUser = new User();
                    loginUser.setter(str_username,str_password,false);
                    //将已注册用户信息写入SharedReference
                    UserLocalInfo.saveInfo(LoginOrRegister.this,str_username,str_password);
                    UserLocalInfo.login_state = 1;  //登陆状态置1

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Const.USER_KEY,loginUser);
                    bundle.putString("sign","RegisterSuccess");
                    intent.putExtras(bundle);
                    intent.setClass(LoginOrRegister.this, MainScreen.class);
                    LoginOrRegister.this.startActivity(intent);
                }
            }
        }
        );


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("sign", "return");
                intent.setClass(LoginOrRegister.this, MainScreen.class);
                LoginOrRegister.this.startActivity(intent);

                if(user_info != null){
                    UserLocalInfo.login_state = 0;  //登陆状态置0
                }
            }
        });
    }

    //检测是否满足正则表达式
    static boolean flag = false;
    public  boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

}

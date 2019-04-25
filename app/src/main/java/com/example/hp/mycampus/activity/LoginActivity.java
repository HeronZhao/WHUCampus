package com.example.hp.mycampus.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.mycampus.R;
import com.example.hp.mycampus.util.InfoUtil;

public class LoginActivity extends Activity {
    public static String username;
    private String password;
    private String code;
    private EditText ed_username;
    private EditText ed_password;
    private EditText ed_code;
    private CheckBox checkboxButton;
    private int mode=0;//网络连接控制

    //消息控制器
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 0:
                    Toast.makeText(getApplicationContext(),InfoUtil.getReason(),Toast.LENGTH_LONG).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(),"登陆成功!",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(),"教务系统连接超时！",Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取主线程网络访问权限
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //获取验证码
        InfoUtil.getVerificationCode();
        String img_path = "data/data/com.example.hp.mycampus/safecode.png";
        Bitmap bmp= BitmapFactory.decodeFile(img_path);
        ImageView imageview=(ImageView) findViewById(R.id.imageView);
        imageview.setImageBitmap(bmp);
        //刷新验证码
        imageview.setOnClickListener(new View.OnClickListener() {
            //设置登入事件
            @Override
            public void onClick(View v) {
                InfoUtil.getVerificationCode();
                String img_path = "data/data/com.example.hp.mycampus/safecode.png";
                Bitmap bmp= BitmapFactory.decodeFile(img_path);

                ImageView imageview=(ImageView) findViewById(R.id.imageView);
                imageview.setImageBitmap(bmp);
            }
        });
        //找到登入按钮
        Button login = (Button) findViewById(R.id.login_button);
        ed_username = (EditText) findViewById(R.id.login_username);
        ed_password = (EditText) findViewById(R.id.login_password);
        ed_code = (EditText) findViewById(R.id.login_code);
        //限制验证码输入字符为4个
        ed_code.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        //取到保存的账号密码验证码
        SharedPreferences sp = getSharedPreferences("config", 0);
        String user = sp.getString("username", null);
        String pass = sp.getString("password", null);
        String code = sp.getString("code", null);
        ed_username.setText(user);
        ed_password.setText(pass);
        ed_code.setText(code);
        login.setOnClickListener(new View.OnClickListener() {
            //设置登入事件
            @Override
            public void onClick(View v) {
                //登入逻辑
                while (0==mode) {
                    mode=1;
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //通过教务系统模拟登陆 测试账号密码验证码是否正确
                            try {
                                //获取EditText中输入的信息
                                LoginActivity.this.username = ed_username.getText().toString().trim();
                                LoginActivity.this.password = ed_password.getText().toString().trim();
                                LoginActivity.this.code = ed_code.getText().toString().trim();
                                checkboxButton = (CheckBox) findViewById(R.id.cb1);
                                boolean CheckBoxLogin = checkboxButton.isChecked();
                                //登陆操作
                                if (InfoUtil.Login(LoginActivity.this.username, LoginActivity.this.password, LoginActivity.this.code)) {
                                    Message msg = handler.obtainMessage();
                                    msg.arg1 = 1;
                                    handler.sendMessage(msg);//提示登陆成功
                                    SharedPreferences sp = getSharedPreferences("config", 0);
                                    if (CheckBoxLogin) {
                                        //保存 用户名 密码
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.putString("username", LoginActivity.this.username);
                                        edit.putString("password", LoginActivity.this.password);
                                        edit.putString("mode", "1");
                                        edit.commit();
                                    }else{
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.putString("username", null);
                                        edit.putString("password", null);
                                        edit.putString("mode", "1");
                                        edit.commit();
                                    }
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    mode=0;
                                    startActivity(intent);
                                } else {
                                    Message msg = handler.obtainMessage();
                                    msg.arg1 = 0;
                                    handler.sendMessage(msg);//提示错误信息
                                    //重新获取验证码
                                    InfoUtil.getVerificationCode();
                                    String img_path = "data/data/com.example.hp.mycampus/safecode.png";
                                    Bitmap bmp= BitmapFactory.decodeFile(img_path);
                                    ImageView imageview=(ImageView) findViewById(R.id.imageView);
                                    imageview.setImageBitmap(bmp);
                                    mode=0;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                //提示连接超时
                                Message msg = handler.obtainMessage();
                                msg.arg1 = 2;
                                handler.sendMessage(msg);
                                mode=0;
                            }
                        }
                    }.start();
                }
            }
        });
    }
}

/**
 *
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 */
    
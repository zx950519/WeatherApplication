package com.example.beta_0;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //核心定位
    String location = "杭州";

    //今天的天气
    public String todaysweather = "233333333333";

    public String sunny = "晴";

    private ImageButton btn_weather;
    //中间页面
    private ImageButton btn_picture;
    //最右页面
    private ImageButton btn_personal;
    //更新按钮
    private ImageButton btn_newdata;
    //Handle信息
    private static final int MSG_CODE = 1001;
    private static final int MSG_CODE_1 = 111;
    private static final int MSG_CODE_2 = 112;
    private static final int MSG_CODE_3 = 113;
    private static final int MSG_CODE_4 = 114;
    //测试hashmap
    public HashMap<String,Object> sr;
    //测试串
    public String ss = null;
    //TextView对象
    public TextView tx;
    //更新按钮计数器
    public int btn_refresh = 0;
    //数据库名称
    public static final String DB_NAME="Weatherdata.db";
    //数据库对象
    SQLiteDatabase db;



    //顶部刷新
    public TextView TopFresh;
    //城市
    public TextView City;
    //天气
    public TextView Weather;
    //温度
    public TextView Tem;
    //风力
    public TextView Wind;
    //降水
    public TextView Rain;

    //今天
    public TextView Today1;
    public TextView Today2;
    public TextView Today3;
    //明天
    public TextView Tomorrow1;
    public TextView Tomorrow2;
    public TextView Tomorrow3;

    //Data第一列
    public TextView Qiantian_2;
    public TextView Qiantian_4;
    public TextView Qiantian_5;
    public TextView Qiantian_7;
    public TextView Qiantian_8;
    public ImageView Qiantian_3;
    public ImageView Qiantian_6;

    //Data第二列
    public TextView Zuotian_2;
    public TextView Zuotian_4;
    public TextView Zuotian_5;
    public TextView Zuotian_7;
    public TextView Zuotian_8;
    public ImageView Zuotian_3;
    public ImageView Zuotian_6;

    //Data第三列
    public TextView Jintian_2;
    public TextView Jintian_4;
    public TextView Jintian_5;
    public TextView Jintian_7;
    public TextView Jintian_8;
    public ImageView Jintian_3;
    public ImageView Jintian_6;

    //Data第四列
    public TextView Mingtian_2;
    public TextView Mingtian_4;
    public TextView Mingtian_5;
    public TextView Mingtian_7;
    public TextView Mingtian_8;
    public ImageView Mingtian_3;
    public ImageView Mingtian_6;

    //Data第五列
    public TextView Houtian_2;
    public TextView Houtian_4;
    public TextView Houtian_5;
    public TextView Houtian_7;
    public TextView Houtian_8;
    public ImageView Houtian_3;
    public ImageView Houtian_6;
    public Boolean is;

    public int sex = -1;

    public void totastt(){
        Toast toast = Toast.makeText(getApplicationContext(),
                "不能为空，请重新设置", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.shizhi_40);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        InitUI();

        OpenCreateDB();
        IniteHistroyData();
        SetHistoryData();

        //选择城市
        ImageButton setcity = (ImageButton)findViewById(R.id.set_city);
        setcity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new  AlertDialog.Builder(MainActivity.this)
//                        .setTitle("请输入" )
//                        .setIcon(android.R.drawable.ic_dialog_info)
//                        .setPositiveButton("确定", null)
//                        .setNegativeButton("取消", null)
//                        .show();
                search();
                updatenewdata();
            }
        });

        //数据更新按钮事件
        btn_newdata = (ImageButton) findViewById(R.id.getnewdata);
        btn_newdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //检查有无可用网络使用
                if (isNetworkAvailable(MainActivity.this)) {

                    new Thread() {
                        @Override
                        public void run() {
                            Data_tool test = new Data_tool();
                            Log.d("tag", location);
                            if(location==null){

                            }
                            else{
                                String response = test.httpGet(location);
                                HashMap<String, Object> r;
                                r = test.getData(response);
                                Log.d("tag", r.toString());
                                ss = response;
                                sr = r;
                                Message msg = mHandler.obtainMessage(MSG_CODE);
                                msg.sendToTarget();

                                //两种发消息的模式：
                                Message message = new Message();
//                            message.what = 1;
//                            m1Handler.sendMessage(message);

//                            Message mes = mmHandler.obtainMessage(1);
//                            mes.sendToTarget();

                                String weather[] = (String[]) sr.get("weather");
                                Log.d("tag", "weather[0]::" + weather[0] + "!!!!");

                                Log.d("tag", "weather[1]::" + weather[1] + "？？？？");

                                function_today(weather[0]);
                                //更新明天的天气Icon
                                function_tomorrow(weather[1]);
                                function_afterday(weather[2]);

                            }



//                            if(weather[0]=="晴"){
//                                Message mes = m1Handler.obtainMessage(1);
//                                mes.sendToTarget();
//                                Log.d("tag", "执行晴");
//                            }
//                            else if(weather[0]=="多云"||weather[0]=="阴"){
//                                Message mes = m2Handler.obtainMessage(2);
//                                mes.sendToTarget();
//                                Log.d("tag", "执行多云");
//                            }
//                            else if(weather[0]=="阵雨"||weather[0]=="雷阵雨"||weather[0]=="雷阵雨伴有冰雹"||weather[0]=="小雨"||weather[0]=="雨夹雪"
//                                    ||weather[0]=="小雨"||weather[0]=="中雨"||weather[0]=="大雨"||weather[0]=="大暴雨"||weather[0]=="特大暴雨"
//                                    ||weather[0]=="雾"||weather[0]=="冻雨"||weather[0]=="小雨转中雨"||weather[0]=="中雨转大雨"
//                                    ||weather[0]=="大雨转暴雨"||weather[0]=="暴雨转大暴雨"||weather[0]=="大暴雨转特大暴雨"){
//                                Message mes = m3Handler.obtainMessage(3);
//                                mes.sendToTarget();
//                            }
//                            else if(weather[0]=="阵雪"||weather[0]=="小雪"||weather[0]=="中雪"||weather[0]=="大雪"||weather[0]=="小雪转中雪"
//                                    ||weather[0]=="中雪转大雪"||weather[0]=="大雪转暴雪"){
//                                Message mes = m4Handler.obtainMessage(4);
//                                mes.sendToTarget();
//                            }

                        }
                    }.start();


                    TextView vv = (TextView) findViewById(R.id.refresh);
                    vv.setText("已更新");

                    ImageButton cc = (ImageButton) findViewById(R.id.getnewdata);
                    if (btn_refresh % 2 == 0)
                        cc.setImageDrawable(getResources().getDrawable(R.drawable.fresh_1));
                    else
                        cc.setImageDrawable(getResources().getDrawable(R.drawable.fresh));
                    btn_refresh++;


                } else {
//                  Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getApplicationContext(), "当前没有可用网络！,请检查链接", Toast.LENGTH_SHORT);
                    LinearLayout toastView = (LinearLayout) toast.getView();
                    ImageView imageCodeProject = new ImageView(getApplicationContext());
                    imageCodeProject.setImageResource(R.drawable.shizhi);
                    toastView.addView(imageCodeProject, 0);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            }
        });

    }

    public void search() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.linear_show, null);
        final EditText et_search = (EditText)layout.findViewById(R.id.getcity);
        dialog.setTitle("城市设置");
        dialog.setIcon(R.drawable.city);
        dialog.setView(layout);
        dialog.setPositiveButton("查找", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String searchC = et_search.getText().toString();
                if(searchC == null){

                }
                else{
                    location = searchC;
                    Log.d("tag", location);
                }

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }

    public void function_today(String s){
        Message message = new Message();
        switch (s){
            case "晴":
//                                    Message mes = m1Handler.obtainMessage(1);
//                                    mes.sendToTarget();
                message.what = 1;
                m1Handler.sendMessage(message);
                Log.d("tag", "执行晴");
                break;
            case "多云":
                message.what = 2;
                m1Handler.sendMessage(message);
                Log.d("tag", "执行多云");
                break;
            case "阴":
                message.what = 2;
                m1Handler.sendMessage(message);
                break;
            case "阵雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "小雨转多云":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "多云转小雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "雷阵雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "雷阵雨伴有冰雹":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "小雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "雨夹雪":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "中雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "大雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "大暴雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "特大暴雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "大雨转暴雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "暴雨转大暴雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "大暴雨转特大暴雨":
                message.what = 3;
                m1Handler.sendMessage(message);
                break;
            case "阵雪":
                message.what = 4;
                m1Handler.sendMessage(message);
                break;
            case "小雪":
                message.what = 4;
                m1Handler.sendMessage(message);
                break;
            case "中雪":
                message.what = 4;
                m1Handler.sendMessage(message);
                break;
            case "大雪":
                message.what = 4;
                m1Handler.sendMessage(message);
                break;
            case "小雪转中雪":
                message.what = 4;
                m1Handler.sendMessage(message);
                break;
            case "中雪转大雪":
                message.what = 4;
                m1Handler.sendMessage(message);
                break;
            case "大雪转暴雪":
                message.what = 4;
                m1Handler.sendMessage(message);
                break;

        }
    }
    public void function_tomorrow(String s){
        //更新明天的天气icon
//        s = "晴";
        Message message = new Message();
        switch (s){
            case "晴":
//              Message mes = m1Handler.obtainMessage(1);
//              mes.sendToTarget();
                message.what = 1;
                m2Handler.sendMessage(message);
                Log.d("tag", s);
                break;
            case "多云":
                message.what = 2;
                m2Handler.sendMessage(message);
                Log.d("tag", "执行多云");
                break;
            case "阴":
                message.what = 2;
                m2Handler.sendMessage(message);
                break;
            case "阵雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "多云转小雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "小雨转多云":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "雷阵雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "雷阵雨伴有冰雹":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "小雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "雨夹雪":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "中雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "大雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "大暴雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "特大暴雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "大雨转暴雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "暴雨转大暴雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "大暴雨转特大暴雨":
                message.what = 3;
                m2Handler.sendMessage(message);
                break;
            case "阵雪":
                message.what = 4;
                m2Handler.sendMessage(message);
                break;
            case "小雪":
                message.what = 4;
                m2Handler.sendMessage(message);
                break;
            case "中雪":
                message.what = 4;
                m2Handler.sendMessage(message);
                break;
            case "大雪":
                message.what = 4;
                m2Handler.sendMessage(message);
                break;
            case "小雪转中雪":
                message.what = 4;
                m2Handler.sendMessage(message);
                break;
            case "中雪转大雪":
                message.what = 4;
                m2Handler.sendMessage(message);
                break;
            case "大雪转暴雪":
                message.what = 4;
                m2Handler.sendMessage(message);
                break;
            default:
                Log.d("tag", "啥也没干啊!");

        }
    }
    public void function_afterday(String s){
        Message message = new Message();
        switch (s){
            case "晴":
//              Message mes = m1Handler.obtainMessage(1);
//              mes.sendToTarget();
                message.what = 1;
                m3Handler.sendMessage(message);
                Log.d("tag", s);
                break;
            case "多云":
                message.what = 2;
                m3Handler.sendMessage(message);
                Log.d("tag", "执行多云");
                break;
            case "阴":
                message.what = 2;
                m3Handler.sendMessage(message);
                break;
            case "阵雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "多云转小雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "小雨转多云":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "雷阵雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "雷阵雨伴有冰雹":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "小雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "雨夹雪":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "中雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "大雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "大暴雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "特大暴雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "大雨转暴雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "暴雨转大暴雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "大暴雨转特大暴雨":
                message.what = 3;
                m3Handler.sendMessage(message);
                break;
            case "阵雪":
                message.what = 4;
                m3Handler.sendMessage(message);
                break;
            case "小雪":
                message.what = 4;
                m3Handler.sendMessage(message);
                break;
            case "中雪":
                message.what = 4;
                m3Handler.sendMessage(message);
                break;
            case "大雪":
                message.what = 4;
                m3Handler.sendMessage(message);
                break;
            case "小雪转中雪":
                message.what = 4;
                m3Handler.sendMessage(message);
                break;
            case "中雪转大雪":
                message.what = 4;
                m3Handler.sendMessage(message);
                break;
            case "大雪转暴雪":
                message.what = 4;
                m3Handler.sendMessage(message);
                break;
            default:
                Log.d("tag", "啥也没干啊!");

        }
    }

    public  void updatenewdata(){
        String weather[] =(String [])sr.get("weather");
        function_afterday(weather[2]);
        function_tomorrow(weather[1]);
        function_today(weather[0]);
    }
    //网络检测
    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //更新明天的天气icon
    private Handler m3Handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Houtian_6.setImageResource(R.drawable.sun);
            }
            if(msg.what == 2){
                Houtian_6.setImageResource(R.drawable.icon_clouds);
            }
            if(msg.what == 3){
                Houtian_6.setImageResource(R.drawable.icon_rain);
            }
            if(msg.what == 4){
                Houtian_6.setImageResource(R.drawable.lsonw);
            }
        }
    };

    //更新明天的天气icon
    private Handler m2Handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                Mingtian_6.setImageResource(R.drawable.sun);
            }
            if(msg.what == 2){
                Mingtian_6.setImageResource(R.drawable.icon_clouds);
            }
            if(msg.what == 3){
                Mingtian_6.setImageResource(R.drawable.icon_rain);
            }
            if(msg.what == 4){
                Mingtian_6.setImageResource(R.drawable.lsonw);
            }
        }
    };
    //更新主界面背景和今天的天气icon
    private Handler m1Handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                RelativeLayout rel = (RelativeLayout) findViewById(R.id.main_xml);
                rel.setBackgroundDrawable(getResources().getDrawable(R.drawable.back_sun));
                Jintian_6.setImageResource(R.drawable.sun);
            }
            if(msg.what == 2){
                RelativeLayout rell = (RelativeLayout) findViewById(R.id.main_xml);
                rell.setBackgroundDrawable(getResources().getDrawable(R.drawable.back_yun));
                Jintian_6.setImageResource(R.drawable.icon_clouds);
            }
            if(msg.what == 3){
                RelativeLayout rell = (RelativeLayout) findViewById(R.id.main_xml);
                rell.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_yu));
                Jintian_6.setImageResource(R.drawable.icon_rain);
            }
            if(msg.what == 4){
                RelativeLayout rellll = (RelativeLayout) findViewById(R.id.main_xml);
                rellll.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_snow));
                Jintian_6.setImageResource(R.drawable.lsonw);
            }
        }
    };

    //线程间的通信
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {

            if(msg.what == MSG_CODE)
            {
                tx = (TextView)findViewById(R.id.windforce);

                //获取时间
                android.text.format.Time time = new android.text.format.Time("GMT+8");
                time.setToNow();
                int year = time.year;
                int month = ++time.month;
                int day = time.monthDay;

                //天气类型
                String weather[] =(String [])sr.get("weather");
                //温度
                String temperature[] =(String [])sr.get("temperature");
                //星期几
                String week[] =(String [])sr.get("week");
                //风力及类型
                String wind[] =(String [])sr.get("wind");
                //城市
                String city = (String)sr.get("currentCity");
                //当前时间温度
                String tem = (String)sr.get("temperature_now");
                Wind.setText(wind[0]);
                City.setText(city);
                Tem.setText(tem + "℃");
                Weather.setText(weather[0]);
                //今天的pm2.5
                String todaypm = (String)sr.get("pm25").toString();
                //三天最低温度
                String today_tem_low[] = (String [])sr.get("temperature_low");
                //三天最高温度
                String today_tem_high[]  = (String [])sr.get("temperature_high");

                Today1.setText("PM2.5"+" : "+todaypm.toString());
                Today2.setText(today_tem_high[0]+"/"+today_tem_low[0]+"℃");
                Today3.setText(weather[0]);

                Tomorrow1.setText("PM2.5" + " : 47");
                Tomorrow2.setText(today_tem_high[1]+"/"+today_tem_low[1]+"℃");
                Tomorrow3.setText(weather[1]);

                Jintian_2.setText(weather[0]);
                Jintian_4.setText(today_tem_high[0]);
                Jintian_5.setText(today_tem_low[0]);
                Jintian_7.setText(weather[0]);
                Jintian_8.setText(month+"/"+day);
                day++;

                Mingtian_2.setText(weather[1]);
                Mingtian_4.setText(today_tem_high[1]);
                Mingtian_5.setText(today_tem_low[1]);
                Mingtian_7.setText(weather[1]);
                Mingtian_8.setText(month+"/"+day);
                day++;

                Houtian_2.setText(weather[2]);
                Houtian_4.setText(today_tem_high[2]);
                Houtian_5.setText(today_tem_low[2]);
                Houtian_7.setText(weather[2]);
                Houtian_8.setText(month+"/"+day);
                day++;

            }
        }
    };


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("HELLO", "HELLO：当Activity被销毁的时候，不是用户主动按back销毁，例如按了home键");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("username", "initphp"); //这里保存一个用户名
    }

    /**
     * onSaveInstanceState方法和onRestoreInstanceState方法“不一定”是成对的被调用的，
     * onRestoreInstanceState被调用的前提是，
     * activity A“确实”被系统销毁了，而如果仅仅是停留在有这种可能性的情况下，
     * 则该方法不会被调用，例如，当正在显示activity A的时候，用户按下HOME键回到主界面，
     * 然后用户紧接着又返回到activity A，这种情况下activity A一般不会因为内存的原因被系统销毁，
     * 故activity A的onRestoreInstanceState方法不会被执行
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("HELLO", "HELLO:如果应用进程被系统咔嚓，则再次打开应用的时候会进入");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //初始化界面UI

    public void InitUI(){
        //顶部刷新
        TopFresh = (TextView)findViewById(R.id.refresh);
        //城市
        City = (TextView)findViewById(R.id.city);
        //天气
        Weather = (TextView)findViewById(R.id.weather);
        //温度
        Tem = (TextView)findViewById(R.id.degree);
        //风力
        Wind = (TextView)findViewById(R.id.windforce);
        //无效的引用
        Rain = (TextView)findViewById(R.id.rain);

        //今天
        Today1 = (TextView)findViewById(R.id.today_1);
        Today2 = (TextView)findViewById(R.id.today_2);
        Today3 = (TextView)findViewById(R.id.today_3);
        //明天
        Tomorrow1 = (TextView)findViewById(R.id.tomorrow_1);
        Tomorrow2 = (TextView)findViewById(R.id.tomorrow_2);
        Tomorrow3 = (TextView)findViewById(R.id.tomorrow_3);

        //Data第一列
        Qiantian_2 = (TextView)findViewById(R.id.long_long_ago_weather_start);
        Qiantian_3 = (ImageView)findViewById(R.id.long_long_ago_iconbefore);
        Qiantian_4 = (TextView)findViewById(R.id.long_long_ago_degree_start);

        Qiantian_5 = (TextView)findViewById(R.id.long_long_ago_degree_end);
        Qiantian_6 = (ImageView)findViewById(R.id.long_long_ago_iconafter);
        Qiantian_7 = (TextView)findViewById(R.id.long_long_ago_weather_end);
        Qiantian_8 = (TextView)findViewById(R.id.long_long_date);

        //Data第二列
        Zuotian_2 = (TextView)findViewById(R.id.long_ago_weather_start);
        Zuotian_3 = (ImageView)findViewById(R.id.long_ago_iconbefore);
        Zuotian_4 = (TextView)findViewById(R.id.long_ago_degree_start);

        Zuotian_5 = (TextView)findViewById(R.id.long_ago_degree_end);
        Zuotian_6 = (ImageView)findViewById(R.id.long_ago_iconafter);
        Zuotian_7 = (TextView)findViewById(R.id.long_ago_weather_end);
        Zuotian_8 = (TextView)findViewById(R.id.long_date);

//
//
//        //Data第三列
        Jintian_2 = (TextView)findViewById(R.id.today_weather_start);
        Jintian_3 = (ImageView)findViewById(R.id.today_ago_iconbefore);
        Jintian_4 = (TextView)findViewById(R.id.today_ago_degree_start);

        Jintian_5 = (TextView)findViewById(R.id.today_ago_degree_end);
        Jintian_6 = (ImageView)findViewById(R.id.today_ago_iconafter);
        Jintian_7 = (TextView)findViewById(R.id.today_ago_weather_end);
        Jintian_8 = (TextView)findViewById(R.id.today_date);
//
//
//
//        //Data第四列
        Mingtian_2 = (TextView)findViewById(R.id.after_weather_start);
        Mingtian_3 = (ImageView)findViewById(R.id.after_ago_iconbefore);
        Mingtian_4 = (TextView)findViewById(R.id.after_ago_degree_start);

        Mingtian_5 = (TextView)findViewById(R.id.after_ago_degree_end);
        Mingtian_6 = (ImageView)findViewById(R.id.after_ago_iconafter);
        Mingtian_7 = (TextView)findViewById(R.id.after_ago_weather_end);
        Mingtian_8 = (TextView)findViewById(R.id.after_date);
//
//
//
//        //Data第五列
        Houtian_2 = (TextView)findViewById(R.id.after_after_weather_start);
        Houtian_3 = (ImageView)findViewById(R.id.after_after_ago_iconbefore);
        Houtian_4 = (TextView)findViewById(R.id.after_after_ago_degree_start);

        Houtian_5 = (TextView)findViewById(R.id.after_after_ago_degree_end);
        Houtian_6 = (ImageView)findViewById(R.id.after_after_ago_iconafter);
        Houtian_7 = (TextView)findViewById(R.id.after_after_ago_weather_end);
        Houtian_8 = (TextView)findViewById(R.id.after_after_date);


    }
    //数据库创立
    public void OpenCreateDB(){
        db = openOrCreateDatabase(DB_NAME, this.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS Weather_data");
        db.execSQL("CREATE TABLE IF NOT EXISTS Weather_data (_id INTEGER PRIMARY KEY AUTOINCREMENT,date VARCHAR,city VARCHAR,weekday VARCHAR,weather VARCHAR,max_tem VARCHAR,min_tem VARCHAR,now_tem VARCHAR,pm25 VARCHAR,ymh VARCHAR)");
    }

    //数据库历史前两条数据（必要，保证数据库元祖>=2）
    public void IniteHistroyData(){

        android.text.format.Time time = new android.text.format.Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = ++time.month;
        int day = time.monthDay-2;

        //测试模型，插入第一个元祖(实际第一次使用时预先插入两天历史数据)
        String date1 = month+"/"+day;
        String date11 = year+"-"+month+"-"+day;
        Weather_db tdry = new Weather_db(date1,"沈阳","周四","小雨","23","7","10","103",date11);
        ContentValues cvOf = new ContentValues();
        cvOf.put("date",tdry.date);
        cvOf.put("city",tdry.city);
        cvOf.put("weekday",tdry.weekday);
        cvOf.put("weather",tdry.weather);
        cvOf.put("max_tem",tdry.max_tem);
        cvOf.put("min_tem",tdry.min_tem);
        cvOf.put("now_tem",tdry.now_tem);
        cvOf.put("pm25",tdry.pm25);
        cvOf.put("ymh", tdry.ymh);
        db.insert("Weather_data", null, cvOf);

        //测试模型，插入第二个元祖(实际第一次使用时预先插入两天历史数据)
        day++;
        String date2 = month+"/"+day;
        String date22 = year+"-"+month+"-"+day;
        Weather_db wtf = new Weather_db(date2,"沈阳","周五","多云","22","11","10","133",date22);
        ContentValues cvOf1 = new ContentValues();
        cvOf1.put("date", wtf.date);
        cvOf1.put("city", wtf.city);
        cvOf1.put("weekday", wtf.weekday);
        cvOf1.put("weather",wtf.weather);
        cvOf1.put("max_tem",wtf.max_tem);
        cvOf1.put("min_tem", wtf.min_tem);
        cvOf1.put("now_tem", wtf.now_tem);
        cvOf1.put("pm25", wtf.pm25);
        cvOf1.put("ymh", wtf.ymh);
        db.insert("Weather_data", null, cvOf1);
//        Toast.makeText(this, "已经成功加载两天前的数据", Toast.LENGTH_SHORT).show();
    }

    //设置UI中昨天、前天的数据（必要，先查询，再更新）
    public void SetHistoryData(){

        android.text.format.Time time = new android.text.format.Time("GMT+8");
        time.setToNow();
        int year = time.year;
        int month = ++time.month;
        int day = time.monthDay-2;
        String date1 = month+"/"+day;


        Cursor c = db.rawQuery("SELECT * FROM Weather_data WHERE date == ?", new String[]{date1});
        Weather_db nvers = new Weather_db();
        while(c.moveToNext()){
            nvers.date = c.getString(c.getColumnIndex("date"));
            nvers.city = c.getString(c.getColumnIndex("city"));
            nvers.weekday = c.getString(c.getColumnIndex("weekday"));
            nvers.weather = c.getString(c.getColumnIndex("weather"));
            nvers.max_tem = c.getString(c.getColumnIndex("max_tem"));
            nvers.min_tem = c.getString(c.getColumnIndex("min_tem"));
            nvers.now_tem = c.getString(c.getColumnIndex("now_tem"));
            nvers.pm25 = c.getString(c.getColumnIndex("pm25"));
            nvers.ymh = c.getString(c.getColumnIndex("ymh"));
        }
        Qiantian_2.setText(nvers.weather);
        Qiantian_4.setText(nvers.max_tem);
        Qiantian_5.setText(nvers.min_tem);
        Qiantian_7.setText(nvers.weather);
        Qiantian_8.setText(nvers.date);

        day++;
        String date2 = month+"/"+day;
        Cursor c1 = db.rawQuery("SELECT * FROM Weather_data WHERE date == ?", new String[]{date2});
        Weather_db wtf = new Weather_db();
        while(c1.moveToNext()){
            wtf.date = c1.getString(c1.getColumnIndex("date"));
            wtf.city = c1.getString(c1.getColumnIndex("city"));
            wtf.weekday = c1.getString(c1.getColumnIndex("weekday"));
            wtf.weather = c1.getString(c1.getColumnIndex("weather"));
            wtf.max_tem = c1.getString(c1.getColumnIndex("max_tem"));
            wtf.min_tem = c1.getString(c1.getColumnIndex("min_tem"));
            wtf.now_tem = c1.getString(c1.getColumnIndex("now_tem"));
            wtf.pm25 = c1.getString(c1.getColumnIndex("pm25"));
            wtf.ymh = c1.getString(c1.getColumnIndex("ymh"));
        }
        Zuotian_2.setText(wtf.weather);
        Zuotian_4.setText(wtf.max_tem);
        Zuotian_5.setText(wtf.min_tem);
        Zuotian_7.setText(wtf.weather);
        Zuotian_8.setText(wtf.date);

    }

    //判断天气类型进而刷新背景
//    public void updatebackground(){
//        String s = todaysweather;
//        if(s == "阵雨"||s =="雨"||s =="大暴雨"){
//            RelativeLayout rel = (RelativeLayout) findViewById(R.id.main_xml);
//            rel.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_yu));
//            Toast toast=Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
//            toast.show();
//        }
//        else{
//            Toast toast=Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }

    //以下function均为更新按钮时间中出现
    //检查数据是否需要更新
    public  Boolean CheckIfUpdate(){
        return false;
    }

    //将从网上得到的数据写入数据库,实时温度另外单独更新UI
    public void UpdateData(){

    }

    //更新UI
    public void UpdateUi(){

    }

    //测试设置整页
//    public void  Tryset(){
//        Cursor c = db.rawQuery("SELECT * FROM Weather_data WHERE date == ?", new String[]{"10.10"});
//        Weather_db axiba = new Weather_db();
//        while(c.moveToNext()){
//            axiba.date = c.getString(c.getColumnIndex("date"));
//            axiba.city = c.getString(c.getColumnIndex("city"));
//            axiba.weekday = c.getString(c.getColumnIndex("weekday"));
//            axiba.weather = c.getString(c.getColumnIndex("weather"));
//            axiba.max_tem = c.getString(c.getColumnIndex("max_tem"));
//            axiba.min_tem = c.getString(c.getColumnIndex("min_tem"));
//            axiba.now_tem = c.getString(c.getColumnIndex("now_tem"));
//            axiba.pm25 = c.getString(c.getColumnIndex("pm25"));
//            axiba.ymh = c.getString(c.getColumnIndex("ymh"));
//        }
//
//        RelativeLayout rel = (RelativeLayout) findViewById(R.id.main_xml);
//        rel.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_yu));
//        if(todaysweather == "阵雨"||todaysweather =="雨"||todaysweather =="大暴雨"){
//
//        }
//
////        if(axiba.weather=="大暴雨"){
////            RelativeLayout rel = (RelativeLayout) findViewById(R.id.main_xml);
////            rel.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_yu));
////        }
//
//    }
}

//实现数据库
//主键：日期  键：城市、周几、天气、最高温度、最低温度、实时温度、pm2.5、年月日
class Weather_db{

    public String date;
    public String city;
    public String weekday;
    public String weather;
    public String max_tem;
    public String min_tem;
    public String now_tem;
    public String pm25;
    public String ymh;

    public Weather_db(){

    }

    public Weather_db(String date, String city, String weekday, String weather,
            String max_tem, String min_tem, String now_tem, String pm25,String ymh){
        this.date = date;
        this.city = city;
        this.weekday = weekday;
        this.weather = weather;
        this.max_tem = max_tem;
        this.min_tem = min_tem;
        this.now_tem = now_tem;
        this.pm25 = pm25;
        this.ymh = ymh;
    }
}

//触屏切换页面需要的类
class MYViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> views;

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {

        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }
}
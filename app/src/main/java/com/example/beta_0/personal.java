package com.example.beta_0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class personal extends AppCompatActivity {

    private ImageButton btn_weather;
    private ImageButton btn_picture;
    private ImageButton btn_personal;

    private List<Map<String, Object>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        btn_weather =(ImageButton) findViewById(R.id.personal_weather);
        btn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(personal.this, MainActivity.class);
                startActivity(intent);
                personal.this.finish();
            }
        });

        btn_picture =(ImageButton) findViewById(R.id.personal_picture);
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(personal.this, picture.class);
                startActivity(intent);
                personal.this.finish();
            }
        });

        ListView list = (ListView) findViewById(R.id.mylistview);

//        简单listview显示
//        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
////        for(int i=0;i<5;i++)
////        {
////            HashMap<String, String> map = new HashMap<String, String>();
////            map.put("ItemTitle", "");
////            map.put("ItemText", "");
////            mylist.add(map);
////        }
//        for(int i=0;i<10;i++)
//        {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("ItemTitle", "This is Title.....");
//            map.put("ItemText", "This is text.....");
//            mylist.add(map);
//        }
//        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
//                mylist,//数据来源
//                R.layout.my_listitem,//ListItem的XML实现
//
//                //动态数组与ListItem对应的子项
//                new String[] {"ItemTitle", "ItemText"},
//
//                //ListItem的XML文件里面的两个TextView ID
//                new int[] {R.id.ItemTitle,R.id.ItemText});
//        //添加并且显示

        SimpleAdapter mSchedule = new SimpleAdapter(this,getData(),R.layout.my_listitem,
                new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.info,R.id.img});

        list.setAdapter(mSchedule);

//        伪listview遍历 失败
//        for (int i = 0; i <list.getChildCount(); i++){
//            View view = list.getChildAt(i);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(personal.this,MainActivity.class);
//                    startActivity(intent);
//                    personal.this.finish();
//                }
//            });
//        }

//        ListView跳转
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("mm", "onItemClick");

                if(position == 0){
                    Intent intent = new Intent(personal.this,MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    personal.this.finish();
                }
                else if (position == 1){
                    Intent intent = new Intent(personal.this,picture.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    personal.this.finish();
                }
//
            }
        });
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "通知");
        map.put("info", "");
        map.put("img", R.drawable.tongzhi);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "个人信息");
        map.put("info", "");
        map.put("img", R.drawable.shangcheng);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "社交圈");
        map.put("info", "");
        map.put("img", R.drawable.shejiao);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "穿衣");
        map.put("info", "");
        map.put("img", R.drawable.chuanyi);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "系统设置");
        map.put("info", "");
        map.put("img", R.drawable.shizhi);
        list.add(map);

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal, menu);
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

}

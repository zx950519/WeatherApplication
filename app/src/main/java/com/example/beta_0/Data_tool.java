package com.example.beta_0;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class Data_tool{
    private static final String root_url="http://api.map.baidu.com/telematics/v3/weather?location=";
    private static final String out_model="&output=json";
    private static final String ak="&ak=pgisuBCzE6OtVg6rGS4fiTIG";


    public  String httpGet(String location){
        String response=null;
        String src_url="";
        try
        {  src_url=root_url+ (location=URLEncoder
                .encode(location, "utf-8"))+out_model+ak;}catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }


        try{
            //创建url并连接请求
            URL url=new URL(src_url);
            HttpURLConnection httpconnection=(HttpURLConnection)url.openConnection();
            httpconnection.setRequestMethod("GET");
            httpconnection.connect();
            //获取数据
            InputStream is=httpconnection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String temp;
            StringBuffer buf=new StringBuffer();
            while ((temp=reader.readLine())!=null){
                buf.append(temp);
            }
            reader.close();
            response=buf.toString();


        }catch(IOException e){
            e.printStackTrace();
        }
        return  response;
    }

    //获取网络连接状态
    public boolean getNetworkInfo(Context context)
    {
        if(context!=null)
        {
            ConnectivityManager connection=(ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info=connection.getActiveNetworkInfo();
            if(info!=null){
                return info.isAvailable();
            }
        }
        return false;
    }

    //json数据处理
    public HashMap<String,Object> getData(String source){
        HashMap<String,Object> data=new HashMap<>();
        try{
            JSONObject src=new JSONObject(source);

            String weather[]=new String[3]; //今，明，后三天天气情况
            String week[]=new String[3];//三天日期
            String wind[]=new String[3];//三天风力
            String temperature_low[]=new String[3];//三天最低温度
            String temperature_high[]=new String[3];//三天最高温度

            if(src.has("error"))
                data.put("status",src.getString("status"));
            else
                data.put("status",src.getInt("status"));

            data.put("date",src.getString("date"));

            JSONArray results=src.getJSONArray("results");
            JSONObject temp=results.getJSONObject(0);
            data.put("currentCity",temp.getString("currentCity"));
            data.put("pm25",temp.getInt("pm25"));
            JSONArray weather_data=temp.getJSONArray("weather_data");
            JSONObject obj;

            //获取温度数字
            Pattern pattern=Pattern.compile("\\d+");

            int m;
            for(m=0;m<3;m++){
                obj=weather_data.getJSONObject(m);
                weather[m]=obj.getString("weather");
                week[m]=obj.getString("date");
                wind[m]=obj.getString("wind");
                String temperature=obj.getString("temperature");
                String buffer[]=temperature.split("~");

                temperature_high[m]=buffer[0];
                Matcher t=pattern.matcher(buffer[1]);
                if(t.find())
                    temperature_low[m]=t.group(0);


            }

            //单独处理一下第一天的天气数据 因为date项中含有实时温度信息可以获取
            obj=weather_data.getJSONObject(0);
            String temp_date=obj.getString("date");
            String buffer[]=temp_date.split("\\s");
            week[0]=buffer[0];
            Matcher t=pattern.matcher(buffer[2]);
            if(t.find())
            {
                data.put("temperature_now",t.group(0));//实时温度
            }
            data.put("buffer_len",buffer.length);

            data.put("weather",weather);
            data.put("week",week);
            data.put("wind",wind);
            data.put("temperature_low",temperature_low);
            data.put("temperature_high",temperature_high);

        }catch(JSONException e){
            e.printStackTrace();
        }
        return data;
    }

}
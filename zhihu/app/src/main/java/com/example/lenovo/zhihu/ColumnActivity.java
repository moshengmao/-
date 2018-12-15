package com.example.lenovo.zhihu;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import android.support.v4.view.GravityCompat;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.zhihu.Adapter.ColumnAdapter;

import com.example.lenovo.zhihu.Bean.Column;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;



public class ColumnActivity extends AppCompatActivity{
    //栏目总览
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    String jsonData;
    private List<Column> colList= new ArrayList<>();
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.column);
        httpURLConnection();   //发送http请求
        //recyclerView
        recyclerView=(RecyclerView)findViewById(R.id.RecyclerView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ColumnAdapter columnAdapter=new ColumnAdapter(colList);
        recyclerView.setAdapter(columnAdapter);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh1);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHotnews();
            }
        });

    }

    private void httpURLConnection(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(" https://news-at.zhihu.com/api/4/sections");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //得到connection对象
                    connection.setRequestMethod("GET");          //设置请求方式
                    connection.connect();                 //连接网络
                    int responseCode = connection.getResponseCode();         //得到响应码
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        // 从流中读取响应信息
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response=new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);                }
                        String responseData=response.toString();
                        parJSONWithJSONObject(responseData); //调用函数
                        showResponse(responseData);
                        reader.close(); // 关闭流
                    }
                    connection.disconnect(); // 断开连接，释放资源
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", "HttpError");
                }    }
        }).start();
    }
    String id;
    String description;
    String thumbnail;
    String name;
    JSONArray jsonArray;
    private void parJSONWithJSONObject(String jsonData){//jason解析http响应返回的数据
        try {
            JSONObject jsonObject=new JSONObject(jsonData);
            String timestamp=jsonObject.getString("data");
            jsonArray=jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                id=jsonObject1.getString("id");
               description=jsonObject1.getString("description");
                thumbnail=jsonObject1.getString("thumbnail");
                name=jsonObject1.getString("name");
                //if(id==null)id=null;
                //if (thumbnail==null)thumbnail=null;
                //if (description==null)description=null;
                //if (name==null)name=null;
                Column column=new Column();
                column.setDescription(description);
                column.setName(name);
                column.setThumbnail(thumbnail);
                column.setId(Integer.valueOf(id));
               colList.add(column);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            Log.e("TAG", "JSONError");
        }
    }
    private void showResponse(final  String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });}
    private void refreshHotnews(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        httpURLConnection();
                        //HotnewsAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}


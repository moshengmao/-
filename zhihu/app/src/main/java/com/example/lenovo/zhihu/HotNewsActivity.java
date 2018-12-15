package com.example.lenovo.zhihu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.lenovo.zhihu.Adapter.HotnewsAdapter;
import com.example.lenovo.zhihu.Bean.HotNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HotNewsActivity extends AppCompatActivity{
    //热门新闻（首页）

    String news_id;
    String url;
    String thumbnail;
    String title;
    JSONArray jsonArray;
    private NavigationView navigationView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private DrawerLayout mDrawerLayout;
    TextView hotNews_item;
    RecyclerView recyclerView;
    String jsonData;
    private List<HotNews> mList= new ArrayList<>();
String data;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.nav_menu,menu);//Menu方法
        return true;
    }
    @Override
    public boolean
    onOptionsItemSelected(MenuItem item){         //Menu方法
        switch( item.getItemId()){
            case R.id.nav_column:
                Intent intent=new Intent(HotNewsActivity.this,ColumnActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotnews);
        httpURLConnection();   //发送http请求
        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        HotnewsAdapter hotnewsAdapter = new HotnewsAdapter(mList, context);
        recyclerView.setAdapter(hotnewsAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.daohang_caidan);
        }
        navView.setCheckedItem(R.id.nav_collect);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onOptionsItemSelected(item);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHotnews();
            }
        });
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        CircleImageView header = (CircleImageView) headerLayout.findViewById(R.id.icon_image);
        TextView myName = (TextView) headerLayout.findViewById(R.id.username);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HotNewsActivity.this, HeadActivity.class);
                startActivity(intent);
            }
        });
        myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HotNewsActivity.this,PersonalDataActivity.class);
                intent.putExtra("UserName",data);
                startActivity(intent);
            }
        });
        //在HotnewsAdapter里设立一个接口，这是搜索到的博客网址https://blog.csdn.net/qq_42792745/article/details/81230897
            hotnewsAdapter.setOnItemClickListener(new HotnewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                    Intent intent = new Intent(HotNewsActivity.this, SpecifichotnewsActivity.class);
                    intent.putExtra("news_id",  mList.get(position).getNews_id());
                    startActivity(intent);

            }
        });}


    private void httpURLConnection() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://news-at.zhihu.com/api/4/news/hot");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //得到connection对象
                    connection.setRequestMethod("GET");          //设置请求方式
                    connection.connect();                 //连接网络
                    int responseCode = connection.getResponseCode();         //得到响应码
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // 从流中读取响应信息
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        String responseData = response.toString();
                        parJSONWithJSONObject(responseData); //调用函数
                        showResponse(responseData);
                        reader.close(); // 关闭流
                    }
                    connection.disconnect(); // 断开连接，释放资源
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parJSONWithJSONObject(String jsonData) {//jason解析http响应返回的数据
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String timestamp = jsonObject.getString("recent");
            jsonArray = jsonObject.getJSONArray("recent");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                news_id = jsonObject1.getString("news_id");
                url = jsonObject1.getString("url");
                thumbnail = jsonObject1.getString("thumbnail");
                title = jsonObject1.getString("title");
                HotNews hotNews = new HotNews();
                hotNews.setUrl(url);
                hotNews.setTitle(title);
                hotNews.setThumbnail(thumbnail);
                hotNews.setNews_id(news_id);
                mList.add(hotNews);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            Log.e("TAG", "JSONError");
        }
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //hotNews_item=(TextView)findViewById(R.id.hotNews_item);
                //recyclerView.(response);
            }
        });
    }

    private void refreshHotnews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
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
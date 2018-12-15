package com.example.lenovo.zhihu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lenovo.zhihu.Bean.Column;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpecifichotnewsActivity extends AppCompatActivity {
    String share_url;
    String js;
    int ga_prefix;
    String images;
    int type;
    int id;
    String css;
    String body;
    String image_source;
    String title;
    String image;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Intent intent=getIntent();
        data=intent.getStringExtra("news_id");
        httpURLConnection();
        WebView webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("share_url");//热门具体消息

    }
    private void httpURLConnection(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(" https://news-at.zhihu.com/api/4/news/"+data);
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


    private void parJSONWithJSONObject(String jsonData){//jason解析http响应返回的数据
        try {

            JSONArray  jsonArray= new JSONArray(jsonData);
            for(int i=1;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                id=jsonObject1.getInt("id");
                css=jsonObject1.getString("css");
                images=jsonObject1.getString("images");
                js=jsonObject1.getString("js");
                share_url=jsonObject1.getString("share_url");
                image=jsonObject1.getString("image");
                title=jsonObject1.getString("title");
                image_source=jsonObject1.getString("image_source");
                body=jsonObject1.getString("body");
                ga_prefix=jsonObject1.getInt("ga_prefix");
                        type=jsonObject1.getInt("type");
                /*Column column=new Column();
                column.setDescription(description);
                column.setName(name);
                column.setThumbnail(thumbnail);
                column.setId(Integer.valueOf(id));
                colList.add(column);
            */}
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
}

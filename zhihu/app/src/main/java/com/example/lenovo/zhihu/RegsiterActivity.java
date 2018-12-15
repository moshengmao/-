package com.example.lenovo.zhihu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegsiterActivity extends AppCompatActivity {
    //注册

private  MyDatabaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regsiter);
        Button btn_rgs=(Button)findViewById(R.id.register);
        final EditText et1=(EditText)findViewById(R.id.edittext1);
        final EditText et2=(EditText) findViewById(R.id.edittext2);
        dbhelper=new MyDatabaseHelper(this,"USER.db",null,1);
        Intent intent=getIntent();
        final String data=intent.getStringExtra("UserId");
//        Log.d("RegsiterActivity",data);
        btn_rgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et1.getText().toString();
                String password=et2.getText().toString();
                SQLiteDatabase db=dbhelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name",name);
                values.put("password",password);
                 db.insert("User",null,values);
                Toast.makeText(RegsiterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegsiterActivity.this,HotNewsActivity.class);
                //传过去用户名表里对应的ID
                intent.putExtra("UserName", name);
                startActivity(intent);
            }
        });
    }
}

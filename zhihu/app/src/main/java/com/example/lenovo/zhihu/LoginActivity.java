package com.example.lenovo.zhihu;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class LoginActivity extends AppCompatActivity {
    //登录

    private MyDatabaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dbhelper=new MyDatabaseHelper(this,"USER.db",null,1);
        Button btn_cfm=(Button)findViewById(R.id.confirm);
        final EditText et1=(EditText)findViewById(R.id.edittext1);
        final EditText et2=(EditText) findViewById(R.id.edittext2);
    btn_cfm.setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int id;
            String password,name;
            String  []et_1={et1.getText().toString()};
            String []et_2={et2.getText().toString()};
            SQLiteDatabase db=dbhelper.getWritableDatabase();
            Cursor cursor=db.query("User",new String[]{"name","password"},"name=?",et_1,null,null,null);

            try{if(cursor.moveToFirst()) {
                Intent intent1 = new Intent(LoginActivity.this, HotNewsActivity.class);
                name = cursor.getString(cursor.getColumnIndex("name"));
                intent1.putExtra("UserName", name);
                startActivity(intent1);

                //do {
                //  password = cursor.getString(cursor.getColumnIndex("password"));
                //name = cursor.getString(cursor.getColumnIndex("name"));
                //} while (cursor.moveToLast());}

            }else{
                Intent intent=new Intent(LoginActivity.this,RegsiterActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this,"等待跳转",Toast.LENGTH_LONG).show();}
            cursor.close();
            }catch (Exception e){e.printStackTrace();Log.d("LoginActivity","Intent failed.");}
        }
    });
}
}
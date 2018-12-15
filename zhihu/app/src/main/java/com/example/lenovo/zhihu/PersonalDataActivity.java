package com.example.lenovo.zhihu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PersonalDataActivity extends AppCompatActivity {
//个人资料
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_data);
        Intent intent= getIntent();
        String data=intent.getStringExtra("UserName");

        TextView userName1=(TextView)findViewById(R.id.userName1);

        userName1.setText(data);
        //TextView password1=(TextView)findViewById(R.id.password1);
        //password1.setText();
    }
}

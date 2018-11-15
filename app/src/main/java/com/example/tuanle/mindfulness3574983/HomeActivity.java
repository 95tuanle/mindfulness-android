package com.example.tuanle.mindfulness3574983;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void ToBreatheClick(View view) {
        Intent intent = new Intent(HomeActivity.this, BreatheActivity.class);
        startActivity(intent);
    }

    public void ToTodoClick(View view) {
        Intent intent = new Intent(HomeActivity.this, TodoActivity.class);
        startActivity(intent);
    }
}

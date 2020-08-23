package com.websarva.wings.android.vs_teruteru;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OpeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        Button btHowTo = findViewById(R.id.btHowTo);
        Button btEasy = findViewById(R.id.btEasy);
        Button btNormal = findViewById(R.id.btNormal);
        Button btHard = findViewById(R.id.btHard);
        btHowTo.setOnClickListener(new btHowToClickListener());
        btEasy.setOnClickListener(new btLevelOnClickListener());
        btNormal.setOnClickListener(new btLevelOnClickListener());
        btHard.setOnClickListener(new btLevelOnClickListener());
        final ImageView ivTeru1 = findViewById(R.id.ivTeru1);
        final ImageView ivTeru2 = findViewById(R.id.ivTeru2);
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                float rot1 = ivTeru1.getRotation();
                float rot2 = ivTeru2.getRotation();
                ivTeru1.setRotation(rot1 + (float) 3);
                ivTeru2.setRotation(rot2 - (float) 3);
                handler.postDelayed(this, 100);
            }
        };
        handler.post(r);
    }

    public class btLevelOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            int m;
            if(view.getId() == R.id.btEasy)m = 10;
            else if(view.getId() == R.id.btNormal)m = 15;
            else m = 20;
            Intent intent = new Intent(OpeningActivity.this, MainActivity.class);
            intent.putExtra("difficulty", m);
            startActivity(intent);
        }
    }

    public class btHowToClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent = new Intent(OpeningActivity.this, HowToActivity.class);
            startActivity(intent);
        }
    }
}

package com.websarva.wings.android.vs_teruteru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameClearActivity extends AppCompatActivity {
    long duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_clear);
        Intent intent = getIntent();
        duration = intent.getLongExtra("duration", 10);
        TextView tvTime = findViewById(R.id.tvTime);
        tvTime.setText(String.valueOf(duration/1000) + "s");
        findViewById(R.id.btOpeningBack).setOnClickListener(new OpeningBack());
    }

    public class OpeningBack implements View.OnClickListener{
        @Override
        public void onClick(View view){
            finish();
        }
    }
}

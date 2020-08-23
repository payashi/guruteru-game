package com.websarva.wings.android.vs_teruteru;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {
    int n = 6;
    int m;
    int dx[] = {0, 1, 0, -1, 0};
    int dy[] = {0, 0, 1, 0, -1};
    boolean[][] retention = new boolean[n][n];
    int[][] count = new int[n][n];
    char[][] bozu = new char[n][n];
    boolean clear_check = false;
    Date start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        m = intent.getIntExtra("difficulty", 0);
        start = new Date(System.currentTimeMillis());
        Log.d("test", "createPanels");
        CreatePanels();
        Log.d("test", "RandomSetPanels");
        RandomSetPanels();
        Log.d("test", "InitializePanels");
        InitializePanels();
        Log.d("test", "Finished");
    }

    public void CreatePanels(){
        LinearLayout Field = findViewById(R.id.tlField);
        for(int i=0;i<n;i++){
            getLayoutInflater().inflate(R.layout.single_row, Field);
            LinearLayout ithRow = (LinearLayout) Field.getChildAt(i);
            for(int j=0;j<n;j++) {
                getLayoutInflater().inflate(R.layout.single_panel, ithRow);
                RelativeLayout Panel = (RelativeLayout) ithRow.getChildAt(j);
                ImageButton ibPanel = (ImageButton) Panel.getChildAt(1);
                TextView tvPanel = (TextView) Panel.getChildAt(0);
                ibPanel.setId(i*n+j);
                tvPanel.setId(n*n + i*n+j);
                ibPanel.setOnClickListener(new PanelClickListener());
                ibPanel.setOnLongClickListener(new PanelLongClickListner());
                bozu[i][j] = 'N';
                count[i][j] = 0;
            }
        }
    }

    public void RandomSetPanels(){
        Random rnd = new Random();
        while(m>0){
            int t = rnd.nextInt(n*n);
            if(bozu[t/n][t%n] != 'N')continue;
            bozu[t/n][t%n] = (rnd.nextInt(100)%2 == 0 ? 'U':'D');
            m--;
        }
        Log.d("test", "finish creating random map");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(bozu[i][j] == 'N')continue;
                int t = (bozu[i][j] == 'U'? 1:-1);
                for(int k=0;k<5;k++){
                    int nx = i+dx[k], ny = j+dy[k];
                    if((0<=nx&&nx<n)&&(0<=ny&&ny<n))count[nx][ny]+=t;
                }
            }
        }
    }

    public void InitializePanels(){
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                retention[i][j] = false;
                RelativeLayout parentView = (RelativeLayout) findViewById(i*n+j).getParent();
                if(count[i][j]>0)parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel_plus));
                else if(count[i][j]<0)parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel_minus));
                else parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel));
                TextView tvCount = (TextView) parentView.getChildAt(0);
                tvCount.setText(String.valueOf(count[i][j]));
            }
        }
    }

    public class PanelClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view){
            if(clear_check)return;
            int view_id = view.getId();
            view.setRotation(view.getRotation() + 90);
            if(view.getRotation()==270)view.setRotation(0);
            int t=(view.getRotation() == 0? -2:1);
            for(int k=0;k<5;k++){
                int nx = view_id/n+dx[k], ny = view_id%n+dy[k];
                if((0<=nx&&nx<n)&&(0<=ny&&ny<n)){
                    count[nx][ny]+=t;
                    RelativeLayout parentView = (RelativeLayout) findViewById(nx*n+ny).getParent();
                    TextView tvCount = findViewById(n*n+nx*n+ny);
                    tvCount.setText(String.valueOf(count[nx][ny]));
                    if(retention[nx][ny])continue;
                    if(count[nx][ny]>0)parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel_plus));
                    else if(count[nx][ny]<0)parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel_minus));
                    else parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel));
                }
            }
            clear_check = true;
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                    if(count[i][j]!=0){clear_check=false;break;}
            if(clear_check){
                Date end = new Date(System.currentTimeMillis());
                long duration = (end.getTime()-start.getTime());
                Intent intent = new Intent(MainActivity.this, GameClearActivity.class);
                intent.putExtra("duration", duration);
                startActivity(intent);
                finish();
            }
        }
    }

    public class PanelLongClickListner implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view){
            if(clear_check)return true;
            int view_id = view.getId();
            View parentView = (View)view.getParent();
            if(retention[view_id/n][view_id % n]){
                retention[view_id/n][view_id % n] = false;
                if(count[view_id/n][view_id % n]>0)parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel_plus));
                else if(count[view_id/n][view_id % n]<0)parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel_minus));
                else parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel));
            }else{
                retention[view_id/n][view_id % n] = true;
                parentView.setBackgroundDrawable(getResources().getDrawable(R.drawable.radius_panel_retention));
            }
            return true;
        }
    }

}

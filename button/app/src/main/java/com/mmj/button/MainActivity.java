package com.mmj.button;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt1_off;
    private Button bt1_on;
    private Button bt2_off;
    private Button bt2_on;
    private Button bt3_off;
    private Button bt3_on;
    private Button bt4_off;
    private Button bt4_on;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1_off=(Button) findViewById(R.id.bt1_off);
        bt1_on=(Button) findViewById(R.id.bt1_on);
        bt2_off=(Button) findViewById(R.id.bt2_off);
        bt2_on=(Button) findViewById(R.id.bt2_on);
        bt3_off=(Button) findViewById(R.id.bt3_off);
        bt3_on=(Button) findViewById(R.id.bt3_on);
        bt4_off=(Button) findViewById(R.id.bt4_off);
        bt4_on=(Button) findViewById(R.id.bt4_on);
        bt1_off.setOnClickListener(this);
        bt2_off.setOnClickListener(this);
        bt3_off.setOnClickListener(this);
        bt4_off.setOnClickListener(this);
        bt1_on.setOnClickListener(this);
        bt2_on.setOnClickListener(this);
        bt3_on.setOnClickListener(this);
        bt4_on.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt1_off:
                bt1_off.setVisibility(View.GONE);
                bt1_on.setVisibility(View.VISIBLE);
                break;
            case R.id.bt1_on:
                bt1_on.setVisibility(View.GONE);
                bt1_off.setVisibility(View.VISIBLE);
                break;
            case R.id.bt2_off:
                bt2_off.setVisibility(View.GONE);
                bt2_on.setVisibility(View.VISIBLE);
                break;
            case R.id.bt2_on:
                bt2_on.setVisibility(View.GONE);
                bt2_off.setVisibility(View.VISIBLE);
                break;
            case R.id.bt3_off:
                bt3_off.setVisibility(View.GONE);
                bt3_on.setVisibility(View.VISIBLE);
                break;
            case R.id.bt3_on:
                bt3_on.setVisibility(View.GONE);
                bt3_off.setVisibility(View.VISIBLE);
                break;
            case R.id.bt4_off:
                bt4_off.setVisibility(View.GONE);
                bt4_on.setVisibility(View.VISIBLE);
                break;
            case R.id.bt4_on:
                bt4_on.setVisibility(View.GONE);
                bt4_off.setVisibility(View.VISIBLE);
                break;
                default:break;
        }
    }
}

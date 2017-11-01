package com.mmj.processbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progressbar;
    private SeekBar seek;
    private int progress=0;
    private int step=10;
    protected static final String TAG = "MainActivity";
    private TextView myTextView;
    private EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton=(Button) findViewById(R.id.add);
        Button minButton=(Button) findViewById(R.id.min);
        progressbar=(ProgressBar) findViewById(R.id.h_processbar);
        addButton.setOnClickListener(this);
        minButton.setOnClickListener(this);
        seek=(SeekBar)findViewById(R.id.seekbar);
        seek.setProgress(50);//初始值
        myTextView = (TextView) findViewById(R.id.tag);
        myTextView.setText("当前值为" + 50);
        seek.setOnSeekBarChangeListener(seeklisten);
        edittext=(EditText) findViewById(R.id.input);
    }
    @Override
    public void onClick(View v){
        progress=progressbar.getProgress();
        String len=edittext.getText().toString();
        boolean isNum = len.matches("[0-9]+");//正则表达式
        if (isNum){
            step=Integer.valueOf(len);
            switch (v.getId()){
                case R.id.add:
                    progress+=step;
                    progressbar.setProgress(progress);
                    break;
                case R.id.min:
                    progress=progressbar.getProgress();
                    progress-=step;
                    progressbar.setProgress(progress);
                    break;
                default:
                    break;
            }
        }
        else {
            Toast.makeText(MainActivity.this, "请输入有效的数字!", Toast.LENGTH_LONG).show();
        }

    }
    private SeekBar.OnSeekBarChangeListener seeklisten=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            Log.i(TAG,"onProgressChanged");     //log.info输出调试信息
            myTextView.setText("当前值为" + i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            Log.i(TAG,"onStartTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.i(TAG,"onStopTrackingTouch");
        }
    };
}

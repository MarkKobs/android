package com.mmj.attribution;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView tv;
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            int arg1 = msg.what;
            if (arg1 == 0) {
                tv.setText(msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        tv = (TextView) findViewById(R.id.textView1);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString();
                if (s.length() == 11) {
                    //查询
                    serchPhone(string);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void serchPhone(final String s) {
        new Thread() {
            public void run() {
                String path = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + s;
                try {
                    URL url = new URL(path);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 提交模式
                    connection.setRequestMethod("GET");
                    //读取超时 单位毫秒
                    connection.setReadTimeout(5000);
                    //连接超时 单位毫秒
                    connection.setConnectTimeout(5000);


                    //获取
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String string = streamToString(inputStream, "gbk");

                        String json = string.substring(string.indexOf("{"));
                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = json;
                        myHandler.sendMessage(message);
                        Log.d("string", json);
                    }
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    private String streamToString(InputStream inputStream, String charset) {
        try {
            //输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
            //得到缓冲流
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                builder.append(s);
            }
            reader.close();
            return builder.toString();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }
}

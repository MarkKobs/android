package com.mmj.phonelistener;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity {
    private TextView textView,textView1;
    private String incomingNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.TeleInfo);
        textView1=(TextView)findViewById(R.id.call);
        //Get the instance of TelephonyManager
        TelephonyManager tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        //Calling the methods of TelephonyManager the returns the information
        String networkCountryISO=tm.getNetworkCountryIso();
        String SIMCountryISO=tm.getSimCountryIso();
        boolean isRoaming=tm.isNetworkRoaming();
        String strphoneType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }

        String info="Personal Phone Details:\n";
        info+="\n Network Country ISO:"+networkCountryISO;
        info+="\n SIM Country ISO:"+SIMCountryISO;
        info+="\n Phone Network Type:"+strphoneType;
        info+="\n In Roaming? :"+isRoaming;
        textView.setText(info);//displaying the information in the textView

        PhoneStateListener pListener=new PhoneStateListener(){ /*注册一个监听器*/
            @Override
            public void onCallStateChanged(int state, String number) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE://无任何状态
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK://接听来电
                        textView1.setText("接听了"+number+"的来电\n");
                        Toast.makeText(getApplicationContext(),number,Toast.LENGTH_LONG).show();
                        break;
                    case TelephonyManager.CALL_STATE_RINGING://来电
                        textView1.setText(number+"来电\n");
                        Toast.makeText(getApplicationContext(),number,Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        tm.listen(pListener,PhoneStateListener.LISTEN_CALL_STATE);
    }
}

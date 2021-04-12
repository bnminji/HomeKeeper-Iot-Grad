package org.techtown.led;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Fragment2 extends Fragment {
    MainActivity mActivity;
    private Switch btnPublish1;
    private Switch btnPublish2;
    private Switch btnPublish3;
    int chk = 0;

    LinearLayout fd_layout;
    LinearLayout co_layout;
    ImageView fd_img;
    TextView fd_state;
    TextView fd_value;
    TextView ufd_state;
    TextView ufd_value;
    TextView co_state;
    TextView co_value;

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (MainActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @androidx.annotation.Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        btnPublish1 = (Switch)rootView.findViewById(R.id.switch1);
        btnPublish2 = (Switch)rootView.findViewById(R.id.switch2);
        btnPublish3 = (Switch)rootView.findViewById(R.id.switch3);

        fd_layout = (LinearLayout) rootView.findViewById(R.id.fd_bg);
        co_layout = (LinearLayout) rootView.findViewById(R.id.co_bg);
        fd_img = (ImageView) rootView.findViewById(R.id.fd_img);
        fd_state = (TextView) rootView.findViewById(R.id.fd_state);
        fd_value = (TextView) rootView.findViewById(R.id.fd_value);
        ufd_state = (TextView) rootView.findViewById(R.id.ufd_state);
        ufd_value = (TextView) rootView.findViewById(R.id.ufd_value);
        co_state = (TextView) rootView.findViewById(R.id.co_state);
        co_value = (TextView) rootView.findViewById(R.id.co_value);


        ImageButton graphButton = (ImageButton)rootView.findViewById(R.id.graphButton);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.chartData();
            }
        });
        btnPublish1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(chk==0){
                        btnPublish2.setChecked(true);
                        btnPublish3.setChecked(true);
                        //mymqtt.sendMsg("LED","ALLON");
                        MyEventData tmpData = new MyEventData("LED", "ALLON");
                        EventBus.getDefault().post(tmpData);
                    }
                } else {
                    if(chk==0){
                        btnPublish2.setChecked(false);
                        btnPublish3.setChecked(false);
                        //mymqtt.sendMsg("LED","ALLOFF");
                        MyEventData tmpData = new MyEventData("LED", "ALLOFF");
                        EventBus.getDefault().post(tmpData);
                    }
                }
                chk = 0;
            }
        });

        btnPublish2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(btnPublish3.isChecked()){
                        chk=1;
                        btnPublish1.setChecked(true);
                    }
                    //mymqtt.sendMsg("LED","ONEON");
                    MyEventData tmpData = new MyEventData("LED", "ONEON");
                    EventBus.getDefault().post(tmpData);
                } else {
                    if(btnPublish1.isChecked()){
                        chk=1;
                        btnPublish1.setChecked(false);
                    }
                    //mymqtt.sendMsg("LED","ONEOFF");
                    MyEventData tmpData = new MyEventData("LED", "ONEOFF");
                    EventBus.getDefault().post(tmpData);
                }
            }
        });

        btnPublish3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(btnPublish2.isChecked()){
                        chk=1;
                        btnPublish1.setChecked(true);
                    }
                    //mymqtt.sendMsg("LED","TWOON");
                    MyEventData tmpData = new MyEventData("LED", "TWOON");
                    EventBus.getDefault().post(tmpData);
                } else {
                    if(btnPublish1.isChecked()){
                        chk=1;
                        btnPublish1.setChecked(false);
                    }
                    //mymqtt.sendMsg("LED","TWOOFF");
                    MyEventData tmpData = new MyEventData("LED", "TWOOFF");
                    EventBus.getDefault().post(tmpData);
                }
            }
        });

        return rootView;
    }

    public static Fragment2 newInstance() {
        Bundle args = new Bundle();

        Fragment2 fragment = new Fragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe( threadMode = ThreadMode.MAIN )
    public void myEventData(MyEventData data){
        if (data.tpc == "fd") {
            String fd = data.msg;
            fd_value.setText(fd);
            int fd_mqtt1 = Integer.parseInt(fd);
            if (fd_mqtt1 >= 0 && fd_mqtt1 < 31) {
                fd_state.setText("좋음");
                fd_img.setImageResource(R.drawable.verygood);
                fd_layout.setBackgroundColor(Color.parseColor("#E9F8FF"));
            } else if (fd_mqtt1 >= 31 && fd_mqtt1 < 81) {
                fd_state.setText("보통");
                fd_img.setImageResource(R.drawable.good);
                fd_layout.setBackgroundColor(Color.parseColor("#D9EDDE"));
            } else if (fd_mqtt1 >= 81 && fd_mqtt1 < 151) {
                fd_state.setText("나쁨");
                fd_img.setImageResource(R.drawable.bad);
                fd_layout.setBackgroundColor(Color.parseColor("#FDE0A2"));
            } else if (fd_mqtt1 >= 151) {
                fd_state.setText("매우나쁨");
                fd_img.setImageResource(R.drawable.verybad);
                fd_layout.setBackgroundColor(Color.parseColor("#DE8891"));
            }
        }
        else if (data.tpc == "ufd") {
            String ufd = data.msg;
            ufd_value.setText(ufd);
            int fd_mqtt2 = Integer.parseInt(ufd);
            if (fd_mqtt2 >= 0 && fd_mqtt2 < 16) {
                ufd_state.setText("좋음");
            } else if (fd_mqtt2 >= 16 && fd_mqtt2 < 36) {
                ufd_state.setText("보통");
            } else if (fd_mqtt2 >= 36 && fd_mqtt2 < 76) {
                ufd_state.setText("나쁨");
            } else if (fd_mqtt2 >= 76) {
                ufd_state.setText("매우나쁨");
            }
        }
        else if (data.tpc == "CO") {
            //Log.e("CO", data.msg);
            String co_v = data.msg;
            int co_int = Integer.parseInt(co_v);
            co_value.setText(data.msg);
            if(co_int >= 0 && co_int < 30) {
                co_state.setText("쾌적");
                co_layout.setBackgroundColor(Color.parseColor("#E9F8FF"));
            }
            else if(co_int >= 30 && co_int < 60) {
                co_state.setText("가스 소량 감지");
                co_layout.setBackgroundColor(Color.parseColor("#FDE0A2"));
            }
            else if(co_int >= 60 && co_int < 100) {
                co_state.setText("가스 누출 의심");
                co_layout.setBackgroundColor(Color.parseColor("#DE8891"));
            }
        }
        else if (data.tpc == "DLED") {
            Log.e("DLED", data.msg);
            if(btnPublish1.isChecked()==false){btnPublish1.setChecked(true);}
       }
    }
}
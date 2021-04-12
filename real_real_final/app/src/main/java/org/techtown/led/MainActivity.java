package org.techtown.led;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.NotificationCompat;
    import androidx.core.app.NotificationManagerCompat;
    import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentStatePagerAdapter;
        import androidx.viewpager.widget.PagerAdapter;
        import androidx.viewpager.widget.ViewPager;

    import android.app.Notification;
    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.app.PendingIntent;
    import android.app.ProgressDialog;
    import android.app.TaskStackBuilder;
    import android.content.Intent;
    import android.media.RingtoneManager;
    import android.net.Uri;
    import android.os.AsyncTask;
    import android.os.Build;
    import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

    import com.google.android.material.tabs.TabLayout;

    import org.eclipse.paho.android.service.MqttAndroidClient;
        import org.eclipse.paho.client.mqttv3.IMqttActionListener;
        import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
        import org.eclipse.paho.client.mqttv3.IMqttToken;
        import org.eclipse.paho.client.mqttv3.MqttCallback;
        import org.eclipse.paho.client.mqttv3.MqttClient;
        import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;
        import org.greenrobot.eventbus.EventBus;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import org.w3c.dom.Text;

    import java.io.BufferedReader;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.io.UnsupportedEncodingException;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    private MqttAndroidClient client;
//    ViewPager pager;
    private static int PAGE_NUMBER = 3;
    TabLayout tabs;
    private Fragment fragment4;
    private String mJsonString;
    private static String IP_ADDRESS = "172.30.1.34";
    private static final String TAG = "MainActivity";
    float[] valueArray;
    int chk = 0; int tmpValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String clientId = MqttClient.generateClientId();
        Intent intents = new Intent(getApplicationContext(), MyMQTT.class);
        startService(intents);

        /*Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intent1 = new Intent(MainActivity.this, GetDatabase.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent1, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CO";
            String description = "CO alert";
            CharSequence name1 = "intruder";
            String description1 = "detect intruder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CO", name, importance);
            channel.setDescription(description);
            NotificationChannel channel1 = new NotificationChannel("intruder", name1, importance);
            channel1.setDescription(description1);
            final NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            final NotificationManager notificationManager1 = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
    }
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CO")
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("Mobility Guard for Home Protection")
                .setContentText("CO warning")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        final NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this, "CO")
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle("Mobility Guard for Home Protection")
                .setContentText("An intruder is Detected")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent1)
                .setAutoCancel(true);

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        final Notification noti = builder.build();

        final NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(this);
        final Notification intrnoti = builder1.build();*/

        /*client = new MqttAndroidClient(this.getApplicationContext(), "tcp://172.30.1.50",
                        clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("Connect_success", "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("Connect_fail", "fail");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("Connect_success", "onSuccess");
                    try {
                        client.subscribe("fine/dust1", 0 );
                        client.subscribe("fine/dust2", 0 );
                        client.subscribe("CO",0);
                        Log.d("subscribe_success", "subscribe");
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("Connect_fail", "fail");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }*/

//        pager = (ViewPager) findViewById(R.id.pager);
//        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
//
//        Fragment1 fragment1 = new Fragment1();
//        adapter.addItem(fragment1);

        final Fragment2 fragment2 = new Fragment2();
        //adapter.addItem(fragment2);

        final Fragment3 fragment3 = new Fragment3();
        //adapter.addItem(fragment3);

        fragment4 = new Fragment4();
        //adapter.addItem(fragment4);
        //pager.setAdapter(adapter);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment2).commit();
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("집 관리"));
        tabs.addTab(tabs.newTab().setText("침입감지"));
        tabs.addTab(tabs.newTab().setText("다이어리"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if (position == 0)
                    selected = fragment2;
                else if (position == 1)
                    selected = fragment3;
                else if (position == 2)
                    selected = fragment4;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.equals("fine/dust1")){
                    String msg = new String(message.getPayload());
                    //Log.e("arrive message1 : ", msg);
                    MyEventData tmpData = new MyEventData("fd", msg);
                    EventBus.getDefault().post(tmpData);
                }
                else if(topic.equals("fine/dust2")){
                    String msg = new String(message.getPayload());
                    //Log.e("arrive message2 : ", msg);
                    MyEventData tmpData = new MyEventData("ufd", msg);
                    EventBus.getDefault().post(tmpData);
                }
                else if(topic.equals("LED")){
                    String msg = new String(message.getPayload());
                    Log.e("LED", msg);
                    MyEventData tmpData = new MyEventData("LED", msg);
                    EventBus.getDefault().post(tmpData);
                    notificationManager1.notify(1, intrnoti);
                }
                else if(topic.equals("CO")){
                    String msg = new String(message.getPayload());
                    MyEventData tmpData = new MyEventData("CO", msg);
                    EventBus.getDefault().post(tmpData);
                    tmpValue = Integer.parseInt(msg);
                    notificationManager.notify(1, noti);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });*/


    }

    /*public void sendMsg(String topic, String message) {
        try {
            client.publish(topic, message.getBytes(), 0, false);
//            Log.e("topic : ", message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }*/
    /*class PagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public PagerAdapter(@androidx.annotation.NonNull FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item) {
            items.add(item);
        }

        @androidx.annotation.NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return Fragment2.newInstance();
                case 1:
                    return Fragment3.newInstance();
                case 2:
                    return Fragment4.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_NUMBER;
        }

        @androidx.annotation.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "홈화면";
            } else if (position == 1) {
                return "침입감지";
            } else if (position == 2) {
                return "다이어리";
            }  else if (position == 3) {
                return "다이어리";
            }
            return null;
        }
    }*/

    public void chartData(){
        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php", "");
    }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result == null){
            }
            else {
                mJsonString = result;
                showResult();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String postParameters = params[1];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();
                return null;
            }

        }
    }
    private void showResult(){

        String TAG_JSON="root";
        String TAG_ID = "id";
        String TAG_NAME = "name";
        String TAG_VALUE = "value";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            Intent intent = new Intent(this, chartActivity.class);
            int array_len = jsonArray.length();
            valueArray = new float[array_len-1];
            for(int i=0;i<array_len;i++) {
                jsonObject = jsonArray.getJSONObject(i);
                if (i + 1 < array_len) {
                    valueArray[i] = Float.parseFloat(jsonObject.getString(TAG_VALUE));
                }
            }
            intent.putExtra("values", valueArray);
            startActivity(intent);
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

    }

}

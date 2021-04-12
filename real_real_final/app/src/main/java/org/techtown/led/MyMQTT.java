package org.techtown.led;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyMQTT extends Service {
    private MqttAndroidClient client;
    public MyMQTT() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        EventBus.getDefault().register(this);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intent1 = new Intent(this, GetDatabase.class);
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
        final Notification intrnoti = builder1.build();

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://172.30.1.50",
                clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    //Log.d("Connect_success", "onSuccess1");
                    Log.d("client", client.getClientId());
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
        }

        client.setCallback(new MqttCallback() {
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
                else if(topic.equals("DLED")){
                    String msg = new String(message.getPayload());
                    notificationManager1.notify(1, intrnoti);
                    Log.e("DLED", msg);
                    MyEventData tmpData = new MyEventData("DLED", msg);
                    EventBus.getDefault().post(tmpData);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Subscribe( threadMode = ThreadMode.MAIN )
    public void myEventData(MyEventData data){
        if (data.tpc == "LED") {
            Log.e("eventbus: ", data.msg);
            try {
                client.publish(data.tpc, data.msg.getBytes(), 0, false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}



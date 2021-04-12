package org.techtown.led;

public class MyEventData {
    public String tpc;
    public String msg;
    public MyEventData(String topic, String message){
        this.tpc=topic;
        this.msg=message;
    }
}

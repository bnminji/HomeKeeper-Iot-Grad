import paho.mqtt.client as mqtt
import RPi.GPIO as GPIO
import time
import pir_a
import finedust_a
import led_a
#import intruder_detection_final
#import register
#import firebase_admin
#from firebase_admin import credentials
#from firebase_admin import db
#from firebase_admin import storage
#import datetime
import time
#from random import *
#import mq_a3 as mq
#import alarm

def init():
    global pirPin
    global ser
    global dust
#    global check
    pirPin=pir_a.init()
    (ser,dust)=finedust_a.init()
    led_a.init()
    #mq.init()
    #check = alarm.init()
    #alarm.init()

def on_connect(client, userdata, flags, rc):
    print ("Connect with result code" + str(rc))
    client.subscribe("LED")
    
    #client.subscribe("TURNOFF_WARNING")

def on_message(client, userdata, msg):
    msg.payload = msg.payload.decode("utf-8")
    #if "TURNOFF_WARNING" in msg.payload:
    #    alarm.beepOff()
    #    check = 1
    #    print('changed check: + {0}'.format(check))
    if "ALLON" in msg.payload:
        led_a.led1(True)
        led_a.led2(True)
    if "ALLOFF" in msg.payload:
        led_a.led1(False)
        led_a.led2(False)
    if "ONEON" in msg.payload:
        led_a.led1(True)
    if "ONEOFF" in msg.payload:
        led_a.led1(False)
    if "TWOON" in msg.payload:
        led_a.led2(True)
    if "TWOOFF" in msg.payload:
        led_a.led2(False)
    if "register" in msg.payload:
        register.register()

if __name__ == "__main__":
#    cred = credentials.Certificate('android-mobility-878d2-firebase-adminsdk-j8v6p-ad63ee259a.json')
#   firebase_admin.initialize_app(cred, {
#        'databaseURL': 'https://android-mobility-878d2.firebaseio.com/',
#        'storageBucket': 'android-mobility-878d2.appspot.com'
#    })
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message

    client.connect("18.224.71.242")
    
    mqttc = mqtt.Client("python_pub")
    mqttc.connect("18.224.71.242")

    init()
    client.loop_start()
    mqttc.loop_start()
    
    
    try:
        while True:
            (fd, ufd) = finedust_a.finedust(ser,dust)
            mqttc.publish("fine/dust1", fd)
            mqttc.publish("fine/dust2", ufd)
            if(pir_a.pir(pirPin)):
                print("detected")
#                if(intruder_detection_final.detected()):
                    mqttc.publish("DLED", "detected")
#            mqvalue = round(uniform(1.0,99.0))
            mqttc.publish("CO", mqvalue)
            time.sleep(5)
            #if (mqvalue > 2 and check == 0):
            #    alarm.beepPlay()
            #    mqttc.publish("CO warning", str(mqvalue))
            #if (mqvalue <= 2):
            #    check = 0
    except KeyboardInterrupt:
        GPIO.cleanup()
    client.loop_stop()
    mqttc.loop_stop()

import RPi.GPIO as GPIO
import time 
def init():
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(24, GPIO.OUT)
    GPIO.setup(25, GPIO.OUT)

def led1(state):
    if(state == True):
        GPIO.output(24,True)
    elif(state == False):
        GPIO.output(24,False)

def led2(state):
    if(state == True):
        GPIO.output(25,True)
    elif(state == False):
        GPIO.output(25,False)

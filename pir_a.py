import RPi.GPIO as GPIO
import time

def init():
    GPIO.setmode(GPIO.BCM)
    pirPin=23
    GPIO.setup(pirPin, GPIO.IN)
    time.sleep(2)
    return pirPin

def pir(pirPin):
    if GPIO.input(pirPin):
        return 1

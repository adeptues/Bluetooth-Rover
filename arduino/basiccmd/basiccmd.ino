/*
  Example Bluetooth Serial Passthrough Sketch
 by: Jim Lindblom
 SparkFun Electronics
 date: February 26, 2013
 license: Public domain

 This example sketch converts an RN-42 bluetooth module to
 communicate at 9600 bps (from 115200), and passes any serial
 data between Serial Monitor and bluetooth module.
 */
#include <SoftwareSerial.h>  
//POTENTIAL PIN CONFLICT
int bluetoothTx = 2;  // TX-O pin of bluetooth mate, Arduino D2
int bluetoothRx = 3;  // RX-I pin of bluetooth mate, Arduino D3
const int pinADir = 12;
const int pinBDir = 13;
const int pwmA = 3;//analog CONFLICT
const int pwmB = 11;//analog
const int brakeA = 9;
const int brakeB = 8;
const int curA = 0;//analog input
const int curB = 1;//analog input
int state = 0;
SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

void setup()
{
  Serial.begin(9600);  // Begin the serial monitor at 9600bps
  
  //Configure motor pins
  pinMode(pinADir,OUTPUT);
  //pwm speed
  pinMode(pwmA,OUTPUT);//0 for stop by inertia 255 for max speed
  pinMode(pinBDir,OUTPUT);
  pinMode(pwmB,OUTPUT);

  bluetooth.begin(115200);  // The Bluetooth Mate defaults to 115200bps
  bluetooth.print("$");  // Print three times individually
  bluetooth.print("$");
  bluetooth.print("$");  // Enter command mode
  delay(100);  // Short delay, wait for the Mate to send back CMD
  bluetooth.println("U,9600,N");  // Temporarily Change the baudrate to 9600, no parity
  // 115200 can be too fast at times for NewSoftSerial to relay the data reliably
  bluetooth.begin(9600);  // Start bluetooth serial at 9600
}

//Move the robot forward both wheels
void forward(){
  digitalWrite(pinADir,HIGH);
  digitalWrite(pinBDir,HIGH);
  analogWrite(pwmA,255);
  analogWrite(pwmB,255);
}
//Move the robot backwards both wheels
void reverse(){
  digitalWrite(pinADir,LOW);
  digitalWrite(pinBDir,LOW);
  analogWrite(pwmA,255);
  analogWrite(pwmB,255);
}

void brakeInertia(){
  analogWrite(pwmB,0);
  analogWrite(pwmA,0);
}
//Move left spin motor B 
void left(){
  digitalWrite(pinBDir,LOW);
  digitalWrite(pinADir,HIGH);
  analogWrite(pwmB,255);
  analogWrite(pwmA,255);
}
//Move right spin motor A
void right(){
  digitalWrite(pinBDir,HIGH);
  digitalWrite(pinADir,LOW);
  analogWrite(pwmB,255);
  analogWrite(pwmA,255);
}

void loop()
{
  if(bluetooth.available())  // If the bluetooth sent any characters
  {
    char command =  bluetooth.read();
    if(command == 'w'){//state 1
      if(state != 1){
        brakeInertia();//may have to brake real when change direction
      }
      forward();
      //move forward global direction variable
    }else if(command == 'a' ){//state 2
      if()
      //left
    }else if(command == 'd'){//state 3
      //right
    }else if(command == 's'){//state 4
      //reverse
    }else if(command == 'b'){//state 5
      //brake
    }
    //flush maybe  
  }
}


const int pinADir = 12;
const int pinBDir = 13;
const int pwmA = 3;//analog
const int pwmB = 11;//analog
const int brakeA = 9;
const int brakeB = 8;
const int curA = 0;//analog input
const int curB = 1;//analog input
void setup(){
  //initialise the serial
  Serial.begin(9600);
  //motor a direction
  pinMode(pinADir,OUTPUT);//set to high forwards low backwards
  //pwm speed
  pinMode(pwmA,OUTPUT);//0 for stop by inertia 255 for max speed
  pinMode(pinBDir,OUTPUT);
  pinMode(pwmB,OUTPUT);
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
//Main Control loop
void loop(){
  if(Serial.available() > 0){
    char code = Serial.read();
    if(code == 'A'){
      Serial.println("Spining motor A for 5 secs");
      digitalWrite(pinADir,HIGH);
      analogWrite(pwmA,255);
      delay(5000);//full speed 5 seconds
      analogWrite(pwmA,0);//stop slowly
    }
    if(code == 'B'){
      Serial.println("Spining motor b for 5 secs");
      digitalWrite(pinBDir,HIGH);
      analogWrite(pwmB,255);
      delay(5000);//full speed 5 seconds
      analogWrite(pwmB,0);//stop slowly
    }
    Serial.flush(); 
  }
}



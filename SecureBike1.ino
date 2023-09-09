#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h>
#include <ArduinoJson.h>
#include <PubSubClient.h>

// Set GPIOs for LED and reedswitch
//const int reedSwitch = D2;
//const int led = 2; //optional

// Detects whenever the door changed state
bool changeState = false;

// Holds reedswitch state (1=opened, 0=close)
bool state;
String doorState;

// Auxiliary variables (it will only detect changes that are 1500 milliseconds apart)
unsigned long previousMillis = 0; 
const long interval = 1500;

const char* ssid = "research";
const char* password = "lovelynet2525";



#define WIFI_SSID "ebike"
#define WIFI_PASSWORD "12345678"



const char* mqtt_server = "broker.mqtt_dashboard.com";

WiFiClient espClient;
PubSubClient client(espClient);
unsigned long lastMsg = 0;
#define MSG_BUFFER_SIZE  (100)
char msg[MSG_BUFFER_SIZE];
int value = 0;



void setup_wifi() {
  
  
  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status() != WL_CONNECTED) {
      digitalWrite(D0,LOW);

    Serial.print(".");
       delay(250);
      digitalWrite(D0,HIGH);

    delay(250);
  }
   digitalWrite(D0,HIGH);

  randomSeed(micros());

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  String msg="";
  for (int i = 0; i < length; i++) {
   // Serial.print((char)payload[i]);
    msg=msg+((char)payload[i]);
  }
  Serial.println(msg);

    
      if(msg=="on")
       {
        digitalWrite(D5,0);
 
       Serial.println("pin D5 on");
 
       }

       if(msg=="off")
       {
            digitalWrite(D5,1);
           Serial.println("pin D5 off");
 
       }

      if(msg=="buzzon")
       {
        //digitalWrite(D2,0);
          digitalWrite(D7, 1);
 
       Serial.println("pin D7 on");
 
       }
       if(msg=="buzzoff")
       {
          //  digitalWrite(D2,0);
              digitalWrite(D7, 0);
 
           Serial.println("pin D7 off");
 
       }
}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "pe2025_veh";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      //client.publish("outTopic", "hello world");
      // ... and resubscribe
      client.subscribe("securebike/devices");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {
  Serial.begin(115200);
 // pinMode(reedSwitch, INPUT_PULLUP);
 // state = digitalRead(D6);
  
  // Set the reedswitch pin as interrupt, assign interrupt function and set CHANGE mode
  //attachInterrupt(digitalPinToInterrupt(reedSwitch), changePinStatus, CHANGE);
 
  pinMode(D0, OUTPUT);

  pinMode(D5, OUTPUT);
  //pinMode(D2, OUTPUT);
  pinMode(D7, OUTPUT);
  
  pinMode(D6, INPUT);

digitalWrite(D0,0);
digitalWrite(D5,HIGH);


    setup_wifi();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
 
}

void loop() {

  if (!client.connected()) {
    reconnect();
  }
   client.loop();

    delay(100);
    
}

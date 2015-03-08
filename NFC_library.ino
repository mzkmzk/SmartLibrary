#include <SPI.h>
#include <Ethernet.h>
#include <PN532.h>

#define SCK 7
#define MISO 6
#define MOSI 5
#define SS 4

PN532 nfc(SCK, MISO, MOSI, SS);

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
char server[] = "192.168.0.155";
String hostip="Host: 192.168.0.155";

EthernetClient client_update;


//========================== setup ==================================
void setup() {
  Serial.begin(9600);
  while (!Serial) { 
    ; 
  }
  Serial.println("start Serial_upon");
  // NFC start ;
    nfc.begin();
  
      uint32_t versiondata = nfc.getFirmwareVersion();
    if (! versiondata) {
        Serial.print("Didn't find PN53x board");
        while (1); // halt
    }
    // Got ok data, print it out!
    Serial.print("Found chip PN5"); Serial.println((versiondata>>24) & 0xFF, HEX);
    Serial.print("Firmware ver. "); Serial.print((versiondata>>16) & 0xFF, DEC);
    Serial.print('.'); Serial.println((versiondata>>8) & 0xFF, DEC);
    Serial.print("Supports "); Serial.println(versiondata & 0xFF, HEX);

    // configure board to read RFID tags and cards
    nfc.SAMConfig();
    // NFC end;
    
     delay(100);
  Serial.println("connecting...");
  
 // setup_(*client_1);
   if (client_update.connect(server, 8080)) {
    Serial.println("connected");  
    client_update.println("GET /NFC_Library/NFC_Library/accessID?NFC_ID=0 HTTP/1.1");
    client_update.println(hostip);
    client_update.println();
  }  
  else {
    Serial.println("query connection failed  setup");
  }
}

//========================== loop ==================================
void loop()
{ 
    update_up();
}


void update_up(){
  //断开
  if (!client_update.connected()) {
    Serial.println();
    Serial.println("disconnecting.");
    client_update.stop();
    if (Ethernet.begin(mac) == 0) {
      Serial.println("Failed to configure Ethernet using DHCP");
      Ethernet.begin(mac);
    }
    delay(500);
    Serial.println("connecting...");

    if (client_update.connect(server, 8080)) {
      Serial.println("connected");        
        char str1[70]="GET /NFC_Library/NFC_Library/accessID?NFC_ID=";
       
        
        uint32_t id;
    // look for MiFare type cards
    id = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A);

    if (id != 0) {
        Serial.print("Read card #"); Serial.println(id);

    }
        char str10[30];
      sprintf(str10,"%ld",id/100);
        strcat(str1,str10);
       
          char str11[12]=" HTTP/1.1";
          strcat(str1,str11); 
          Serial.println(str1);
      
      client_update.println(str1);
      client_update.println(hostip);
      client_update.println("Connection: close");
      client_update.println();
    } 
    else {
      Serial.println("connection failed");
    }
  }else{  
     client_update.println("GET /NFC_Library/NFC_Library/accessID?NFC_ID=40");
      client_update.println(hostip);
      client_update.println("Connection: close");
      client_update.println();
  }
  
}







// DecaDuinoReceiver
// Ce croquis est un exemple d'utilisation de la librairie DecaDuino
// Il permet de recevoir un message quelconque envoyé par UWB.
// Il écrit le contenu des octets reçus en HEX
// Il peut-être utilisé comme un sniffer de trames. 
// by Adrien van den Bossche <vandenbo@univ-tlse2.fr>
// Ce croquis fait partir du projet DecaDuino (cf. fichier DecaDuino LICENCE)

#include <SPI.h>
#include <DecaDuino.h>

#define MAX_FRAME_LEN 120
//#define MY_ADDR 32 // Mon adresse

DecaDuino decaduino;  //Interface radio UWB
uint8_t rxData[MAX_FRAME_LEN];  //buffer des données reçues (tableau d'octets)
uint16_t rxLen; // nombre d'octets reçus, <= MAX_FRAME_LEN
uint8_t rxNb; // nombre de trames modulo 255
uint8_t lastnb;
uint8_t compteurTx;
uint8_t compteurTour;
uint8_t nb_erreur;
uint8_t nb_Tx;
double taux_reussite;


void setup()
{
  //Sélection de la LED interne connectée au pin 13 de la carte
  pinMode(13, OUTPUT); 
  Serial.begin(115200); // Init Serial port speed
  SPI.setSCK(14); // Set SPI clock pin (pin 14 on DecaWiNo board)

  // Initialisation de DecaDuino
  if ( !decaduino.init() ) {
    // LED 13 clignotte si init échoue
    Serial.println("decaduino init failed");
    while(1) { digitalWrite(13, HIGH); delay(50); digitalWrite(13, LOW); delay(50); }
  }
  
  // Initialisation du buffer de reception avec rxData, 
  // et de la variable où sera enregistré le nombre d'octets reçus.
  decaduino.setRxBuffer(rxData, &rxLen);
  // Lancement de l'écoute pour la réception d'une trame.
  decaduino.plmeRxEnableRequest();
  // On met a zéro le nombre de trames reçues
  rxNb = 0;
  lastnb = 0;
  compteurTour = 0;
  nb_erreur = 0;
}


void loop()
{  
  // Si un message a été reçu, l'afficher sur le port série 
  // et repasser en écoute
  if ( decaduino.rxFrameAvailable() ) {
    if(rxData[0] == 1 && rxData[1] == 1 && rxData[2] == 4) {
      
    digitalWrite(13, HIGH);
    Serial.print(" rxNb |"); 
    Serial.print(++rxNb); 
    Serial.print("| ");
    compteurTx++;
    if(lastnb > rxData[3])
    {
      compteurTour ++;
    }
    nb_Tx = compteurTour * 255 + rxData[3];
    if(lastnb != rxData[3])
    {
      nb_erreur ++;
    }
    taux_reussite = 1 - (nb_erreur / nb_Tx);
    
    Serial.print(" rxLen |"); 
    Serial.print(rxLen);
    Serial.print("| ");
    
    Serial.print(" DATA: |");
    for (int i=0; i<rxLen; i++) {
      Serial.print(rxData[i], DEC);
    }
    // if(rxData != " )
    
    Serial.print("|");
    Serial.println();

    Serial.println(taux_reussite);
    Serial.print(" nb_erreur |"); 
    Serial.print(nb_erreur);
    Serial.print("| \n");

    lastnb = rxData[3];
    }
   // on repasse en écoute
    decaduino.plmeRxEnableRequest();
    digitalWrite(13, LOW);
    
  }
}



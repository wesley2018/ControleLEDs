/*ONDE EXISTIR "//" SIGNIFICA QUE É UM COMENTÁRIO REFERENTE A LINHA*/

//INCLUSÃO DAS BIBLIOTECAS NECESSÁRIAS PARA A EXECUÇÃO DO CÓDIGO

#include <SPI.h>
#include <Client.h>
#include <Ethernet.h>
#include <Server.h>
#include <Udp.h>
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED }; // NÃO PRECISA MEXER
byte ip[] = { 192, 168, 0, 177 }; // COLOQUE UMA FAIXA DE IP DISPONÍVEL DO SEU ROTEADOR. EX: 192.168.1.110  **** ISSO VARIA, NO MEU CASO É: 192.168.0.177
byte gateway[] = { 192, 168, 0, 1 };// MUDE PARA O GATEWAY PADRÃO DO SEU ROTEADOR **** NO MEU CASO É O 192.168.0.1
byte subnet[] = { 255, 255, 255, 0 }; //NÃO PRECISA MEXER
EthernetServer server(80); //CASO OCORRA PROBLEMAS COM A PORTA 80, UTILIZE OUTRA (EX:8082,8089)
byte sampledata=50;            

int ledPin = 5;  // CRIA UMA VARIÁVEL INTEIRA E DEFINE O PINO REFERENTE
int ledPin2=6; // CRIA UMA VARIÁVEL INTEIRA E DEFINE O PINO REFERENTE
int ledPin3=7; // CRIA UMA VARIÁVEL INTEIRA E DEFINE O PINO REFERENTE

String readString = String(30); //CRIA UMA STRING CHAMADA "readString"

boolean LEDON = false; // DECLARAÇÃO DE VARIÁVEL BOOLEANA(VERDADEIRO OU FALSO)
boolean LED2ON=false; // DECLARAÇÃO DE VARIÁVEL BOOLEANA(VERDADEIRO OU FALSO)
boolean LED3ON=false; // DECLARAÇÃO DE VARIÁVEL BOOLEANA(VERDADEIRO OU FALSO)

String LAMP1; // DECLARAÇÃO DE VARIÁVEL DO TIPO STRING
String LAMP2; // DECLARAÇÃO DE VARIÁVEL DO TIPO STRING
String LAMP3; // DECLARAÇÃO DE VARIÁVEL DO TIPO STRING

void setup(){

  Ethernet.begin(mac, ip, gateway, subnet); // INICIALIZA A CONEXÃO ETHERNET
  
pinMode(ledPin, OUTPUT); // DECLARA QUE O "ledPin" É UMA SAÍDA 
pinMode(ledPin2, OUTPUT); // DECLARA QUE O "ledPin2" É UMA SAÍDA
pinMode(ledPin3, OUTPUT); // DECLARA QUE O "ledPin3" É UMA SAÍDA 
}
void loop(){

EthernetClient client = server.available(); // CRIA UMA VARIÁVEL CHAMADA client
  if (client) { //SE EXISTE CLIENTE
    while (client.connected()) { // ENQUANTO  EXISTIR CLIENTE CONECTADO
   if (client.available()) { // SE EXISTIR CLIENTE HABILITADO
    char c = client.read(); // CRIA A VARIÁVEL c

    if (readString.length() < 100) // SE O ARRAY FOR MENOR QUE 100
      {
        readString += c; // "readstring" VAI RECEBER OS CARACTERES LIDO
      }
        if (c == '\n') { // SE ENCONTRAR "\n" É O FINAL DO CABEÇALHO DA REQUISIÇÃO HTTP
          if (readString.indexOf("?") <0) //SE ENCONTRAR O CARACTER "?"
          {
          }
          else // SENÃO
        if(readString.indexOf("L=1") >0){ // SE ENCONTRAR O PARÂMETRO "L=1"
             digitalWrite(ledPin, HIGH); // ENERGIZA A PORTA "ledPin" 
             LEDON = true; // VARIÁVEL BOOLEANA RECEBE "verdadeiro"
           }
           if(readString.indexOf("M=1") >0){ // SE ENCONTRAR O PARÂMETRO "M=1"
             digitalWrite(ledPin2, HIGH); // ENERGIZA A PORTA "ledPin2" 
              LED2ON = true; // VARIÁVEL BOOLEANA RECEBE "verdadeiro"           
           }
           if(readString.indexOf("N=1") >0){ // SE ENCONTRAR O PARÂMETRO "N=1"
             digitalWrite(ledPin3, HIGH); // ENERGIZA A PORTA "ledPin3" 
             LED3ON = true; // VARIÁVEL BOOLEANA RECEBE "verdadeiro"
            }
            if(readString.indexOf("L=0") >0){ // SE ENCONTRAR O PARÂMETRO "L=0"
             digitalWrite(ledPin, LOW); // DESENERGIZA A PORTA "ledPin"
             LEDON = false; // VARIÁVEL BOOLEANA RECEBE "falso"
           }
           if(readString.indexOf("M=0") >0){ // SE ENCONTRAR O PARÂMETRO "M=0"
             digitalWrite(ledPin2, LOW); // DESENERGIZA A PORTA "ledPin2"
             LED2ON = false; // VARIÁVEL BOOLEANA RECEBE "falso"       
           }
           if(readString.indexOf("N=0") >0){ // SE ENCONTRAR O PARÂMETRO "N=0"
             digitalWrite(ledPin3, LOW); // DESENERGIZA A PORTA "ledPin3"
             LED3ON = false; // VARIÁVEL BOOLEANA RECEBE "falso"           
           }
           if (LEDON == true){ // SE VARIÁVEL BOOLEANA É IGUAL A "verdadeiro"            
             LAMP1 = "AC1"; // STRING RECEBE "AC1"
             }else{
               if (LEDON == false){ // SE VARIÁVEL BOOLEANA É IGUAL A "falso"
               LAMP1 = "AP1"; // STRING RECEBE "AP1"
               }
             }            
             if (LED2ON == true){ // SE VARIÁVEL BOOLEANA É IGUAL A "verdadeiro"            
             LAMP2 = ",AC2,"; // STRING RECEBE "AC2"
             }else{
               if (LED2ON == false){ // SE VARIÁVEL BOOLEANA É IGUAL A "falso"
               LAMP2 = ",AP2,"; // STRING RECEBE "AP2"
               }
             }             
             if (LED3ON == true){ // SE VARIÁVEL BOOLEANA É IGUAL A "verdadeiro"           
             LAMP3 = "AC3"; // STRING RECEBE "AC3"
             }else{
               if (LED3ON == false){ // SE VARIÁVEL BOOLEANA É IGUAL A "falso"
               LAMP3 = "AP3"; // STRING RECEBE "AP3"
               }
             }
          client.println("HTTP/1.1 200 OK"); // ESCREVE PARA O CLIENTE A VERSÃO DO HTTP
          client.println("Content-Type: text/html"); // ESCREVE PARA O CLIENTE O TIPO DE CONTEÚDO(texto/html)
          client.println();
          
           client.println(LAMP1); // RETORNA PARA O CLIENTE O STATUS DO LED 1
           client.println(LAMP2); // RETORNA PARA O CLIENTE O STATUS DO LED 2
           client.println(LAMP3); // RETORNA PARA O CLIENTE O STATUS DO LED 3

           readString="";
          client.stop(); // FINALIZA A REQUISIÇÃO HTTP
            }
          }
        }
      }
 }

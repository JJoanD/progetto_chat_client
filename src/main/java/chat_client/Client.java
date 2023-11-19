package chat_client;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Client {

    String nomeServer = "172.21.233.198";
    int portaServer = 6789;
    Socket mySocket;
    String nomeClient;
    String stringaUtente;
    String stringaRicevuta;
    DataOutputStream outVersoServer;
    BufferedReader tastiera ;
    ClientInputThread inputThread ;
   


    public void createSocket(){
        
        try{

            this.mySocket = new Socket(nomeServer , portaServer);
            outVersoServer = new DataOutputStream(mySocket.getOutputStream());
            inputThread = new ClientInputThread(mySocket);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            
          
            
            joinChat();
           

        }catch(UnknownHostException e){
        System.err.println("Host sconosciuto");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void closeSocket(){
        try{

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void joinChat(){
        
        try{
            stringaRicevuta =  inputThread.receiveMessage();
            System.out.println(stringaRicevuta);

            if(stringaRicevuta.equals("Inserisci il tuo nome: "))
              nomeClient = sendMessage();

            stringaRicevuta =  inputThread.receiveMessage();

            

            if(stringaRicevuta.equals("OK")){
                inputThread.start();
                 for(;;){
                    stringaUtente = sendMessage();

                    if (stringaUtente.equals("/exit")) {
                        break;
                    }
                }
            }


            // uscire
            this.leaveChat();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        

    }

    public void leaveChat(){
         
        try{
            this.outVersoServer.close();
            this.mySocket.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String sendMessage(){
        try{

            String msg = tastiera.readLine();

            outVersoServer.writeBytes(msg + "\n");


            return msg;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }
      
}

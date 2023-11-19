package chat_client;

import java.io.*;
import java.net.Socket;

public class ClientInputThread extends Thread{
    
    Socket clientSocket;
    BufferedReader inDalServer;
    String   msgRicevuto;

    public ClientInputThread(Socket socket){
        try{
            clientSocket = socket;
            inDalServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch(Exception e){}
        
    }

    public String receiveMessage(){
        try{
            msgRicevuto = inDalServer.readLine();

        }catch(Exception e){
            System.out.println("Messaggio non ricevuto");
        }
        
        return msgRicevuto;
    }


    @Override
    public void run() {
        String msg;
        for(;;){
            msg = receiveMessage();
            if (msg.equals("EXIT")) {
                break;
            }
            
            if(msg != null){
                System.out.println(msg);
            }
        }

        try {
            inDalServer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
  
        
    }
}

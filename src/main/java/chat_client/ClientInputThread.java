package chat_client;

import java.io.*;
import java.net.Socket;

public class ClientInputThread extends Thread{
    
    Socket clientSocket;
    Client client;
    BufferedReader inputFromServer;
    String receiveMsg;

    public ClientInputThread(Client client, Socket socket){
        try{
            this.client = client;
            clientSocket = socket;
            inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch(Exception e){}
        
    }

    public String receiveMessage(){
        try{
            receiveMsg = inputFromServer.readLine();

        }catch(Exception e){
            System.out.println("Interrotta la comunicazione col server");
            System.exit(1);
        }
        
        return receiveMsg;
    }


    @Override
    public void run() {

        String msg;

        try {
            for(;;){
                msg = receiveMessage();
                if (msg.equals("EXIT")) {
                    client.leaveChat();
                    break;
                }
                
                if(msg != null){
                    System.out.println(msg);
                }
            
            }
            inputFromServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
  
        
    }
}

package chat_client;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class Client {

    String serverIP = "172.21.232.226";
    int serverPort = 6789;
    Socket mySocket;
    String clientName;
    String sentString;
    String rcvString; 
    DataOutputStream outToServer;
    BufferedReader keyboard ;
    ClientInputThread inputThread ;
   

    
    public void createSocket(){
        
        try{

            this.mySocket = new Socket(serverIP , serverPort);
            outToServer = new DataOutputStream(mySocket.getOutputStream());
            inputThread = new ClientInputThread(this,mySocket);
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            
          
            
            joinChat();

        }catch(UnknownHostException e){
        System.err.println("Host sconosciuto");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void closeSocket(){
        try{
            this.mySocket.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void joinChat(){
        
        try{
            while(true){
            rcvString =  inputThread.receiveMessage();
            System.out.println(rcvString);

            if(rcvString.equals("Inserisci il tuo nome: "))
              clientName = sendMessage();

            rcvString =  inputThread.receiveMessage();

            if(rcvString.equals("BLANK_USER")){
                System.out.println("Nome utente non ammesso.");
                continue;
            }

            if(rcvString.equals("OK")){
                inputThread.start();
                 for(;;){
                    
                    sentString = sendMessage();                  
                }
            }
            
            
            }
            
            
            


        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        
        

    }

    public void leaveChat(){
         
        try{
            this.outToServer.close();
            closeSocket();
            System.exit(1);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String sendMessage(){
        try{

            String msg = keyboard.readLine();

            outToServer.writeBytes(msg + "\n");


            return msg;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return "ERROR";
        }
    }
      
}

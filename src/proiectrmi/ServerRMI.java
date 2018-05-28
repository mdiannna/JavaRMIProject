/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectrmi;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.*;

class ServerOp implements GeneralInterface {
    int val;

    ServerOp(int i) {
        this.val = 0;
    }
    
    @Override
    public void pune(int i) throws RemoteException {
        val += i;
        System.out.println("Un client a pus" + i);
    }

    @Override
    public int curent() throws RemoteException {
        return val;
    }

    @Override
    public void terminate() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getVal() {
        return val;
    }
    
}



public class ServerRMI
{
    static final int PORT = 1100;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {      
        try{
            System.out.println("Serverul a pornit");
            
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = serverSocket.accept();

            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            OutputStream outputStream = clientSocket.getOutputStream();
            ServerOp serverOb = new ServerOp(0);
            
            do
            {
                String method = inputStream.readUTF();
                if (method.equals("pune")) {
                    final int i = inputStream.readInt();
                    serverOb.pune(i);
                }
                else if(method.equals("curent")) {
                    outputStream.write(serverOb.curent());
                }
//
//                if (method.equals("add"))
//                {
//                    final int paramA = inputStream.readInt();
//                    final int paramB = inputStream.readInt();
//                    final int result = serverOb.add(paramA, paramB);
//                    outputStream.write(result);
//                }
                else if (method.equals("terminate"))
                {
                    break;
                }
            }while(true);        
            
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch(Exception e) { System.out.println(e.getMessage()); }         
        
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectrmi;

import java.io.*;
import java.net.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;


/**
 *
 * @author mdiannna
 */
class ClientOp implements GeneralInterface {
    Socket m_socket;
    DataOutputStream m_output;
    InputStream m_input;
    
    static final int PORT = 1100;
    static final String IP = "localhost";
    
    ClientOp() throws IOException
    {
        m_socket = new Socket(IP, PORT);
        m_output= new DataOutputStream(m_socket.getOutputStream());
        m_input = m_socket.getInputStream();
    }
    
    public void pune(int i) throws RemoteException {
        try{
            m_output.writeUTF("pune");
            m_output.writeInt(i);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    };
    
    public int curent() throws RemoteException {
        try {
            m_output.writeUTF("curent");

            int result = m_input.read();
            return result;
        }  catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    };
    
    public void terminate() throws RemoteException
    {
        try{
            m_output.writeUTF("terminate");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}



public class ClientRMI
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {

    
        try{
            System.out.println("Clientul a pornit");
            ClientOp ob = new ClientOp();
            Scanner sc = new Scanner(System.in);

            for (;;)
            {
                //to do:
                String request = sc.next();
                if (request.equals("terminate"))
                {
                    ob.terminate();
                    break;
                }
                else
                if (request.equals("pune"))
                {
                    int i = sc.nextInt();
                    ob.pune(i);
                }
                else
                if (request.equals("curent"))
                {
                    int result = ob.curent();
                    System.out.println("Current calue is " + result);
                }
               
//                break;
            }
        }
        catch(Exception e) { System.out.println(e.getMessage());}
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectrmi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServerOp implements GeneralInterface {
    Places places;
    int val;
    static int reservationIDs = 1;

    ServerOp(int i) {
        this.val = 0;
        places = new Places(20);
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
    
    public ArrayList<Integer> locuriLibere() throws RemoteException {
        ArrayList<Integer> locuriLibere = new ArrayList<Integer>();    
        Places.Iterator it1 = places.createIterator();

        System.out.println("Free places:");
        try {
            for (it1.first(); !it1.isDone(); it1.nextFree()) {
                System.out.println(it1.currentPlace());
                locuriLibere.add(it1.currentPlace());
            }
        } catch (Exception ex) {
            Logger.getLogger(ServerOp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return locuriLibere;
    }

    @Override
    public void terminate() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getVal() {
        return val;
    }

    @Override
    public int reservation(ArrayList<Integer> placesToReserve) throws RemoteException {
        if(places.canReserve(placesToReserve)) {
            reservationIDs++;
            places.reserve(placesToReserve, reservationIDs);
            return reservationIDs;
        } else {
        return -1;
        }
    }
    
}



public class ServerRMI
{
    static final int PORT = 1100;
//    Client id, reservation array list
    HashMap<Integer, ArrayList<Reservation>> reservations = new HashMap<Integer, ArrayList<Reservation>>();
    static int clientsIDs = 0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception 
    {      
        try{
            System.out.println("Serverul a pornit");
            
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = serverSocket.accept();
            int clientID = clientsIDs++;
            
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            OutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            ServerOp serverOb = new ServerOp(0);
            
            
            do
            {
                String method = inputStream.readUTF();
                System.out.println("Method: " + method);
                if (method.equals("pune")) {
                    final int i = inputStream.readInt();
                    serverOb.pune(i);
                }
                else if(method.equals("curent")) {
                    outputStream.write(serverOb.curent());
                }
                else if(method.equals("libere")) {
                    System.out.println(":locurLibere");
                    
//                    outputStream.write(2);

                    ArrayList<Integer> libere = new ArrayList<Integer>(serverOb.locuriLibere());
                    System.out.println("Size:" + libere.size());
                    outputStream.write(libere.size());

                    for (int i = 0; i < libere.size(); i++) {
			System.out.println(libere.get(i));
                        outputStream.write(libere.get(i));
                    }
                }
                else if (method.equals("reservation")) {
                    System.out.println("Reservation");
                    ArrayList<Integer> tryReserve = new ArrayList<Integer>(serverOb.locuriLibere());
                    
                    int size = inputStream.readInt();
                    for(int i=0; i<size; i++) {
                        int nr = inputStream.readInt();
                        tryReserve.add(nr);
                    }
                                        
                    int reservationID = serverOb.reservation(tryReserve);
                    outputStream.write(reservationID);
                }
                else if (method.equals("terminate"))
                {
                    break;
                }
            } while(true);        
            
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch(Exception e) { System.out.println(e.getMessage()); }         
        
    }
    
}
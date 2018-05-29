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
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author mdiannna
 */
class ClientOp implements GeneralInterface {
    Socket m_socket;
    DataOutputStream m_output;
    DataInputStream m_input;
    
    static final int PORT = 1100;
    static final String IP = "localhost";
    
    ClientOp() throws IOException
    {
        m_socket = new Socket(IP, PORT);
        m_output= new DataOutputStream(m_socket.getOutputStream());
        m_input = new DataInputStream(m_socket.getInputStream());
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
    
    @Override
    public ArrayList<Integer> locuriLibere() throws RemoteException {
        ArrayList<Integer> libere = null;
        try  {
            m_output.writeUTF("libere");
        }  catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        
        try {
            
            int size = m_input.read();
            System.out.println("Size:" + size);

            System.out.println("Size:" + size);
            libere = new ArrayList<Integer>();    
            for (int i = 0; i < size; i++) {
                int nr = m_input.read();
                libere.add(nr);
//                System.out.println("Nr: " + nr);
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());

        }
        return libere;
    }
    
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

    @Override
    public int reservation(ArrayList<Integer> placesToReserve) throws RemoteException {
        try{
            m_output.writeUTF("reservation");
            m_output.writeInt(placesToReserve.size());
            for(int i=0; i<placesToReserve.size(); i++) {
                m_output.writeInt(placesToReserve.get(i));
                System.out.println("Wrote " + placesToReserve.get(i) + " to server");
            }

            int reservationID = m_input.read();
            System.out.println("Reservation ID: " + reservationID);
            return reservationID;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
//        -1 = failure
        return -1;
    }

    @Override
    public ArrayList<Integer> getReservation(int id) throws RemoteException {
        try {
            ArrayList<Integer> result = new ArrayList<Integer>();
            m_output.writeUTF("getReservation");
            m_output.writeInt(id);

            int size = m_input.read();
            System.out.println("Size:" + size);
            if(size == -1) {
                System.out.println("Invalid reservation.");
            } else {
                System.out.println("Places reserved:");
                for(int i=0;i<size;i++) {
                    int number = m_input.read();
                    result.add(number);
                    System.out.println(number);
                }
            }
            return result;
        }  catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
}



public class ClientRMI
{
    static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
    
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
                    System.out.println("Current value is " + result);
                }
                else
                if (request.equals("libere"))
                {
                    System.out.println("Locuri libere:");
                    ArrayList<Integer> libere = new ArrayList<Integer>(ob.locuriLibere());
                    for (int i = 0; i < libere.size(); i++) {
                        System.out.println(libere.get(i));
                    }
                } else {
                    if(request.equals("reservation")) {
                        System.out.println("Number of places?");
                        int nrPlaces = sc.nextInt();
                        System.out.println(nrPlaces + " Places:");
                        
                        ArrayList<Integer> placesToReserve = new ArrayList<Integer>();
                        for(int i=0; i<nrPlaces; i++) {
                            int p = sc.nextInt();
                            placesToReserve.add(p);
                        }
                        int reservationId = ob.reservation(placesToReserve);
                        if(reservationId == -1) {
                            System.out.println("Reservation failed.");
                        } else {
                            System.out.println("Reservation succeeded.");
                            Reservation reservation = new Reservation(reservationId, placesToReserve);
                            reservations.add(reservation);
                        }
                    }
                }
                if (request.equals("getReservation"))
                {
                    int i = sc.nextInt();
                    ob.getReservation(i);
                }
               
//                break;
            }
        }
        catch(Exception e) { System.out.println(e.getMessage());}
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author mdiannna
 */
interface GeneralInterface extends Remote {
    public void pune(int i) throws RemoteException;
    public int curent() throws RemoteException;
    public void terminate() throws RemoteException;
    public ArrayList<Integer> locuriLibere() throws RemoteException;
}



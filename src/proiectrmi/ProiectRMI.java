/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectrmi;

/**
 *
 * @author mdiannna
 */
public class ProiectRMI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        new ServerRMI();
    }
    
}

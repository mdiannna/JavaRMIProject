/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectrmi;

import java.util.ArrayList;

/**
 *
 * @author mdiannna
 */
public class Reservation {
    private int reservationId;
    ArrayList<Integer> reservedPlaces;
    
    public Reservation(int resId, ArrayList resPlaces) {
        reservationId = resId;
        reservedPlaces = new ArrayList<>(resPlaces);
    }

    Reservation(ArrayList<Integer> placesToReserve) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getReservationId() {
        return reservationId;
    }
    
    public ArrayList<Integer> getReservedPlaces() {
        return reservedPlaces;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectrmi;

import java.util.*;
import java.util.stream.IntStream;

/**
 *
 * @author mdiannna
 */
public class Places {

    private int maxLength = 20;
    private int list[];

    public Places() {
        list = new int[maxLength];
        Arrays.fill(list, 0);
    }

    public Places(int nrPlaces) {
        list = new int[nrPlaces];
        Arrays.fill(list, 0);
    }

    public int getMaxLength() {
        return maxLength;
    }

    public static class Iterator {

        private Places places;
        private Integer currentPos;
        private Integer currentReserv;

        public Iterator(Places in) {
            places = in;
            currentPos = 0;
            currentReserv = 0;
        }

        public int first() {
            currentPos = 0;
            return 0;
        }

        public void next() throws Exception {
            if (currentPos < places.getMaxLength()) {
                currentReserv = places.list[currentPos];
                currentPos++;
            }
        }

        public void nextFree() {
            boolean incremented = false;
            while (currentPos < places.getMaxLength() - 1 && places.list[currentPos] != 0) {
                currentPos++;
                incremented = true;
            }
            if (!incremented) {
                currentPos++;
            }
            currentReserv = places.list[currentPos];
        }

        public int currentPlace() {
            return currentPos;
        }

        public int currentReservation() {
            return currentReserv;
        }

        public boolean isDone() throws Exception {
            if (currentPos >= 0 && currentPos < places.getMaxLength()) {
                return false;
            }
            return true;
        }

    }

    public void add(int place, int clientID) {
        list[place] = clientID;
    }

    public boolean hasNumber(int i) {
        boolean contains = IntStream.of(list).anyMatch(x -> x == i);
        return contains;
    }

    public int[] getPlacesList() {
        return list;
    }

    public boolean canReserve(ArrayList<Integer> tryPlaces) {
        for (int j = 0; j < tryPlaces.size(); j++) {
            if (list[tryPlaces.get(j)] != 0) {
                return false;
            }
        }
        return true;
    }

    public void reserve(ArrayList<Integer> tryPlaces, int reservationID) {
        for (int j = 0; j < tryPlaces.size(); j++) {
            add(tryPlaces.get(j), reservationID);
        }
    }

    // 2. Add a createIterator() member to the collection class
    public Iterator createIterator() {
        return new Iterator(this);
    }

    public static void main(String[] args) throws Exception {

        Places p = new Places();
        for (int i = 2; i < 10; i += 2) {
            int j = i * 2;
            p.add(i, j);
            System.out.println("Added" + i + "  : " + j);
        }

        Places.Iterator it1 = p.createIterator();

//        System.out.println(it1.first());
//        System.out.println(it1.isDone());
        for (it1.first(); !it1.isDone(); it1.next()) {
            System.out.println(it1.currentPlace() + " :  " + it1.currentReservation());
        }

//        TODO: metoda getFreePlaces in ServerOp si ClientOp
        System.out.println("Free places:");
        for (it1.first(); !it1.isDone(); it1.nextFree()) {
            System.out.println(it1.currentPlace() + " :  " + it1.currentReservation());
        }

    }
}

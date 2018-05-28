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
            if (places.maxLength > 0) {
                return places.list[0];
            }
            return -1;
        }

        public void next() {
            try {
                currentPos++;
                currentReserv = places.list[currentPos];
            } catch (Exception e) {
                currentReserv = null;
            }
        }

        public void nextFree() {
            while (places.list[currentPos] != 0) {
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

        public boolean isDone() {
            try {
                if (currentPos >= 0 && currentPos < places.getMaxLength()) {
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return true;
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

        System.out.println(it1.first());
        System.out.println(it1.isDone());

//                           System.out.println(it1.next());
        for (it1.first(); !it1.isDone(); it1.next()) {
            System.out.println(it1.currentPlace() + " :  " + it1.currentReservation());
        }

        System.out.println("sdfgsdfG");
    }
}

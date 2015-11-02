/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import algorithms.megresort.MergeSort;
import algorithms.naturaljoin.NaturalJoinSort;
import algorithms.simplejoin.SimpleJoinSort;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Witold
 */
public class MainWindow {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SimpleJoinSort simpleJoinSort = new SimpleJoinSort("s.txt", "rS.txt", "AS.txt", "BS.txt");
        NaturalJoinSort naturalJoinSort = new NaturalJoinSort("s.txt", "rN.txt", "AN.txt", "BN.txt");
        MergeSort mergeSort = new MergeSort("s.txt", "rM.txt");
        try {
            simpleJoinSort.sort();
            naturalJoinSort.sort();
            mergeSort.twoWayMergeSort(5);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

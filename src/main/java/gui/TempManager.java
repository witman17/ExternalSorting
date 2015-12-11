/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import algorithms.megresort.MergeSort;
import algorithms.naturaljoin.NaturalJoinSort;
import algorithms.polyphasesort.PolyphaseSort;
import algorithms.simplejoin.SimpleJoinSort;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Witold
 */
public class TempManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainWindow m = new MainWindow("External Sort");
        SimpleJoinSort simpleJoinSort = new SimpleJoinSort("s.txt", "rS.txt", "AS.txt", "BS.txt");
        NaturalJoinSort naturalJoinSort = new NaturalJoinSort("s.txt", "rN.txt", "AN.txt", "BN.txt");
        MergeSort mergeSort1 = new MergeSort("s.txt", "rM-nF.txt", "AM", "BM");
        MergeSort mergeSort2 = new MergeSort("s.txt", "rM-4F.txt", "AM2", "BM2");
        PolyphaseSort polyphaseSort = new PolyphaseSort("s.txt", "rP.txt", "AP.txt", "BP.txt");
        try {
            simpleJoinSort.sort();
            naturalJoinSort.sort();
            mergeSort1.twoWayMergeSortNFiles(1000);
            mergeSort2.twoWayMergeSortFourFiles(1000);
            polyphaseSort.sort(1000);
        } catch (IOException ex) {
            Logger.getLogger(TempManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            m.dispose();
        }
    }

}

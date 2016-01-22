/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import configuration.TestConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Witold
 */
public class ApplicationStart {

    private final static Logger log = LogManager.getLogger("algorithms");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestConfigurationManager manager = new TestConfigurationManager();
        MainWindow m = new MainWindow("External Sort", manager);
//        SimpleJoinSort simpleJoinSort = new SimpleJoinSort("s.txt", "rS.txt", "AS.txt", "BS.txt");
//        NaturalJoinSort naturalJoinSort = new NaturalJoinSort("s.txt", "rN.txt", "AN.txt", "BN.txt");
//        MergeSort mergeSort1 = new MergeSort("s.txt", "rM-nF.txt", "AM", "BM");
//        MergeSort mergeSort2 = new MergeSort("s.txt", "rM-4F.txt", "AM2", "BM2");
//        PolyphaseSort polyphaseSort = new PolyphaseSort("s.txt", "rP.txt", "AP.txt", "BP.txt");
//        try {
//            simpleJoinSort.sort();
//            naturalJoinSort.sort();
//            mergeSort1.twoWayMergeSortNFiles(1000);
//            mergeSort2.twoWayMergeSortFourFiles(1000);
//            polyphaseSort.sort(1000);
//        } catch (IOException ex) {
//            log.error("Blad", ex);
//        } finally {
//            //m.dispose();
//        }
    }

}

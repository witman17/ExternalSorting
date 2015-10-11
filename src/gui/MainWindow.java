/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import algorithms.simplejoin.*;
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
        SimpleJoinSort simpleJoinSort = new SimpleJoinSort("s.txt", "r.txt", "A.txt", "B.txt");
        try {
            simpleJoinSort.sort();
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

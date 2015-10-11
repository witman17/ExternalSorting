/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.simplejoin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Witold
 */
public class SimpleJoinSort {

    private Splitter spriter;
    private Combiner combiner;

    public SimpleJoinSort(String source, String sortedFile, String tempA, String tempB) {
        spriter = new Splitter(source, tempA, tempB);
        combiner = new Combiner(tempA, tempB, sortedFile);

    }

    public void sort() throws IOException {
        int blocksize = 1;
        spriter.split(blocksize);
        spriter.setInputFile(combiner.getOutput());
        do {
            combiner.combine(blocksize);
            blocksize *= 2;
        } while (!spriter.split(blocksize));
        Logger.getLogger(SimpleJoinSort.class.getName()).log(Level.INFO, "Koniec Sort");
        
    }
    private void clean(){
        File tempA = new File(spriter.getOutputA());
        File tempB = new File(spriter.getOutputB());
        tempA.delete();
        tempB.delete();
    }

}

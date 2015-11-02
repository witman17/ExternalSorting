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
 * @author Witold
 */
public class SimpleJoinSort {

    protected SimpleSplitter splitter;
    protected SimpleCombiner combiner;

    public SimpleJoinSort(String source, String sortedFile, String tempA, String tempB) {
        splitter = new SimpleSplitter(source, tempA, tempB);
        combiner = new SimpleCombiner(tempA, tempB, sortedFile);
    }

    public SimpleJoinSort() {

    }

    public void sort() throws IOException {
        int blockSize = 1;
        if (splitter.split(blockSize) > 1) { // jak false to plik jednoelementowy na wejœciu
            splitter.setInputFile(combiner.getOutput());
            do {
                combiner.combine(blockSize);
                blockSize *= 2;
            } while (splitter.split(blockSize) > 1);
        }
        Logger.getLogger(SimpleJoinSort.class.getName()).log(Level.INFO, "Koniec SimpleSort");
        //clean();
    }

    protected void clean() {
        File tempA = new File(splitter.getOutputA());
        File tempB = new File(splitter.getOutputB());
        tempA.delete();
        tempB.delete();
    }

}

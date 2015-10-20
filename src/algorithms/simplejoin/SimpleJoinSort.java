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

    private SimpleSplitter spriter;
    private SimpleCombiner combiner;

    public SimpleJoinSort(String source, String sortedFile, String tempA, String tempB) {
        spriter = new SimpleSplitter(source, tempA, tempB);
        combiner = new SimpleCombiner(tempA, tempB, sortedFile);

    }

    public void sort() throws IOException {
        int blockSize = 1;
        spriter.split(blockSize);
        spriter.setInputFile(combiner.getOutput());
        do {
            combiner.combine(blockSize);
            blockSize *= 2;
        } while (!spriter.split(blockSize));
        Logger.getLogger(SimpleJoinSort.class.getName()).log(Level.INFO, "Koniec SimpleSort");
        //clean();
    }
    private void clean(){
        File tempA = new File(spriter.getOutputA());
        File tempB = new File(spriter.getOutputB());
        tempA.delete();
        tempB.delete();
    }

}

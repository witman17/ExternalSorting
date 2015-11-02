package algorithms.naturaljoin;

import algorithms.simplejoin.SimpleJoinSort;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Witold on 2015-10-21.
 */
public class NaturalJoinSort extends SimpleJoinSort {
    public NaturalJoinSort(String source, String sortedFile, String tempA, String tempB) {
        super();
        splitter = new NaturalSplitter(source, tempA, tempB);
        combiner = new NaturalCombiner(tempA, tempB, sortedFile);
    }

    @Override
    public void sort() throws IOException {
        if (splitter.split() > 1) { // jak false to plik posortowany na wejściu.
            splitter.setInputFile(combiner.getOutput());
            do {
                combiner.combine();
            } while (splitter.split() > 1);
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Koniec NaturalSort");
    }

    @Override
    protected void clean() {
        //TODO napisać cleaner
    }
}

package algorithms.naturaljoin;

import algorithms.simplejoin.SimpleJoinSort;

import java.io.IOException;

/**
 * Created by Witold on 2015-10-21.
 */
public class NaturalJoinSort extends SimpleJoinSort {
    public NaturalJoinSort(String source, String sortedFile, String tempA, String tempB) {
        super();
        splitter = new NaturalSplitter(source, tempA, tempB);
        combiner = new NaturalCombiner(tempA, tempB, sortedFile);
    }

    public NaturalJoinSort(String source, String sortedFile, String tempA, String tempB, int inputBufferSize, int outputBufferSize) {
        super();
        splitter = new NaturalSplitter(source, tempA, tempB, inputBufferSize, outputBufferSize);
        combiner = new NaturalCombiner(tempA, tempB, sortedFile, inputBufferSize, outputBufferSize);
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
    }

    @Override
    public void sort() throws IOException {
        log.info("START");
        if (splitter.split() > 1) { // jak false to plik posortowany na wejściu.
            splitter.setInputFile(combiner.getOutput());
            do {
                combiner.combine();
            } while (splitter.split() > 1);
        }
        clean();
        log.info("END");
    }

}

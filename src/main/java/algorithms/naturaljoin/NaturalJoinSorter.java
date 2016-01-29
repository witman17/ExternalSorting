package algorithms.naturaljoin;

import algorithms.simplejoin.SimpleJoinSorter;

import java.io.IOException;

/**
 * Created by Witold on 2015-10-21.
 */
public class NaturalJoinSorter extends SimpleJoinSorter {
    private final static String startMessage = NaturalJoinSorter.class.getSimpleName() + " - START";
    private final static String endMessage = NaturalJoinSorter.class.getSimpleName() + " - END";

    public NaturalJoinSorter(String source, String sortedFile, String tempA, String tempB) {
        super();
        this.source = source;
        splitter = new NaturalSplitter(source, tempA, tempB);
        combiner = new NaturalCombiner(tempA, tempB, sortedFile);
    }

    public NaturalJoinSorter(String source, String sortedFile, String tempA, String tempB, int inputBufferSize, int outputBufferSize) {
        super();
        this.source = source;
        splitter = new NaturalSplitter(source, tempA, tempB, inputBufferSize, outputBufferSize);
        combiner = new NaturalCombiner(tempA, tempB, sortedFile, inputBufferSize, outputBufferSize);
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
    }

    @Override
    public void sort() throws IOException {
        log.info(startMessage);
        if (splitter.split() > 1) { // jak false to plik posortowany na wejściu.
            splitter.setInputFile(combiner.getOutput());
            do {
                combiner.combine();
            } while (splitter.split() > 1);
        }
        clean();
        log.info(endMessage);
    }

}

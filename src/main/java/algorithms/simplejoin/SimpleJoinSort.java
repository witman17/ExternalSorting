/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.simplejoin;

import algorithms.Sorter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Witold
 */
public class SimpleJoinSort extends Sorter {
    private final static String startMessage = SimpleJoinSort.class.getSimpleName() + " - START";
    private final static String endMessage = SimpleJoinSort.class.getSimpleName() + " - END";
    protected SimpleSplitter splitter;
    protected SimpleCombiner combiner;

    public SimpleJoinSort(String source, String sortedFile, String tempA, String tempB) {
        splitter = new SimpleSplitter(source, tempA, tempB);
        combiner = new SimpleCombiner(tempA, tempB, sortedFile);
    }

    public SimpleJoinSort(String source, String sortedFile, String tempA, String tempB, int inputBufferSize, int outputBufferSize) {
        super(inputBufferSize, outputBufferSize);
        splitter = new SimpleSplitter(source, tempA, tempB, inputBufferSize, outputBufferSize);
        combiner = new SimpleCombiner(tempA, tempB, sortedFile, inputBufferSize, outputBufferSize);
    }

    public SimpleJoinSort() {

    }

    public void sort() throws IOException {
        log.info(startMessage);
        int blockSize = 1;
        if (splitter.split(blockSize) > 1) { // jak false to plik jednoelementowy na wejsciu
            splitter.setInputFile(combiner.getOutput());
            do {
                combiner.combine(blockSize);
                blockSize *= 2;
            } while (splitter.split(blockSize) > 1);
        }
        clean();
        log.info(endMessage);
    }

    protected void clean() throws IOException {
        Files.delete(Paths.get(splitter.getOutputA()));
        Files.delete(Paths.get(splitter.getOutputB()));
    }
    
}

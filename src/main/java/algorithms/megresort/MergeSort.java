package algorithms.megresort;

import algorithms.Sorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

/**
 * Created by Witold on 2015-10-31.
 */
public class MergeSort extends Sorter {
    private final Logger log = LogManager.getLogger("algorithms");
    protected MergeSortSplitter splitter;
    protected String tempA;
    protected String outputFile;

    public MergeSort(String inputFile, String outputFile, String tempA, String tempB) {
        super();
        splitter = new MergeSortSplitter(inputFile, tempA, tempB);
        this.tempA = tempA;
        this.outputFile = outputFile;
    }

    public MergeSort(String inputFile, String outputFile, String tempA, String tempB, int inputBufferSize, int outputBufferSize) {
        super(inputBufferSize, outputBufferSize);
        splitter = new MergeSortSplitter(inputFile, tempA, tempB, inputBufferSize, outputBufferSize);
        this.tempA = tempA;
        this.outputFile = outputFile;
    }

    public void twoWayMergeSortNFiles(int blockSize) throws IOException {
        log.debug("START");
        int filesNumber = splitter.splitNFiles(blockSize);
        if (filesNumber > 1) {
            LinkedList<String> tempOutputs = splitter.getOutputFiles();
            while (tempOutputs.size() > 1) {
                String newTemp = tempA + ++filesNumber;
                log.info(newTemp + " CREATED");
                MergeSortCombiner combiner = new MergeSortCombiner(newTemp, tempOutputs.get(0), tempOutputs.get(1), inputBufferSize, outputBufferSize);
                combiner.combineTwoFiles();
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                tempOutputs.add(newTemp);
            }
            clean(tempOutputs.removeFirst());
        }
        log.debug("END");
    }

    public void twoWayMergeSortFourFiles(int blockSize) {


    }

    public void kWayMergeSort(int blockSize) {

    }

    public void kWayMergeSort(int blocksize, int k) {



    }

    public void clean(String result) throws IOException {
        Files.move(Paths.get(result), Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    @Deprecated
    public void sort() throws IOException {

    }
}

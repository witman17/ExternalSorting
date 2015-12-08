package algorithms.megresort;

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
public class MergeSort {
    private final Logger log = LogManager.getLogger("algorithms");
    protected MergeSortSplitter splitter;
    protected String tempA;
    protected String outputFile;


    public MergeSort(String inputFile, String outputFile, String tempA, String tempB) {
        splitter = new MergeSortSplitter(inputFile, tempA, tempB);
        this.tempA = tempA;
        this.outputFile = outputFile;
    }

    public void twoWayMergeSortNFiles(int blockSize) throws IOException {
        log.debug("START");
        int filesNumber = splitter.splitNFiles(blockSize);
        if (filesNumber > 1) {
            LinkedList<String> tempOutputs = splitter.getOutputFiles();
            int i = 1;
            while (tempOutputs.size() > 1) {
                log.info("MERGE ITERATION START " + i);
                String newTemp = tempA + ++filesNumber;
                MergeSortCombiner combiner = new MergeSortCombiner(newTemp, tempOutputs.get(0), tempOutputs.get(1));
                combiner.combineTwoFiles();
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                tempOutputs.add(newTemp);
                log.info("MERGE ITERATION END " + i++);
            }
            clean(tempOutputs.removeFirst());
        }
        log.debug("END");
    }

    public void twoWayMergeSortFourFiles(int blockSize) {


    }

    public void kWayMergeSort(int blockSize) {

    }

    public void clean(String result) throws IOException {
        Files.move(Paths.get(result), Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

}

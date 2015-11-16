package algorithms.megresort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Witold on 2015-10-31.
 */
public class MergeSort {
    protected MergeSortSplitter splitter;
    protected String outputFile;


    public MergeSort(String inputFile, String outputFile, String tempA, String tempB) {
        splitter = new MergeSortSplitter(inputFile, tempA, tempB);
        this.outputFile = outputFile;
    }

    public void twoWayMergeSortNFiles(int blockSize) throws IOException {
        if (splitter.splitNFiles(blockSize) > 1) {
            LinkedList<String> tempOutputs = splitter.getOutputFiles();
            while (tempOutputs.size() > 1) {
                String newTemp = tempOutputs.get(0) + tempOutputs.get(1);
                MergeSortCombiner combiner = new MergeSortCombiner(newTemp, tempOutputs.get(0), tempOutputs.get(1));
                combiner.combineTwoFiles();
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                tempOutputs.add(newTemp);
            }
            clean(tempOutputs.removeFirst());
        }
        Logger.getLogger(MergeSort.class.getName()).log(Level.INFO, "Koniec MergeSort N-Files");
    }

    public void twoWayMergeSortFourFiles(int blockSize) {


    }

    public void kWayMergeSort(int blockSize) {

    }

    public void clean(String result) throws IOException {
        Files.move(Paths.get(result), Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

}

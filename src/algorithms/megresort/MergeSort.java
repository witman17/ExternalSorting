package algorithms.megresort;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Witold on 2015-10-31.
 */
public class MergeSort {
    protected MergeSortSplitter splitter;
    protected String outputFile;


    public MergeSort(String inputFile, String outputFile) {
        splitter = new MergeSortSplitter(inputFile);
        this.outputFile = outputFile;
    }

    public void sort() {


    }

    public void twoWayMergeSort(int blockSize) throws IOException {
        if (splitter.split(blockSize) > 1) {
            LinkedList<String> tempOutputs = splitter.getOutputFiles();
            while (tempOutputs.size() > 1) {
                String newTemp = tempOutputs.get(0) + tempOutputs.get(1);
                MergeSortCombiner combiner = new MergeSortCombiner(newTemp, tempOutputs.get(0), tempOutputs.get(1));
                combiner.combine();
                File tempA = new File(tempOutputs.removeFirst());
                File tempB = new File(tempOutputs.removeFirst());
                tempA.delete();
                tempB.delete();
                tempOutputs.add(newTemp);
            }
            clean(tempOutputs.removeFirst());
        }
        Logger.getLogger(MergeSort.class.getName()).log(Level.INFO, "Koniec MergeSort");
    }

    public void clean(String result) {
        File resultFile = new File(result);
        resultFile.renameTo(new File("rM.txt"));
    }

}

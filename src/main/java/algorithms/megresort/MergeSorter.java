package algorithms.megresort;

import algorithms.Sorter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;

/**
 * Created by Witold on 2015-10-31.
 *
 */
public class MergeSorter extends Sorter {
    private final static String startTwoWayNMessage = MergeSorter.class.getSimpleName() + " - START - Two Way N Files";
    private final static String startTwoWay4Message = MergeSorter.class.getSimpleName() + " - START - Two Way 4 Files";
    private final static String endTwoWayNMessage = MergeSorter.class.getSimpleName() + " - END - Two Way N Files";
    private final static String endTwoWay4Message = MergeSorter.class.getSimpleName() + " - END - Two Way 4 Files";
    protected MergeSortSplitter splitter;
    protected String tempA;
    protected String outputFile;

    public MergeSorter(String inputFile, String outputFile, String tempA, String tempB) {
        super();
        splitter = new MergeSortSplitter(inputFile, tempA, tempB);
        this.tempA = tempA;
        this.outputFile = outputFile;
    }

    public MergeSorter(String inputFile, String outputFile, String tempA, String tempB, int inputBufferSize, int outputBufferSize) {
        super(inputBufferSize, outputBufferSize);
        splitter = new MergeSortSplitter(inputFile, tempA, tempB, inputBufferSize, outputBufferSize);
        this.tempA = tempA;
        this.outputFile = outputFile;
    }

    public void twoWayMergeSortNFiles(int blockSize) throws IOException {
        log.info(startTwoWayNMessage);
        int filesNumber = splitter.splitNFiles(blockSize);
        if (filesNumber > 1) {
            LinkedList<String> tempOutputs = splitter.getOutputFiles();
            while (tempOutputs.size() > 1) {
                String newTemp = tempA + ++filesNumber;
                log.trace(newTemp + " CREATED");
                MergeSortCombiner combiner = new MergeSortCombiner(newTemp, tempOutputs.get(0), tempOutputs.get(1), inputBufferSize, outputBufferSize);
                combiner.combineTwoFiles();
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                Files.delete(Paths.get(tempOutputs.removeFirst()));
                tempOutputs.add(newTemp);
            }
            clean(tempOutputs.removeFirst());
        } else {
            clean(splitter.getOutputFiles().removeFirst());
        }
        log.info(endTwoWayNMessage);
    }

    public void twoWayMergeSortFourFiles(int blockSize) throws IOException {
        log.info(startTwoWay4Message);
        if (splitter.splitTwoFiles(blockSize) > 1) { // jeśli równe 1 cały plik zmieścił się w pamięci.
            String fileNames[] = new String[4];
            fileNames[3] = outputFile + "B";
            MergeSortCombiner combiner = new MergeSortCombiner(outputFile, splitter.outputA, splitter.outputB, inputBufferSize, outputBufferSize);
            while (combiner.combineFourFiles(fileNames[3]) > 1) {
                fileNames[0] = combiner.getOutput();
                fileNames[1] = fileNames[3];
                fileNames[2] = combiner.getInputs().get(0);
                fileNames[3] = combiner.getInputs().get(1);
                combiner = new MergeSortCombiner(fileNames[2], fileNames[0], fileNames[1]);
            }
            String[] junk = {fileNames[3], fileNames[0], fileNames[1] };
            clean(fileNames[2], junk);
        } else
            clean(splitter.outputA);
        log.info(endTwoWay4Message);
    }

    public void kWayMergeSort(int blockSize) {

    }

    public void kWayMergeSort(int blocksize, int k) {


    }

    public void clean(String result) throws IOException {
        Files.move(Paths.get(result), Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public void clean(String result, String[] junk) throws IOException {
        try {
            for (String fileName : junk)
                Files.delete(Paths.get(fileName));
            Files.move(Paths.get(result), Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        }
        catch (NullPointerException e){
            log.trace(e);
        }
    }

    @Deprecated
    public void sort() throws IOException {

    }
}

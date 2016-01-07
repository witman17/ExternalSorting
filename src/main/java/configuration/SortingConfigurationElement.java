package configuration;

import algorithms.megresort.MergeSort;
import algorithms.naturaljoin.NaturalJoinSort;
import algorithms.polyphasesort.PolyphaseSort;
import algorithms.simplejoin.SimpleJoinSort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Witold on 2015-12-20.
 */
public abstract class SortingConfigurationElement {
    public static final int MERGE_SORT = 0;
    public static final int NATURAL_JOIN_SORT = 1;
    public static final int POLYPHASE_SORT = 2;
    public static final int SIMPLE_JOIN_SORT = 3;

    public static final int BASIC_VARIANT = 11;
    public static final int TWO_WAY_N_FILES_VARIANT = 12;
    public static final int TWO_WAY_4_FILES_VARIANT = 13;
    public static final int K_WAY_VARIANT = 14;

    private final static String tempName = "temp_";


    protected String sourceFileName;
    protected String resultFileName;
    protected int sortAlgorithm;
    protected int variant;
    protected int inputBufferSize;
    protected int outputBufferSize;
    protected int[] sortMethodParameters;

    public SortingConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize,
                                       int[] sortMethodParameters, int sortAlgorithm, int variant) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = sortMethodParameters;
        this.sortAlgorithm = sortAlgorithm;
        this.variant = variant;
    }

    public SortingConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize,
                                       int sortAlgorithm, int variant) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = null;
        this.sortAlgorithm = sortAlgorithm;
        this.variant = variant;
    }

    public void runSorting() throws IOException {
        switch (variant) {
            case MERGE_SORT:
                runMergeSort();
                break;
            case NATURAL_JOIN_SORT:
                runNaturalJoinSort();
                break;
            case POLYPHASE_SORT:
                runPolyphaseSort();
                break;
            case SIMPLE_JOIN_SORT:
                runSimpleJoinSort();
                break;
        }

    }

    private void runSimpleJoinSort() throws IOException {
        String tempA = tempName + SIMPLE_JOIN_SORT + "A";
        String tempB = tempName + SIMPLE_JOIN_SORT + "B";
        SimpleJoinSort sorter = new SimpleJoinSort(sourceFileName, resultFileName, tempA, tempB, inputBufferSize, outputBufferSize);
        sorter.sort();
    }

    private void runPolyphaseSort() throws IOException {
        String tempA = tempName + POLYPHASE_SORT + "A";
        String tempB = tempName + POLYPHASE_SORT + "B";
        PolyphaseSort sorter = new PolyphaseSort(sourceFileName, resultFileName, tempA, tempB, inputBufferSize, outputBufferSize);
        long blocksize;
        if (sortMethodParameters != null)
            blocksize = sortMethodParameters[0];
        else
            blocksize = getBlockSize();
        sorter.sort(blocksize);
    }

    private void runNaturalJoinSort() throws IOException {
        String tempA = tempName + NATURAL_JOIN_SORT + "A";
        String tempB = tempName + NATURAL_JOIN_SORT + "B";
        NaturalJoinSort sorter = new NaturalJoinSort(sourceFileName, resultFileName, tempA, tempB, inputBufferSize, outputBufferSize);
        sorter.sort();
    }

    private void runMergeSort() throws IOException {
        String tempA = tempName + MERGE_SORT + "A";
        String tempB = tempName + MERGE_SORT + "B";
        MergeSort sorter = new MergeSort(sourceFileName, resultFileName, tempA, tempB, inputBufferSize, outputBufferSize);
        long blockSize;
        int k = 4;
        if (sortMethodParameters != null) {
            blockSize = sortMethodParameters[0];
            k = sortMethodParameters[1];
        } else
            blockSize = getBlockSize();
        switch (variant) {
            case TWO_WAY_N_FILES_VARIANT:
                sorter.twoWayMergeSortNFiles(blockSize);
                break;
            case TWO_WAY_4_FILES_VARIANT:
                sorter.twoWayMergeSortFourFiles(blockSize);
                break;
            case K_WAY_VARIANT:
                sorter.kWayMergeSort(blockSize, k);
                break;
        }
    }

    private long getBlockSize() throws IOException {
        long fileSize = Files.size(Paths.get(sourceFileName)) / 4;
        return fileSize / 10;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public int getInputBufferSize() {
        return inputBufferSize;
    }

    public void setInputBufferSize(int inputBufferSize) {
        this.inputBufferSize = inputBufferSize;
    }

    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    public int[] getSortMethodParameters() {
        return sortMethodParameters;
    }

    public void setSortMethodParameters(int[] sortMethodParameters) {
        this.sortMethodParameters = sortMethodParameters;
    }

    public int getSortAlgorithm() {
        return sortAlgorithm;
    }

    public void setSortAlgorithm(int sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }
}

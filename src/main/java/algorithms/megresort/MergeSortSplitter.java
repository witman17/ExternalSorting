package algorithms.megresort;

import algorithms.Splitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Witold on 2015-10-28.
 */
public class MergeSortSplitter extends Splitter {

    protected static final int N_FILES = 1;
    protected static final int TWO_FILES = 2;

    protected String outputA;
    protected String outputB;
    protected BufferedWriter writerA;
    protected BufferedWriter writerB;
    protected LinkedList<String> outputFiles;
    protected int outputBufferSize;

    public MergeSortSplitter(String input, String outputBase) {
        super(input);
        outputA = outputBase;
        outputB = null;
        this.outputBufferSize = 8192;
    }

    public MergeSortSplitter(String input, String outputA, String outputB) {
        super(input);
        this.outputA = outputA;
        this.outputB = outputB;
        this.outputBufferSize = 8192;
    }

    public MergeSortSplitter(String input, String outputA, String outputB, int inputBufferSize, int outputBufferSize) {
        super(input, inputBufferSize);
        this.outputA = outputA;
        this.outputB = outputB;
        this.outputBufferSize = outputBufferSize;
    }

    @Deprecated
    public int split(int blockSize) throws IOException {
        return 0;
    }

    @Deprecated
    public int split() throws IOException {
        return 0;
    }

    public int splitNFiles(long blockSize) throws IOException {
        log.debug("START");
        init(N_FILES);
        int blocksNumber = 1;
        String buffer = "Start";
        ArrayList<Integer> bufferList = new ArrayList<>((int) blockSize);
        while (buffer != null) {
            int i = 0;
            while (i < blockSize && (buffer = reader.readLine()) != null) {
                bufferList.add(Integer.parseInt(buffer));
                i++;
            }
            Collections.sort(bufferList);
            for (Integer number : bufferList) {
                writerA.write(number.toString());
                writerA.newLine();
            }
            bufferList.clear();
            if (buffer != null) {
                writerA.close();
                writerA = null;
                writerA = new BufferedWriter(new FileWriter(outputA + blocksNumber));
                outputFiles.add(outputA + blocksNumber);
                blocksNumber++;
            }
            log.trace("chunk sorted & saved no. " + blocksNumber);
        }
        close();
        log.debug("END");
        return blocksNumber;
    }

    public int splitTwoFiles(int blockSize) throws IOException {
        log.debug("START");
        init(TWO_FILES);
        int blocksNumber = 0;
        boolean currentFile = true;
        String buffer = "Start";
        String lastWrittenA = null;
        String lastWrittenB = null;
        ArrayList<Integer> bufferList = new ArrayList<>(blockSize);
        while (buffer != null) {
            int i = 0;
            // zape�nienie bufora
            while (i < blockSize && (buffer = reader.readLine()) != null) {
                bufferList.add(Integer.parseInt(buffer));
                i++;
            }
            if (bufferList.size() > 0) {
                Collections.sort(bufferList); //sortowanie bufora
                if (currentFile) {
                    for (Integer number : bufferList) {
                        writerA.write(number.toString());
                        writerA.newLine();
                    }
//                    wykrywanie sklejania serii
                    if (lastWrittenA == null || Integer.parseInt(lastWrittenA) > (bufferList.get(0))) {
                        blocksNumber++;
                        currentFile = !currentFile;
                    }
                    lastWrittenA = bufferList.get(bufferList.size() - 1).toString();
                } else {
                    for (Integer number : bufferList) {
                        writerB.write(number.toString());
                        writerB.newLine();
                    }
//                    wykrywanie sklejania serii
                    if (lastWrittenB == null || Integer.parseInt(lastWrittenB) > (bufferList.get(0))) {
                        blocksNumber++;
                        currentFile = !currentFile;
                    }
                    lastWrittenB = bufferList.get(bufferList.size() - 1).toString();
                }
                bufferList.clear();
            }
        }
        close();
        log.debug("END");
        return blocksNumber;
    }


    protected void init(int mode) throws IOException {
        super.init();
        writerA = new BufferedWriter(new FileWriter(outputA));
        if (mode == TWO_FILES) {
            if (outputB == null)
                outputB = outputA + "1";
            writerB = new BufferedWriter(new FileWriter(outputB));
        }
        if (mode == N_FILES) {
            if (outputFiles == null)
                outputFiles = new LinkedList<>();
            else
                outputFiles.clear();
            outputFiles.add(outputA);
        }

    }

    @Override
    protected void close() throws IOException {
        super.close();
        writerA.close();
        if (writerB != null)
            writerB.close();
        writerA = null;
        writerB = null;
    }

    public LinkedList<String> getOutputFiles() {
        return outputFiles;
    }
}

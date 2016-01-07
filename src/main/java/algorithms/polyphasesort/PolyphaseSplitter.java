package algorithms.polyphasesort;

import algorithms.simplejoin.SimpleSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Witold on 2015-11-02.
 */
public class PolyphaseSplitter extends SimpleSplitter {

    protected int SeriesNumberA;
    protected int SeriesNumberB;
    protected boolean currentFile;

    public PolyphaseSplitter(String InputFile, String OutputA, String OutputB) {
        super(InputFile, OutputA, OutputB);
    }

    public PolyphaseSplitter(String InputFile, String OutputA, String OutputB, int inputBufferSize, int outputBufferSize) {
        super(InputFile, OutputA, OutputB, inputBufferSize, outputBufferSize);
    }


    @Override
    public int split(long blockSize) throws IOException {
        log.debug("START - POLYPHASE SORT");
        init();
        String buffer = "Start";
        String lastWrittenA = null;
        String lastWrittenB = null;
        ArrayList<String> bufferList = new ArrayList<>((int) blockSize);
        while (buffer != null) {
            int i = 0;
            while (i < blockSize && (buffer = reader.readLine()) != null) {
                bufferList.add(buffer);
                i++;
            }
            Collections.sort(bufferList);
            if (bufferList.size() > 0) {
                if (currentFile) {
                    for (String buff : bufferList) {
                        writerA.write(buff);
                        writerA.newLine();
                    }
                    if (lastWrittenA == null || lastWrittenA.compareTo(bufferList.get(0)) > 0) {
                        SeriesNumberA++;
                        chooseFile();
                    }
                    lastWrittenA = bufferList.get(bufferList.size() - 1);
                } else {
                    for (String buff : bufferList) {
                        writerB.write(buff);
                        writerB.newLine();
                    }
                    if (lastWrittenB == null || lastWrittenB.compareTo(bufferList.get(0)) > 0) {
                        SeriesNumberB++;
                        chooseFile();
                    }
                    lastWrittenB = bufferList.get(bufferList.size() - 1);
                }
                bufferList.clear();
            }
        }
        close();
        log.debug("END - POLYPHASE SORT");
        return SeriesNumberA + SeriesNumberB;
    }

    private void chooseFile() {
        if (currentFile) {
            if (Fibonacci.isFibonacci(SeriesNumberA))
                currentFile = !currentFile;
        } else {
            if (Fibonacci.isFibonacci(SeriesNumberB))
                currentFile = !currentFile;
        }
    }


    @Override
    protected void init() throws IOException {
        super.init();
        SeriesNumberA = 0;
        SeriesNumberB = 0;
        currentFile = true;
    }

    @Override
    protected void close() throws IOException {
        super.close();
    }

    public int getSeriesNumberA() {
        return SeriesNumberA;
    }

    public int getSeriesNumberB() {
        return SeriesNumberB;
    }
}

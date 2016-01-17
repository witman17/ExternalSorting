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
    public int split(int blockSize) throws IOException {
        log.debug("START - POLYPHASE SORT");
        init();
        String buffer = "Start";
        Integer lastWrittenA = null;
        Integer lastWrittenB = null;
        ArrayList<Integer> bufferList = new ArrayList<>((int) blockSize);
        while (buffer != null) {
            int i = 0;
            while (i < blockSize && (buffer = reader.readLine()) != null) {
                bufferList.add(Integer.parseInt(buffer));
                i++;
            }
            Collections.sort(bufferList);
            if (bufferList.size() > 0) {
                if (currentFile) {
                    for (Integer number : bufferList) {
                        writerA.write(number.toString());
                        writerA.newLine();
                    }
                    //wykrywanie sklejania serii
                    if (lastWrittenA == null || lastWrittenA > bufferList.get(0)) {
                        SeriesNumberA++;
                        chooseFile();
                    }
                    lastWrittenA = bufferList.get(bufferList.size() - 1);
                } else {
                    for (Integer number : bufferList) {
                        writerB.write(number.toString());
                        writerB.newLine();
                    }
                    if (lastWrittenB == null || lastWrittenB > bufferList.get(0)) {
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

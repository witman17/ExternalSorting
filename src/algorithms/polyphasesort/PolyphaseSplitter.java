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
    //


    public PolyphaseSplitter(String InputFile, String OutputA, String OutputB) {
        super(InputFile, OutputA, OutputB);
    }

    @Override
    public int split() throws IOException {
        //TODO maksymalna pojemnoœæ pamiêci
        return 0;
    }

    public int split(int blockSize) throws IOException {
//        TODO wykrywanie sklejania siê serii..
        init();
        String buffer = "Start";
        ArrayList<String> bufferList = new ArrayList<>(blockSize);
        while (buffer != null) {
            int i = 0;
            while (i < blockSize && (buffer = reader.readLine()) != null) {
                bufferList.add(buffer);
                i++;
            }
            Collections.sort(bufferList);
            chooseFile();
            if (bufferList.size() > 0) {
                if (currentFile) {
                    for (String buff : bufferList) {
                        writerA.write(buff);
                        writerA.newLine();
                    }
                    SeriesNumberA++;
                } else {
                    for (String buff : bufferList) {
                        writerB.write(buff);
                        writerB.newLine();
                    }
                    SeriesNumberB++;
                }
                bufferList.clear();
            }
        }
        close();
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

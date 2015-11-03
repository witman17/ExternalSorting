package algorithms.polyphasesort;

import algorithms.simplejoin.SimpleSplitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Witold on 2015-11-02.
 */
public class PolyphaseSplitter extends SimpleSplitter {

    protected int blocksNumberA;
    protected int blocksNumberB;
    protected boolean currentFile;


    public PolyphaseSplitter(String InputFile, String OutputA, String OutputB) {
        super(InputFile, OutputA, OutputB);
    }

    @Override
    public int split() throws IOException {
        //TODO maksymalna pojemnoœæ pamiêci
        return 0;
    }

    public int split(int blockSize) throws IOException {
        init();
        int blocksNumber = 0;
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
            if (currentFile)
                for (String buff : bufferList) {
                    writerA.write(buff);
                    writerA.newLine();
                    blocksNumberA++;
                }
            else
                for (String buff : bufferList) {
                    writerB.write(buff);
                    writerB.newLine();
                    blocksNumberB++;
                }
            bufferList.clear();
        }
        close();
        return blocksNumber;
    }

    private boolean chooseFile() {
        if (currentFile && Fibonacci.isFibonacci(blocksNumberA))
            return !currentFile;
        if (!currentFile && Fibonacci.isFibonacci(blocksNumberB))
            return !currentFile;
        return currentFile;

    }


    @Override
    protected void init() throws IOException {
        super.init();
        blocksNumberA = 0;
        blocksNumberB = 0;
        currentFile = true;
    }

//    @Override
//    protected void close() throws IOException {
//        super.close();
//    }
}

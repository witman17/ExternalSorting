package algorithms.megresort;

import algorithms.Combiner;
import algorithms.SeriesReader;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Witold on 2015-10-28.
 */
public class MergeSortCombiner extends Combiner {
    protected SeriesReader seriesReaderA;
    protected SeriesReader seriesReaderB;
    protected ArrayList<BufferedReader> readers;
    protected BufferedWriter writerB;
    protected LinkedList<String> inputs;
    protected int inputBufferSize;

    public MergeSortCombiner(String outputFile, LinkedList<String> inputFiles) throws IllegalArgumentException {
        super(outputFile);
        if (inputFiles.size() < 2)
            throw new IllegalArgumentException();
        this.inputs = inputFiles;
        this.inputBufferSize = 8192;
    }

    public MergeSortCombiner(String outputFile, String inputA, String inputB) {
        super(outputFile);
        inputs = new LinkedList<>();
        inputs.add(inputA);
        inputs.add(inputB);
        this.inputBufferSize = 8192;
    }

    public MergeSortCombiner(String outputFile, String inputA, String inputB, int inputBufferSize, int outputBufferSize) {
        super(outputFile, outputBufferSize);
        inputs = new LinkedList<>();
        inputs.add(inputA);
        inputs.add(inputB);
        this.inputBufferSize = inputBufferSize;
    }

    @Deprecated
    public void combine() throws IOException {

    }

    public void combineTwoFiles() throws IOException {
        log.debug("START");
        init();
        String bufferA = readers.get(0).readLine();
        String bufferB = readers.get(1).readLine();
        while (bufferA != null && bufferB != null) {
            if (bufferA.compareTo(bufferB) < 0) {
                writer.write(bufferA);
                bufferA = readers.get(0).readLine();
            } else {
                writer.write(bufferB);
                bufferB = readers.get(1).readLine();
            }
            writer.newLine();
        }
        //dopisanie ko�c�wek plik�w
        while (bufferA != null) {
            writer.write(bufferA);
            writer.newLine();
            bufferA = readers.get(0).readLine();
        }
        while (bufferB != null) {
            writer.write(bufferB);
            writer.newLine();
            bufferB = readers.get(1).readLine();
        }
        close();
        log.debug("END");
    }

    public int combineFourFiles(String outputB) throws IOException {
        log.debug("START");
        init();
        int blocksNumber = 0;
        boolean currentFile = true;
        BufferedWriter currentWriter;
        seriesReaderA = new SeriesReader(readers.get(0));
        seriesReaderB = new SeriesReader(readers.get(1));
        writerB = new BufferedWriter(new FileWriter(outputB));
        //pobranie pierwszych dwoch elementow
        String bufferA = seriesReaderA.getSeriesElement();
        String bufferB = seriesReaderB.getSeriesElement();
        while (bufferA != null && bufferB != null) {
            blocksNumber++;
            if (currentFile)
                currentWriter = writer;
            else
                currentWriter = writerB;
            // por�wnanie dwoch element�w serii, przepisanie mniejszego
            while (bufferA != null && bufferB != null && !seriesReaderA.isSeriesEnded() && !seriesReaderB.isSeriesEnded()) {
                if (bufferA.compareTo(bufferB) < 0) {
                    currentWriter.write(bufferA);
                    bufferA = seriesReaderA.getSeriesElement();
                } else {
                    currentWriter.write(bufferB);
                    bufferB = seriesReaderB.getSeriesElement();
                }
                currentWriter.newLine();
            }
            //dopisanie do pozosta�ych element�w, serri kt�ra si� nie sko�czy�a
            while (!seriesReaderA.isSeriesEnded() && bufferA != null) {
                currentWriter.write(bufferA);
                currentWriter.newLine();
                bufferA = seriesReaderA.getSeriesElement();
            }
            while (!seriesReaderB.isSeriesEnded() && bufferB != null) {
                currentWriter.write(bufferB);
                currentWriter.newLine();
                bufferB = seriesReaderB.getSeriesElement();
            }
            currentFile = !currentFile;
            seriesReaderA.resetSeriesEnd();
            seriesReaderB.resetSeriesEnd();
        }
        writerB.close();
        close();
        log.debug("END");
        return blocksNumber;
    }

    @Override
    protected void init() throws IOException {
        super.init();
        readers = new ArrayList<>(2);
        int oneBuffSize = inputBufferSize / inputs.size();
        if (oneBuffSize < 8192)
            oneBuffSize = 8192;
        for (String source : inputs) {
            readers.add(new BufferedReader(new FileReader(source), oneBuffSize));
        }
    }


    @Override
    protected void close() throws IOException {
        super.close();
        for (int i = 0; i < readers.size(); i++) {
            readers.get(i).close();
        }
        readers.clear();
        readers = null;
    }

    public LinkedList<String> getInputs() {
        return inputs;
    }

}

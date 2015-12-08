package algorithms.megresort;

import algorithms.Combiner;
import algorithms.naturaljoin.SeriesReader;

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
    protected String outputB;
    protected int inputBufferSize;

    public MergeSortCombiner(String outputFile, LinkedList<String> inputFiles) throws IllegalArgumentException {
        super(outputFile);
        outputB = outputFile + "B";
        if (inputFiles.size() < 2)
            throw new IllegalArgumentException();
        this.inputs = inputFiles;
        this.inputBufferSize = 8192;
    }

    public MergeSortCombiner(String outputFile, String inputA, String inputB) {
        super(outputFile);
        outputB = outputFile + "B";
        inputs = new LinkedList<>();
        inputs.add(inputA);
        inputs.add(inputB);
        this.inputBufferSize = 8192;
    }

    public MergeSortCombiner(String outputFile, String inputA, String inputB, int inputBufferSize, int outputBufferSize) {
        super(outputFile, outputBufferSize);
        outputB = outputFile + "B";
        inputs = new LinkedList<>();
        inputs.add(inputA);
        inputs.add(inputB);
        this.inputBufferSize = inputBufferSize;
    }

    @Deprecated
    public void combine() throws IOException {

    }

    public void combineTwoFiles() throws IOException {
        log.info("START");
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
        log.info("END");
    }

    public void combineFourFiles() throws IOException {
        //TODO poprawic
        init();
        seriesReaderA = new SeriesReader(readers.get(0));
        seriesReaderB = new SeriesReader(readers.get(1));
        writerB = new BufferedWriter(new FileWriter(outputB));
        //pobranie pierwszych dwoch elementow
        String bufferA = seriesReaderA.getSeriesElement();
        String bufferB = seriesReaderB.getSeriesElement();
        while (bufferA != null && bufferB != null) {

            // por�wnanie dwoch element�w serii, przepisanie mniejszego
            while (bufferA != null && bufferB != null && !seriesReaderA.isSeriesEnded() && !seriesReaderB.isSeriesEnded()) {
                if (bufferA.compareTo(bufferB) < 0) {
                    writer.write(bufferA);
                    bufferA = seriesReaderA.getSeriesElement();
                } else {
                    writer.write(bufferB);
                    bufferB = seriesReaderB.getSeriesElement();
                }
                writer.newLine();
            }
            //dopisanie do pozosta�ych element�w, serri kt�ra si� nie sko�czy�a
            while (!seriesReaderA.isSeriesEnded() && bufferA != null) {
                writer.write(bufferA);
                writer.newLine();
                bufferA = seriesReaderA.getSeriesElement();
            }
            while (!seriesReaderB.isSeriesEnded() && bufferB != null) {
                writer.write(bufferB);
                writer.newLine();
                bufferB = seriesReaderB.getSeriesElement();
            }
            seriesReaderA.resetSeriesEnd();
            seriesReaderB.resetSeriesEnd();
        }

        close();
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

    //    TODO pomy�le�
    protected void reInit() throws IOException {
        String tempA = inputs.get(0);
        String tempB = inputs.get(1);
        inputs.clear();
        inputs.add(output);
        inputs.add(outputB);
        output = tempA;
        outputB = tempB;
        for (BufferedReader reader : readers)
            reader.close();
        writer.close();
        writerB.close();

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

}

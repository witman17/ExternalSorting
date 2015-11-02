package algorithms.megresort;

import algorithms.Combiner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Witold on 2015-10-28.
 */
public class MergeSortCombiner extends Combiner {
    protected ArrayList<BufferedReader> readers;
    protected LinkedList<String> inputs;

    public MergeSortCombiner(String outputFile, LinkedList<String> inputFiles) throws IllegalArgumentException {
        super(outputFile);
        if (inputFiles.size() < 2)
            throw new IllegalArgumentException();
        this.inputs = inputFiles;

    }

    public MergeSortCombiner(String outputFile, String inputA, String inputB) {
        super(outputFile);
        inputs = new LinkedList<>();
        inputs.add(inputA);
        inputs.add(inputB);
    }

    @Override
    public void combine() throws IOException {
        init();
        twoWayMerge();
        close();
    }

    private void twoWayMerge() throws IOException {
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
        //dopisanie koñcówek plików
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
    }

    @Override
    protected void init() throws IOException {
        super.init();
        readers = new ArrayList<>(2);
        for (int i = 0; i < inputs.size(); i++) {
            readers.add(new BufferedReader(new FileReader(inputs.get(i))));
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

}

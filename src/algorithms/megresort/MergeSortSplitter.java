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
    public static String output = "M";
    protected BufferedWriter writer;
    protected LinkedList<String> outputFiles;

    public MergeSortSplitter(String inputFile) {
        super(inputFile);
    }

    @Override
    public int split() throws IOException {
        //TODO dzielenie z maksymaln¹ dostêpn¹ pamiêci¹.
        return 0;
    }

    public int split(int blockSize) throws IOException {
        init();
        int blocksNumber = 1;
        String buffer = "Start";
        ArrayList<String> bufferList = new ArrayList<>(blockSize);
        while (buffer != null) {
            int i = 0;
            while (i < blockSize && (buffer = reader.readLine()) != null) {
                bufferList.add(buffer);
                i++;
            }
            Collections.sort(bufferList);
            for (String s : bufferList) {
                writer.write(s);
                writer.newLine();
            }
            bufferList.clear();
            if (buffer != null) {
                writer.close();
                writer = null;
                writer = new BufferedWriter(new FileWriter(output + blocksNumber));
                outputFiles.add(output + blocksNumber);
                blocksNumber++;
            }
        }
        close();
        return blocksNumber;
    }

    @Override
    protected void init() throws IOException {
        super.init();
        writer = new BufferedWriter(new FileWriter(output + "0"));
        if (outputFiles == null)
            outputFiles = new LinkedList<>();
        else
            outputFiles.clear();
        outputFiles.add(output + "0");
    }

    @Override
    protected void close() throws IOException {
        super.close();
        writer.close();
        writer = null;
    }

    public LinkedList<String> getOutputFiles() {
        return outputFiles;
    }
}

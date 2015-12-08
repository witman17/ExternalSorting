/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package algorithms.simplejoin;

import algorithms.Splitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Witold
 */
public class SimpleSplitter extends Splitter {

    protected String outputA;
    protected String outputB;
    protected BufferedWriter writerA;
    protected BufferedWriter writerB;

    public SimpleSplitter(String InputFile, String OutputA, String OutputB) {
        super(InputFile);
        this.outputA = OutputA;
        this.outputB = OutputB;
        this.reader = null;
        this.writerA = null;
        this.writerB = null;
    }

    public int split(int blockSize) throws IOException {
        String buffer = "Start";
        int blocksNumber = 0;
        int counter;
        init();
        while (buffer != null) {
            counter = 0;
            while (counter < blockSize && (buffer = reader.readLine()) != null) {
                counter++;
                writerA.write(buffer);
                writerA.newLine();
            }
            if (counter > 0)
                blocksNumber++;
            counter = 0;
            while (counter < blockSize && (buffer = reader.readLine()) != null) {
                counter++;
                writerB.write(buffer);
                writerB.newLine();
            }
            if (counter > 0)
                blocksNumber++;

        }
        this.close();
        return blocksNumber;
    }

    @Override
    protected void init() throws IOException {
        super.init();
        writerA = new BufferedWriter(new FileWriter(outputA));
        writerB = new BufferedWriter(new FileWriter(outputB));
    }

    @Override
    protected void close() throws IOException {
        super.close();
        writerA.close();
        writerB.close();
        writerA = null;
        writerB = null;
    }

    @Override
    public int split() throws IOException {
        return 0;
    }

    /**
     * Get the value of outputB
     *
     * @return the value of outputB
     */
    public String getOutputB() {
        return outputB;
    }

    /**
     * Get the value of outputA
     *
     * @return the value of outputA
     */
    public String getOutputA() {
        return outputA;
    }

    /**
     * Set the value of InputFile
     *
     * @param InputFile new value of InputFile
     */
    public void setInputFile(String InputFile) {
        this.input = InputFile;
    }

}

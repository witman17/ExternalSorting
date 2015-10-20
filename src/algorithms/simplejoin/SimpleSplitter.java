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
 *
 * @author Witold
 */
public class SimpleSplitter extends Splitter {

    protected String OutputA;
    protected String OutputB;
    protected BufferedWriter writerA;
    protected BufferedWriter writerB;

    public SimpleSplitter(String InputFile, String OutputA, String OutputB) {
        super(InputFile);
        this.OutputA = OutputA;
        this.OutputB = OutputB;
        this.reader = null;
        this.writerA = null;
        this.writerB = null;
    }

    @Override
    public boolean split(int blockSize) throws IOException {
        String buffer = "Start";
        boolean oneBlock = true;
        int counter;
        init();
        while (buffer != null) {
            counter = 0;
            while (counter < blockSize && (buffer = reader.readLine()) != null) {
                counter++;
                writerA.write(buffer);
                writerA.newLine();
            }
            counter = 0;
            while (counter < blockSize && (buffer = reader.readLine()) != null) {
                oneBlock = false;
                counter++;
                writerB.write(buffer);
                writerB.newLine();
            }

        }
        this.close();
        return oneBlock;
    }

    @Override
    protected void init() throws IOException {
        super.init();
        writerA = new BufferedWriter(new FileWriter(OutputA));
        writerB = new BufferedWriter(new FileWriter(OutputB));
    }

    @Override
    protected void close() throws IOException {
        super.close();
        writerA.close();
        writerB.close();
        writerA = null;
        writerB = null;
    }

    /**
     * Get the value of OutputB
     *
     * @return the value of OutputB
     */
    public String getOutputB() {
        return OutputB;
    }

    /**
     * Set the value of OutputB
     *
     * @param OutputB new value of OutputB
     */
    public void setOutputB(String OutputB) {
        this.OutputB = OutputB;
    }

    /**
     * Get the value of OutputA
     *
     * @return the value of OutputA
     */
    public String getOutputA() {
        return OutputA;
    }

    /**
     * Set the value of OutputA
     *
     * @param OutputA new value of OutputA
     */
    public void setOutputA(String OutputA) {
        this.OutputA = OutputA;
    }

    /**
     * Get the value of InputFile
     *
     * @return the value of InputFile
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * Set the value of InputFile
     *
     * @param InputFile new value of InputFile
     */
    public void setInputFile(String InputFile) {
        this.inputFile = InputFile;
    }

}

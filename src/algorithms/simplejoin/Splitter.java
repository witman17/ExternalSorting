/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package algorithms.simplejoin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Witold
 */
public class Splitter {

    private String InputFile;

    private String OutputA;

    private String OutputB;

    private BufferedReader reader;
    private BufferedWriter writerA;
    private BufferedWriter writerB;

    public Splitter(String InputFile, String OutputA, String OutputB) {
        this.InputFile = InputFile;
        this.OutputA = OutputA;
        this.OutputB = OutputB;
        this.reader = null;
        this.writerA = null;
        this.writerB = null;
    }

    public void split(int blocksize) throws IOException {
        String buffer = "Start";
        int counter;
        init();
        while (buffer != null) {
            counter = 0;
            while (counter < blocksize && (buffer = reader.readLine()) != null) {
                counter++;
                writerA.write(buffer);
            }
            if (buffer != null) {
                counter = 0;
                while (counter < blocksize && (buffer = reader.readLine()) != null) {
                    counter++;
                    writerB.write(buffer);
                }
            }
        }
        this.close();

    }

    private void init() throws FileNotFoundException, IOException {
        reader = new BufferedReader(new FileReader(InputFile));
        writerA = new BufferedWriter(new FileWriter(OutputA));
        writerB = new BufferedWriter(new FileWriter(OutputB));
    }

    private void close() throws IOException {
        reader.close();
        writerA.close();
        writerB.close();
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
        return InputFile;
    }

    /**
     * Set the value of InputFile
     *
     * @param InputFile new value of InputFile
     */
    public void setInputFile(String InputFile) {
        this.InputFile = InputFile;
    }

}

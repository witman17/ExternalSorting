/*
 
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
public class Combiner {

    private String inputA;

    private String InputB;

    private String output;

    private BufferedReader readerA;
    private BufferedReader readerB;
    private BufferedWriter writer;

    public Combiner(String inputA, String InputB, String output) {
        this.inputA = inputA;
        this.InputB = InputB;
        this.output = output;
        readerA = null;
        readerB = null;
        writer = null;
    }

    public void combine(int blocksize) throws IOException {
        String bufferA = "Start";
        String bufferB = "Start";
        int counterA;
        int counterB;
        init();
        while (bufferA != null && bufferB != null) {
            counterA = 0;
            counterB = 0;
            bufferA = readerA.readLine();
            bufferB = readerB.readLine();
            while (counterA < blocksize && counterB < blocksize && bufferA != null && bufferB != null) {
                if (bufferA.compareTo(bufferB) < 0) {
                    counterA++;
                    writer.write(bufferA);
                    bufferA = readerA.readLine();
                } else {
                    counterB++;
                    writer.write(bufferB);
                    bufferB = readerB.readLine();
                }
            }
            while (counterA < blocksize && bufferA != null) {
                
                
                
                
            }

        }

        close();
    }

    private void init() throws IOException {
        readerA = new BufferedReader(new FileReader(inputA));
        readerB = new BufferedReader(new FileReader(InputB));
        writer = new BufferedWriter(new FileWriter(output));
    }

    private void close() throws IOException {
        readerA.close();
        readerB.close();
        writer.close();

    }

    /**
     * Get the value of output
     *
     * @return the value of output
     */
    public String getOutput() {
        return output;
    }

    /**
     * Set the value of output
     *
     * @param output new value of output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * Get the value of InputB
     *
     * @return the value of InputB
     */
    public String getInputB() {
        return InputB;
    }

    /**
     * Set the value of InputB
     *
     * @param InputB new value of InputB
     */
    public void setInputB(String InputB) {
        this.InputB = InputB;
    }

    /**
     * Get the value of inputA
     *
     * @return the value of inputA
     */
    public String getInputA() {
        return inputA;
    }

    /**
     * Set the value of inputA
     *
     * @param inputA new value of inputA
     */
    public void setInputA(String inputA) {
        this.inputA = inputA;
    }

}

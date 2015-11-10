/*
 
 */
package algorithms.simplejoin;

import algorithms.Combiner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Witold
 */
public class SimpleCombiner extends Combiner {

    protected String inputA;
    protected String inputB;
    protected BufferedReader readerA;
    protected BufferedReader readerB;

    public SimpleCombiner(String inputA, String inputB, String output) {
        super(output);
        this.inputA = inputA;
        this.inputB = inputB;
        readerA = null;
        readerB = null;
    }

    public SimpleCombiner() {

    }

    @Override
    public void combine() throws IOException {


    }

    public void combine(int blockSize) throws IOException {
        String bufferA, bufferB;
        int counterA, counterB;
        init();
        bufferA = readerA.readLine();
        bufferB = readerB.readLine();
        while (bufferA != null && bufferB != null) {
            counterA = 0;
            counterB = 0;
            while (counterA < blockSize && counterB < blockSize && bufferA != null && bufferB != null) {
                if (bufferA.compareTo(bufferB) < 0) {
                    counterA++;
                    writer.write(bufferA);
                    bufferA = readerA.readLine();
                } else {
                    counterB++;
                    writer.write(bufferB);
                    bufferB = readerB.readLine();
                }
                writer.newLine();
            }
            while (counterA < blockSize && bufferA != null) {
                writer.write(bufferA);
                writer.newLine();
                counterA++;
                bufferA = readerA.readLine();

            }
            while (counterB < blockSize && bufferB != null) {
                writer.write(bufferB);
                writer.newLine();
                counterB++;
                bufferB = readerB.readLine();
            }
        }
        while (bufferA != null) {
            writer.write(bufferA);
            writer.newLine();
            bufferA = readerA.readLine();
        }
        while (bufferB != null) {
            writer.write(bufferB);
            writer.newLine();
            bufferB = readerB.readLine();
        }
        close();
    }

    @Override
    protected void init() throws IOException {
        super.init();
        readerA = new BufferedReader(new FileReader(inputA));
        readerB = new BufferedReader(new FileReader(inputB));
    }

    @Override
    protected void close() throws IOException {
        super.close();
        readerA.close();
        readerB.close();
        readerA = null;
        readerB = null;
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
     * Get the value of inputB
     *
     * @return the value of inputB
     */
    public String getInputB() {
        return inputB;
    }

    /**
     * Set the value of inputB
     *
     * @param InputB new value of inputB
     */
    public void setInputB(String InputB) {
        this.inputB = InputB;
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

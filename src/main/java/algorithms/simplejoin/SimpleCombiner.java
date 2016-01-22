/*
 
 */
package algorithms.simplejoin;

import algorithms.Combiner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Witold
 */
public class SimpleCombiner extends Combiner {
    private final static String startMessage = SimpleCombiner.class.getSimpleName() + " - START";
    private final static String endMessage = SimpleCombiner.class.getSimpleName() + " - END";
    protected String inputA;
    protected String inputB;
    protected BufferedReader readerA;
    protected BufferedReader readerB;
    protected int inputBufferSize;

    public SimpleCombiner(String inputA, String inputB, String output) {
        super(output);
        this.inputA = inputA;
        this.inputB = inputB;
        readerA = null;
        readerB = null;
        this.inputBufferSize = 8192;
    }

    public SimpleCombiner(String inputA, String inputB, String output, int inputBufferSize, int outputBufferSize) {
        super(output, outputBufferSize);
        this.inputA = inputA;
        this.inputB = inputB;
        readerA = null;
        readerB = null;
        this.inputBufferSize = inputBufferSize;
    }


    @Override
    public void combine() throws IOException {


    }

    /**
     * combines sorted blocks from inputs and writes them to output in ascending order
     *
     * @param blockSize size of sorted block
     * @throws IOException
     */
    public void combine(int blockSize) throws IOException {
        log.debug(startMessage);
        String bufferA, bufferB;
        int counterA, counterB;
        init();
        bufferA = readerA.readLine();
        bufferB = readerB.readLine();
        while (bufferA != null && bufferB != null) {
            counterA = 0;
            counterB = 0;
            while (counterA < blockSize && counterB < blockSize && bufferA != null && bufferB != null) {
                if (Integer.parseInt(bufferA) < Integer.parseInt(bufferB)) {
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
        log.debug(endMessage);
    }

    /**
     * initializes writers and readers
     *
     * @throws IOException
     */
    @Override
    protected void init() throws IOException {
        super.init();
        readerA = new BufferedReader(new FileReader(inputA), inputBufferSize / 2);
        readerB = new BufferedReader(new FileReader(inputB), inputBufferSize / 2);
    }

    /**
     * closes all readers and writes
     *
     * @throws IOException
     */
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

}

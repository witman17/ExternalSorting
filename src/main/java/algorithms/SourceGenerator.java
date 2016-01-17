package algorithms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Witold on 2015-10-26.
 */
public class SourceGenerator {

    private final Logger log = LogManager.getLogger(SourceGenerator.class);
    protected String fileName;
    protected int outputBufferSize;
    protected BufferedWriter writer;

    public SourceGenerator(String fileName, int outputBufferSize) {
        this.fileName = fileName;
        this.outputBufferSize = outputBufferSize;
        this.writer = null;
    }

    public SourceGenerator() {

    }

    public void generateUniform(int bytesNumber) throws IOException {
        log.info(SourceGenerator.class.toString() + " START");
        init();
        Random random = new Random();
        int currentBytesNumber = 0;
        while (currentBytesNumber < bytesNumber) {
            Integer number = random.nextInt();
            writer.write(number.toString());
            writer.newLine();
            currentBytesNumber += number.toString().length();
        }
        close();
        log.info(SourceGenerator.class.toString() + " END");
    }

    public void generateNormal(int bytesNumber) throws IOException {
        log.info(SourceGenerator.class.toString() + " START");
        init();
        Random random = new Random();
        int currentBytesNumber = 0;
        while (currentBytesNumber < bytesNumber) {
            double d = random.nextGaussian();
            Integer number = (int) Math.round(d * ((double) Integer.MAX_VALUE) / 2.0);
            writer.write(number.toString());
            writer.newLine();
            currentBytesNumber += number.toString().length() + 2;
        }
        close();
        log.info(SourceGenerator.class.toString() + " END");
    }


    private void init() throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName), outputBufferSize);
    }

    private void close() throws IOException {
        writer.close();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }
}

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

    public void generateUniform(int vectorSize) throws IOException {
        log.info(SourceGenerator.class.toString() + " START");
        init();
        Random random = new Random();
        for (int i = vectorSize; i > 0; i--) {
            Integer number = random.nextInt();
            writeInt(number);
        }
        close();
        log.info(SourceGenerator.class.toString() + " END");
    }

    public void generateNormal(int vectorSize) throws IOException {
        log.info(SourceGenerator.class.toString() + " START");
        init();
        Random random = new Random();
        for (int i = vectorSize; i > 0; i--) {
            double d = random.nextGaussian();
            Integer number = (int) Math.round(d * 7000000.0);
            writeInt(number);
        }
        close();
        log.info(SourceGenerator.class.toString() + " END");
    }

    private void writeInt(Integer number) throws IOException {
        writer.write(number.toString());
        writer.newLine();
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

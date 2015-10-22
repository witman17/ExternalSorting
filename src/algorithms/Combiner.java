package algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public abstract class Combiner {
    protected String outputFile;
    protected BufferedWriter writer;

    public Combiner() {
    }

    public Combiner(String outputFile) {
        this.outputFile = outputFile;
        writer = null;
    }

    public abstract void combine() throws IOException;

    public abstract void combine(int blockSize) throws IOException;

    protected void init() throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
    }

    protected void close() throws IOException {
        writer.close();
        writer = null;
    }

}

package algorithms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public abstract class Combiner {
    protected final static Logger log = LogManager.getLogger("algorithms");
    protected String output;
    protected BufferedWriter writer;

    public Combiner() {
    }

    public Combiner(String outputFile) {
        this.output = outputFile;
        writer = null;
    }

    public abstract void combine() throws IOException;


    protected void init() throws IOException {
        writer = new BufferedWriter(new FileWriter(output));
    }

    protected void close() throws IOException {
        writer.close();
        writer = null;
    }

}

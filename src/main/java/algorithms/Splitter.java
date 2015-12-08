package algorithms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public abstract class Splitter {
    protected final static Logger log = LogManager.getLogger("algorithms");
    protected String input;
    protected BufferedReader reader;
    protected int inputBufferSize;

    public Splitter() {

    }

    public Splitter(String input) {
        this.input = input;
        inputBufferSize = 8192;
    }

    public Splitter(String input, int inputBufferSize) {
        this.input = input;
        this.inputBufferSize = inputBufferSize;
    }

    protected void init() throws IOException {
        reader = new BufferedReader(new FileReader(input), inputBufferSize);
    }

    protected void close() throws IOException {
        reader.close();
        reader = null;
    }

    public abstract int split() throws IOException;

}

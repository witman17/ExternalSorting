package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public abstract class Splitter {
    protected String inputFile;
    protected BufferedReader reader;

    public Splitter() {
    }

    public Splitter(String inputFile) {
        this.inputFile = inputFile;
    }

    protected void init() throws IOException {
        reader = new BufferedReader(new FileReader(inputFile));
    }

    protected void close() throws IOException {
        reader.close();
        reader = null;
    }

    public abstract boolean split() throws IOException;

}

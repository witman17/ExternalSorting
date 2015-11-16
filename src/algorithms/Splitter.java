package algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public abstract class Splitter {
    protected String input;
    protected BufferedReader reader;

    public Splitter() {

    }

    public Splitter(String input) {
        this.input = input;
    }

    protected void init() throws IOException {
        reader = new BufferedReader(new FileReader(input));
    }

    protected void close() throws IOException {
        reader.close();
        reader = null;
    }

    public abstract int split() throws IOException;

}

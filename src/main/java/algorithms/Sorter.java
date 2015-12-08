package algorithms;

import java.io.IOException;

/**
 * Created by Witold on 2015-12-08.
 */
public abstract class Sorter {
    protected Splitter splitter;
    protected Combiner combiner;
    protected int inputBufferSize;
    protected int outputBufferSize;

    public Sorter(int inputBufferSize, int outputBufferSize) {
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
    }

    public Sorter() {
        this.inputBufferSize = 8192;
        this.outputBufferSize = 8192;
    }

    abstract public void sort() throws IOException;


}

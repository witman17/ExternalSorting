package algorithms.naturaljoin;

import algorithms.simplejoin.SimpleSplitter;

import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public class NaturalSplitter extends SimpleSplitter {


    public NaturalSplitter(String inputFile, String outputA, String outputB) {
        super(inputFile, outputA, outputB);
    }

    @Override
    public boolean split(int blockSize) throws IOException {
        super.init();
        //TODO some splitting code
        super.close();
        return false;
    }
}

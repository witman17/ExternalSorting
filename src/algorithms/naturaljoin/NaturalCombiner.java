package algorithms.naturaljoin;

import algorithms.simplejoin.SimpleCombiner;

import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public class NaturalCombiner extends SimpleCombiner {


    public NaturalCombiner(String inputA, String InputB, String output) {
        super(inputA, InputB, output);
    }

    @Override
    public void combine(int blockSize) throws IOException {
        super.init();
        //TODO some combining code
        super.close();
    }
}

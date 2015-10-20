package algorithms.naturaljoin;

import algorithms.simplejoin.SimpleSplitter;

import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public class NaturalSplitter extends SimpleSplitter {

    protected SeriesReader seriesReader;

    public NaturalSplitter(String inputFile, String outputA, String outputB) {
        super(inputFile, outputA, outputB);
        seriesReader = new SeriesReader();
    }

    @Override
    public boolean split(int blockSize) throws IOException {
        String buffer;
        boolean changeFile = true;
        boolean oneSeries = true;
        super.init();
        while ((buffer = seriesReader.getSeriesElement(reader)) != null) {
            if (seriesReader.ifSeriesEnded) {
                changeFile = !changeFile;
                oneSeries = false;
            }
            if (changeFile) {
                writerA.write(buffer);
                writerA.newLine();
            } else {
                writerB.write(buffer);
                writerB.newLine();
            }
        }
        super.close();
        return oneSeries;
    }
}

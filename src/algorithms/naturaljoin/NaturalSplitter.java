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
    }


    public boolean split() throws IOException {
        String buffer;
        boolean changeFile = true;
        boolean oneSeries = true;
        init();
        while ((buffer = seriesReader.getSeriesElement()) != null) {
            if (seriesReader.isSeriesEnded()) {
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

    @Override
    protected void init() throws IOException {
        super.init();
        seriesReader = new SeriesReader(reader);
    }
}

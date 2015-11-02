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

    @Override
    public int split() throws IOException {
        String buffer;
        boolean changeFile = true;
        int seriesNumber = 1;
        init();
        while ((buffer = seriesReader.getSeriesElement()) != null) {
            if (seriesReader.isSeriesEnded()) {
                changeFile = !changeFile;
                seriesNumber++;
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
        return seriesNumber;
    }

    @Override
    protected void init() throws IOException {
        super.init();
        seriesReader = new SeriesReader(reader);
    }
}

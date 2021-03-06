package algorithms.naturaljoin;

import algorithms.SeriesReader;
import algorithms.simplejoin.SimpleSplitter;

import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public class NaturalSplitter extends SimpleSplitter {

    private final static String startMessage = NaturalSplitter.class.getSimpleName() + " - START";
    private final static String endMessage = NaturalSplitter.class.getSimpleName() + " - END";
    protected SeriesReader seriesReader;

    public NaturalSplitter(String inputFile, String outputA, String outputB) {
        super(inputFile, outputA, outputB);
    }

    public NaturalSplitter(String InputFile, String OutputA, String OutputB, int inputBufferSize, int outputBufferSize) {
        super(InputFile, OutputA, OutputB, inputBufferSize, outputBufferSize);
    }

    @Override
    public int split() throws IOException {
        log.debug(startMessage);
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
        log.debug(endMessage);
        return seriesNumber;
    }

    @Override
    protected void init() throws IOException {
        super.init();
        seriesReader = new SeriesReader(reader);
    }
}

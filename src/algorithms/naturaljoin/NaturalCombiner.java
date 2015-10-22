package algorithms.naturaljoin;

import algorithms.simplejoin.SimpleCombiner;

import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public class NaturalCombiner extends SimpleCombiner {

    protected SeriesReader seriesReaderA;
    protected SeriesReader seriesReaderB;

    public NaturalCombiner(String inputA, String InputB, String output) {
        super(inputA, InputB, output);
    }

    @Override
    public void combine() throws IOException {
        init();
        String bufferA = seriesReaderA.getSeriesElement();
        String bufferB = seriesReaderB.getSeriesElement();
        while (bufferA != null && bufferB != null) {
            do {
                if (bufferA.compareTo(bufferB) < 0) {
                    writer.write(bufferA);
                    if (!seriesReaderA.isSeriesEnded())
                        bufferA = seriesReaderA.getSeriesElement();
                } else {
                    writer.write(bufferB);
                    if (!seriesReaderB.isSeriesEnded())
                        bufferB = seriesReaderB.getSeriesElement();
                }
                writer.newLine();
                if (seriesReaderA.isSeriesEnded()) {
                    writer.write(bufferA);
                    bufferA = seriesReaderA.getSeriesElement();
                    writer.newLine();
                }
                if (seriesReaderB.isSeriesEnded()) {
                    writer.write(bufferB);
                    bufferB = seriesReaderB.getSeriesElement();
                    writer.newLine();
                }

            }
            while (bufferA != null && bufferB != null && !seriesReaderA.isSeriesEnded() && !seriesReaderB.isSeriesEnded());
            while (!seriesReaderA.isSeriesEnded() && bufferA != null) {
                writer.write(bufferA);
                writer.newLine();
                bufferA = seriesReaderA.getSeriesElement();
            }
            while (!seriesReaderB.isSeriesEnded() && bufferB != null) {
                writer.write(bufferB);
                writer.newLine();
                bufferB = seriesReaderB.getSeriesElement();
            }
        }
        while (bufferA != null) {
            writer.write(bufferA);
            writer.newLine();
            bufferA = seriesReaderA.getSeriesElement();
        }
        while (bufferB != null) {
            writer.write(bufferB);
            writer.newLine();
            bufferB = seriesReaderB.getSeriesElement();
        }
        super.close();
    }

    @Override
    protected void init() throws IOException {
        super.init();
        seriesReaderA = new SeriesReader(readerA);
        seriesReaderB = new SeriesReader(readerB);
    }
}

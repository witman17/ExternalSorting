package algorithms.naturaljoin;

import algorithms.SeriesReader;
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

    public NaturalCombiner(String inputA, String inputB, String output, int inputBufferSize, int outputBufferSize) {
        super(inputA, inputB, output, inputBufferSize, outputBufferSize);
    }


    @Override
    public void combine() throws IOException {
        log.debug("START");
        init();
        //pobranie pierwszych dwoch elementow
        String bufferA = seriesReaderA.getSeriesElement();
        String bufferB = seriesReaderB.getSeriesElement();
        while (bufferA != null && bufferB != null) {
            // por�wnanie dwoch element�w serii, przepisanie mniejszego
            do {
                if (Integer.parseInt(bufferA) < Integer.parseInt(bufferB)) {
                    writer.write(bufferA);
                    bufferA = seriesReaderA.getSeriesElement();
                } else {
                    writer.write(bufferB);
                    bufferB = seriesReaderB.getSeriesElement();
                }
                writer.newLine();
            }
            while (bufferA != null && bufferB != null && !seriesReaderA.isSeriesEnded() && !seriesReaderB.isSeriesEnded());
            //dopisanie do pozosta�ych element�w w serri kt�rej elementy si� nie sko�czy�y
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
            seriesReaderA.resetSeriesEnd();
            seriesReaderB.resetSeriesEnd();
        }
        //dopisanie pozosta�ych element�w w pliku
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
        log.debug("END");
    }

    @Override
    protected void init() throws IOException {
        super.init();
        seriesReaderA = new SeriesReader(readerA);
        seriesReaderB = new SeriesReader(readerB);
    }
}

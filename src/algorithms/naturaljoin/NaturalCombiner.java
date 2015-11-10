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

    public NaturalCombiner() {
        super();

    }

    @Override
    public void combine() throws IOException {
        init();
        //pobranie pierwszych dwoch elementow
        String bufferA = seriesReaderA.getSeriesElement();
        String bufferB = seriesReaderB.getSeriesElement();
        while (bufferA != null && bufferB != null) {
            // porównanie dwoch elementów serii, przepisanie mniejszego
            do {
                if (bufferA.compareTo(bufferB) < 0) {
                    writer.write(bufferA);
                    bufferA = seriesReaderA.getSeriesElement();
                } else {
                    writer.write(bufferB);
                    bufferB = seriesReaderB.getSeriesElement();
                }
                writer.newLine();
//          TODO sprawdziæ, czy dobrze dzia³a odwróceniu pêtli
            }
            while (bufferA != null && bufferB != null && !seriesReaderA.isSeriesEnded() && !seriesReaderB.isSeriesEnded());
            //dopisanie do pozosta³ych elementów w serri której elementy siê nie skoñczy³y
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
        //dopisanie pozosta³ych elementów w pliku
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

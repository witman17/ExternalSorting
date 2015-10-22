package algorithms.naturaljoin;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public class SeriesReader {


    private boolean seriesEnded;
    private String currentBuffer;
    private String nextBuffer;
    private BufferedReader reader;

    public SeriesReader(BufferedReader reader) {
        this.reader = reader;
        currentBuffer = null;
        nextBuffer = null;
        seriesEnded = false;
    }

    public String getSeriesElement() throws IOException {
        if (currentBuffer != null && nextBuffer != null)
            if (currentBuffer.compareTo(nextBuffer) <= 0)
                seriesEnded = false;
            else
                seriesEnded = true;
        else
            seriesEnded = false;
        if (currentBuffer == null && nextBuffer == null) {
            currentBuffer = reader.readLine();
            nextBuffer = reader.readLine();
        } else {
            currentBuffer = nextBuffer;
            nextBuffer = reader.readLine();
        }
        //sprawdzanie koñca serii

        return currentBuffer;
    }

    public boolean isSeriesEnded() {
        return seriesEnded;
    }
}

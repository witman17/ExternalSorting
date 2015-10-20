package algorithms.naturaljoin;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Witold on 2015-10-20.
 */
public class SeriesReader {


    protected boolean ifSeriesEnded;
    protected String currentBuffer;
    protected String nextBuffer;

    public SeriesReader() {
        currentBuffer = null;
        nextBuffer = null;
        ifSeriesEnded = false;
    }

    public String getSeriesElement(BufferedReader reader) throws IOException {
        if (currentBuffer == null && nextBuffer == null) {
            currentBuffer = reader.readLine();
            nextBuffer = reader.readLine();
        } else {
            currentBuffer = nextBuffer;
            nextBuffer = reader.readLine();
        }
        //sprawdzanie koñca serii
        if (currentBuffer != null && nextBuffer != null)
            if (currentBuffer.compareTo(nextBuffer) < 0)
                ifSeriesEnded = false;
            else
                ifSeriesEnded = true;
        else
            ifSeriesEnded = false;
        return currentBuffer;
    }

    public boolean isIfSeriesEnded() {
        return ifSeriesEnded;
    }
}

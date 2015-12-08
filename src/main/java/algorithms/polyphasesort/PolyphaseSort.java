package algorithms.polyphasesort;

import algorithms.Sorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * Created by Witold on 2015-11-02.
 */
public class PolyphaseSort extends Sorter {
    protected final static Logger log = LogManager.getLogger("algorithms");
    protected PolyphaseSplitter splitter;
    protected String output;


    public PolyphaseSort(String source, String sortedFile, String tempA, String tempB) {
        super();
        splitter = new PolyphaseSplitter(source, tempA, tempB);
        output = sortedFile;
    }

    public PolyphaseSort(String source, String sortedFile, String tempA, String tempB, int inputBufferSize, int outputBufferSize) {
        super(inputBufferSize, outputBufferSize);
        splitter = new PolyphaseSplitter(source, tempA, tempB);
        output = sortedFile;
    }

    public void sort(int blockSize) throws IOException {
        log.debug("START - POLYPHASE SORT");
        int fakeSeriesNumber = 0;
        //podzia� i sortowanie serii
        int totalSeriesNumber = splitter.split(blockSize);
        int seriesNumberA = splitter.getSeriesNumberA();
        int seriesNumberB = splitter.getSeriesNumberB();
        //sprawdzenie ilo�ci, czy jest liczb� fibonacciego
        int fixedSeriesNumber = Fibonacci.nextFibonacci(totalSeriesNumber);
        if (fixedSeriesNumber > totalSeriesNumber)
            fakeSeriesNumber = fixedSeriesNumber - totalSeriesNumber;
        //uzupe�nienie liczby serii do liczby fibonacciego
        if (fakeSeriesNumber > 0) {
            if (Fibonacci.isFibonacci(seriesNumberA) && Fibonacci.isFibonacci(seriesNumberB) || !Fibonacci.isFibonacci(seriesNumberA))
                seriesNumberA += fakeSeriesNumber;
            if (!Fibonacci.isFibonacci(seriesNumberB))
                seriesNumberB += fakeSeriesNumber;
        }
        // ��czenie serii
        PolyphaseCombiner combiner = new PolyphaseCombiner(splitter.getOutputA(), splitter.getOutputB(),
                output, seriesNumberA, seriesNumberB);
        combiner.combine();
        clean(combiner.getOutput(), combiner.getInputA(), combiner.getInputB());
        log.debug("END - POLYPHASE SORT");
    }

    protected void clean(String resultFile, String tempA, String tempB) throws IOException {
        Files.move(Paths.get(resultFile), Paths.get(output), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        Files.delete(Paths.get(tempA));
        Files.delete(Paths.get(tempB));
    }
    

    @Deprecated
    public void sort() {

    }
}

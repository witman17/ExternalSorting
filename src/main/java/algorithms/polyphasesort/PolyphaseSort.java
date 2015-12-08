package algorithms.polyphasesort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * Created by Witold on 2015-11-02.
 */
public class PolyphaseSort {
    protected PolyphaseSplitter splitter;
    protected String output;


    public PolyphaseSort(String source, String sortedFile, String tempA, String tempB) {
        super();
        splitter = new PolyphaseSplitter(source, tempA, tempB);
        output = sortedFile;
    }

    public void sort(int blockSize) throws IOException {
        int fakeSeriesNumber = 0;
        //podzia³ i sortowanie serii
        int totalSeriesNumber = splitter.split(blockSize);
        int seriesNumberA = splitter.getSeriesNumberA();
        int seriesNumberB = splitter.getSeriesNumberB();
        //sprawdzenie iloœci, czy jest liczb¹ fibonacciego
        int fixedSeriesNumber = Fibonacci.nextFibonacci(totalSeriesNumber);
        if (fixedSeriesNumber > totalSeriesNumber)
            fakeSeriesNumber = fixedSeriesNumber - totalSeriesNumber;
        //uzupe³nienie liczby serii do liczby fibonacciego
        if (fakeSeriesNumber > 0) {
            if (Fibonacci.isFibonacci(seriesNumberA) && Fibonacci.isFibonacci(seriesNumberB) || !Fibonacci.isFibonacci(seriesNumberA))
                seriesNumberA += fakeSeriesNumber;
            if (!Fibonacci.isFibonacci(seriesNumberB))
                seriesNumberB += fakeSeriesNumber;
        }
        // ³¹czenie serii
        PolyphaseCombiner combiner = new PolyphaseCombiner(splitter.getOutputA(), splitter.getOutputB(),
                output, seriesNumberA, seriesNumberB);
        combiner.combine();
        clean(combiner.getOutput());
    }

    protected void clean(String resultFile) throws IOException {
        Files.move(Paths.get(resultFile), Paths.get(output), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    public String getOutput() {
        return output;
    }
}

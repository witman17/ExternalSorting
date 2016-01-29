package algorithms.polyphasesort;

import algorithms.Sorter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * Created by Witold on 2015-11-02.
 */
public class PolyphaseSorter extends Sorter {
    private final static String startMessage = PolyphaseSorter.class.getSimpleName() + " - START";
    private final static String endMessage = PolyphaseSorter.class.getSimpleName() + " - END";
    protected PolyphaseSplitter splitter;
    protected String output;


    public PolyphaseSorter(String source, String sortedFile, String tempA, String tempB) {
        super();
        splitter = new PolyphaseSplitter(source, tempA, tempB);
        output = sortedFile;
    }

    public PolyphaseSorter(String source, String sortedFile, String tempA, String tempB, int inputBufferSize, int outputBufferSize) {
        super(inputBufferSize, outputBufferSize);
        splitter = new PolyphaseSplitter(source, tempA, tempB);
        output = sortedFile;
    }

    public void sort(int blockSize) throws IOException {
        log.info(startMessage);
        int fakeSeriesNumber = 0;
        //podzial i sortowanie serii
        int totalSeriesNumber = splitter.split(blockSize);
        if (totalSeriesNumber > 1) {
            int seriesNumberA = splitter.getSeriesNumberA();
            int seriesNumberB = splitter.getSeriesNumberB();
            //sprawdzenie ilosci serii, czy jest liczba fibonacciego
            int fixedSeriesNumber = Fibonacci.nextFibonacci(totalSeriesNumber);
            if (fixedSeriesNumber > totalSeriesNumber)
                fakeSeriesNumber = fixedSeriesNumber - totalSeriesNumber;
            //uzupelnienie liczby serii do liczby fibonacciego
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
        } else
            clean(splitter.getOutputA());
        log.info(endMessage);
    }

    protected void clean(String resultFile, String tempA, String tempB) throws IOException {
        Files.move(Paths.get(resultFile), Paths.get(output), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        if (tempA.compareTo(output) != 0)
            Files.delete(Paths.get(tempA));
        if (tempB.compareTo(output) != 0)
            Files.delete(Paths.get(tempB));
    }

    protected void clean(String resultFile) throws IOException {
        Files.move(Paths.get(resultFile), Paths.get(output), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }


    @Deprecated
    public void sort() {

    }
}

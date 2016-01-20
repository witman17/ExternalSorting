package configuration.benchmarks;

import algorithms.polyphasesort.PolyphaseSort;
import configuration.TemporaryFileBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Witold on 2016-01-19.
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PolyphaseSortBenchmark {
    private final static Logger log = LogManager.getLogger("algorithms");
    @Param("s.txt")
    protected String sourceFileName;
    @Param("r.txt")
    protected String resultFileName;
    @Param("32768")
    protected int inputBufferSize;
    @Param("32768")
    protected int outputBufferSize;
    @Param("32768")
    protected int sortMethodParameter;

    private PolyphaseSort sorter;


    @Setup
    public void setup() {
        sorter = new PolyphaseSort(sourceFileName, resultFileName, TemporaryFileBuilder.build(resultFileName, "tempA.txt"),
                TemporaryFileBuilder.build(resultFileName, "tempB.txt"), inputBufferSize, outputBufferSize);
    }

    @Benchmark
    public void runPolyphaseSortBenchmark() {
        try {
            sorter.sort(sortMethodParameter);
        } catch (IOException e) {
            log.error(e);
        }
    }




}

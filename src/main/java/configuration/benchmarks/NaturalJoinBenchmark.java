package configuration.benchmarks;

import algorithms.naturaljoin.NaturalJoinSorter;
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
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class NaturalJoinBenchmark {
    private final static Logger log = LogManager.getLogger("algorithms");
    private final static String startMessage = NaturalJoinBenchmark.class.getSimpleName() + " - START";
    private final static String endMessage = NaturalJoinBenchmark.class.getSimpleName() + " - END";
    @Param("s.txt")
    protected String sourceFileName;
    @Param("r.txt")
    protected String resultFileName;
    @Param("32768")
    protected int inputBufferSize;
    @Param("32768")
    protected int outputBufferSize;

    private NaturalJoinSorter sorter;


    @Setup
    public void setup() {
        sorter = new NaturalJoinSorter(sourceFileName, resultFileName, TemporaryFileBuilder.build(resultFileName, "tempA.txt"),
                TemporaryFileBuilder.build(resultFileName, "tempB.txt"), inputBufferSize, outputBufferSize);
        log.info(startMessage);
    }

    @Benchmark
    public void runNaturalJoinBenchmark() {
        try {
            sorter.sort();
        } catch (IOException e) {
            log.error(NaturalJoinSorter.class.getSimpleName(), e);
        }
    }

    @TearDown
    public void tearDown() {
        log.info(endMessage);
    }



}

package configuration.benchmarks;

import algorithms.simplejoin.SimpleJoinSorter;
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
public class SimpleJoinBenchmark {
    private final static Logger log = LogManager.getLogger("algorithms");
    private final static String startMessage = SimpleJoinBenchmark.class.getSimpleName() + " - START";
    private final static String endMessage = SimpleJoinBenchmark.class.getSimpleName() + " - END";
    @Param("s.txt")
    protected String sourceFileName;
    @Param("r.txt")
    protected String resultFileName;
    @Param("32768")
    protected int inputBufferSize;
    @Param("32768")
    protected int outputBufferSize;

    private SimpleJoinSorter sorter;


    @Setup
    public void setup() {
        sorter = new SimpleJoinSorter(sourceFileName, resultFileName, TemporaryFileBuilder.build(resultFileName, "tempA.txt"),
                TemporaryFileBuilder.build(resultFileName, "tempB.txt"), inputBufferSize, outputBufferSize);
        log.info(startMessage);
    }

    @Benchmark
    public void runSimpleJoinBenchmark() {
        try {
            sorter.sort();
        } catch (IOException e) {
            log.error(SimpleJoinSorter.class.getSimpleName(), e);
        }
    }

    @TearDown
    public void tearDown() {
        log.info(endMessage);
    }
}

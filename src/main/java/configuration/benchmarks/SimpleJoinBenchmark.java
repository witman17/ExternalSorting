package configuration.benchmarks;

import algorithms.simplejoin.SimpleJoinSort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


/**
 * Created by Witold on 2016-01-19.
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SimpleJoinBenchmark {

    private final static Logger log = LogManager.getLogger("algorithms");
    @Param("s.txt")
    protected String sourceFileName;
    @Param("r.txt")
    protected String resultFileName;
    @Param("32768")
    protected int inputBufferSize;
    @Param("32768")
    protected int outputBufferSize;

    private SimpleJoinSort sorter;


    @Setup
    public void setup() {
        sorter = new SimpleJoinSort(sourceFileName, resultFileName, temporaryNameBuilding("tempA.txt"), temporaryNameBuilding("tempB.txt"),
                inputBufferSize, outputBufferSize);
    }

    @Benchmark
    public void runBenchmark() {
        try {
            sorter.sort();
        } catch (IOException e) {
            log.error(e);
        }
    }

    protected String temporaryNameBuilding(String name) {
        StringBuilder builder = new StringBuilder();
        Path tempFile = Paths.get(resultFileName);
        builder.append(tempFile.getParent());
        builder.append("\\");
        builder.append(name);
        return builder.toString();
    }
}

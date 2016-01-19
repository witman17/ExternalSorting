package configuration.benchmarks;

import algorithms.simplejoin.SimpleJoinSort;
import com.janosgyerik.microbench.api.annotation.Benchmark;
import com.janosgyerik.microbench.api.annotation.MeasureMemory;
import com.janosgyerik.microbench.api.annotation.MeasureTime;
import com.janosgyerik.microbench.api.annotation.Prepare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Created by Witold on 2016-01-19.
 */
@Benchmark(iterations = 5, warmUpIterations = 3)
public class SimpleJoinBenchmark extends BenchmarkTemplate {

    private final static Logger log = LogManager.getLogger("algorithms");

    protected SimpleJoinSort sorter;

    @Deprecated
    public SimpleJoinBenchmark(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize, int... sortMethodParameters) {
        super(sourceFileName, resultFileName, inputBufferSize, outputBufferSize, sortMethodParameters);
    }

    public SimpleJoinBenchmark(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize) {
        super(sourceFileName, resultFileName, inputBufferSize, outputBufferSize);
    }

    @Prepare
    public void prepare() {
        sorter = new SimpleJoinSort(sourceFileName, resultFileName, temporaryNameBuilding("tempA.txt"), temporaryNameBuilding("tempB.txt"),
                inputBufferSize, outputBufferSize);
    }

    @Override
    @MeasureTime
    @MeasureMemory
    public void runBenchmark() {
        try {
            sorter.sort();
        } catch (IOException e) {
            log.error(e);
        }
    }

    @Override
    public String toString() {
        return "sorter=" + SimpleJoinSort.class +
                " resultFileName=" + resultFileName +
                " inputBufferSize=" + inputBufferSize;
    }
}

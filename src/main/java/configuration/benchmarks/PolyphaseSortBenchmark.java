package configuration.benchmarks;

/**
 * Created by Witold on 2016-01-19.
 */
public class PolyphaseSortBenchmark extends BenchmarkTemplate {
    public PolyphaseSortBenchmark(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize, int... sortMethodParameters) {
        super(sourceFileName, resultFileName, inputBufferSize, outputBufferSize, sortMethodParameters);
    }

    public PolyphaseSortBenchmark(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize) {
        super(sourceFileName, resultFileName, inputBufferSize, outputBufferSize);
    }

    @Override
    public void runBenchmark() {

    }
}

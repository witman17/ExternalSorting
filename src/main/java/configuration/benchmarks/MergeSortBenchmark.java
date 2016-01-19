package configuration.benchmarks;

/**
 * Created by Witold on 2016-01-19.
 */
public class MergeSortBenchmark extends BenchmarkTemplate {
    public MergeSortBenchmark(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize, int... sortMethodParameters) {
        super(sourceFileName, resultFileName, inputBufferSize, outputBufferSize, sortMethodParameters);
    }

    public MergeSortBenchmark(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize) {
        super(sourceFileName, resultFileName, inputBufferSize, outputBufferSize);
    }

    @Override
    public void runBenchmark() {

    }
}

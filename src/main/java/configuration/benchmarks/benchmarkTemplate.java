package configuration.benchmarks;

import com.janosgyerik.microbench.api.annotation.Benchmark;
import com.janosgyerik.microbench.api.annotation.MeasureTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Witold on 2016-01-19.
 */
@Benchmark
public abstract class BenchmarkTemplate {
    private final static Logger log = LogManager.getLogger("algorithms");

    protected String sourceFileName;
    protected String resultFileName;
    protected int inputBufferSize;
    protected int outputBufferSize;
    protected int[] sortMethodParameters;

    public BenchmarkTemplate(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize,
                             int... sortMethodParameters) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = sortMethodParameters;

    }

    public BenchmarkTemplate(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = null;

    }

    @MeasureTime
    public abstract void runBenchmark();


    protected String temporaryNameBuilding(String name) {
        StringBuilder builder = new StringBuilder();
        Path tempFile = Paths.get(resultFileName);
        builder.append(tempFile.getParent());
        builder.append("\\");
        builder.append(name);
        return builder.toString();
    }

    @Override
    public String toString() {
        return "BenchmarkTemplate{" +
                "resultFileName='" + resultFileName + '\'' +
                ", inputBufferSize=" + inputBufferSize +
                ", outputBufferSize=" + outputBufferSize +
                ", sortMethodParameters=" + Arrays.toString(sortMethodParameters) +
                '}';
    }
}

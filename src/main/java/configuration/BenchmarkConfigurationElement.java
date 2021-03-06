package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Created by Witold on 2015-12-20.
 */

public class BenchmarkConfigurationElement implements Runnable, SourceFileOwner {
    private final static Logger log = LogManager.getLogger("algorithms");
    private static int port = 22015;
    protected String className;
    protected String sourceFileName;
    protected String resultFileName;
    protected Integer inputBufferSize;
    protected Integer outputBufferSize;
    protected Integer sortMethodParameter;

    protected int warmUpIterations;
    protected int measurementIterations;
    Collection<RunResult> results;

    public BenchmarkConfigurationElement(String className, String sourceFileName, String resultFileName, Integer inputBufferSize,
                                         Integer outputBufferSize, int sortMethodParameter) {
        this.className = className;
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameter = sortMethodParameter;
        this.warmUpIterations = 3;
        this.measurementIterations = 5;
    }

    public BenchmarkConfigurationElement(String className, String sourceFileName, String resultFileName, Integer inputBufferSize,
                                         Integer outputBufferSize, int sortMethodParameter, int warmUpIterations, int measurementIterations) {
        this.className = className;
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameter = sortMethodParameter;
        this.warmUpIterations = warmUpIterations;
        this.measurementIterations = measurementIterations;
    }

    @Override
    public void run() {
        Options options;
        StringBuilder tempName = new StringBuilder();
        tempName.append(TemporaryFileBuilder.build(resultFileName, className));
        tempName.append("Log.txt");
        if (sortMethodParameter == 0) {
            options = new OptionsBuilder().include(className)
                    .param("sourceFileName", sourceFileName)
                    .param("resultFileName", resultFileName)
                    .param("inputBufferSize", inputBufferSize.toString())
                    .param("outputBufferSize", outputBufferSize.toString())
                    //                .param("sortMethodParameter", sortMethodParameter.toString())
                    .warmupIterations(warmUpIterations)
                    .measurementIterations(measurementIterations)
                    .jvmArgs(getConsoleParam())
                    .output(TemporaryFileBuilder.build(resultFileName, className + "_log.txt"))
//                    .result(TemporaryFileBuilder.build(resultFileName, className + "_res.txt"))
                    .build();
        } else {
            options = new OptionsBuilder().include(className)
                    .param("sourceFileName", sourceFileName)
                    .param("resultFileName", resultFileName)
                    .param("inputBufferSize", inputBufferSize.toString())
                    .param("outputBufferSize", outputBufferSize.toString())
                    .param("sortMethodParameter", sortMethodParameter.toString())
                    .warmupIterations(warmUpIterations)
                    .measurementIterations(measurementIterations)
                    .jvmArgs(getConsoleParam())
                    .output(TemporaryFileBuilder.build(resultFileName, className + "_log.txt"))
//                    .result(TemporaryFileBuilder.build(resultFileName, className + "_res.txt"))
                    .build();
        }
        try {
            results = new Runner(options).run();
            for (RunResult result : results) {
                ConfigurationManager.getInstance().addResult(result);
            }

        } catch (RunnerException e) {
            log.error(e);
        }
    }

    private String getConsoleParam() {
        StringBuilder builder = new StringBuilder();
        builder.append("-Xrunjdwp:transport=dt_socket,address=");
        builder.append(getNewPort());
        builder.append(",server=y,suspend=n");
        return builder.toString();
    }

    private int getNewPort() {
        return ++port;
    }

    public Collection<RunResult> getResults() {
        return results;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(50);
        Path result = Paths.get(resultFileName);
        builder.append(className)
                .append(" : ")
                .append("iteracje=")
                .append(measurementIterations)
                .append('[').append(warmUpIterations).append(']')
                .append(" wynik=").append(result.getFileName())
                .append(" inBuf=").append(inputBufferSize)
                .append(" outBuf=").append(outputBufferSize);
        if (sortMethodParameter > 0)
            builder.append(" parametr=").append(sortMethodParameter);
        return builder.toString();
    }

    public int getWarmUpIterations() {
        return warmUpIterations;
    }

    public int getMeasurementIterations() {
        return measurementIterations;
    }

    @Override
    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }
}

package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

/**
 * Created by Witold on 2015-12-20.
 */

public class ConfigurationElement implements Runnable {
    private final static Logger log = LogManager.getLogger("algorithms");
    private static int port = 50015;
    protected String className;
    protected String sourceFileName;
    protected String resultFileName;
    protected Integer inputBufferSize;
    protected Integer outputBufferSize;
    protected Integer sortMethodParameter;

    protected int warmUpIterations;
    protected int measurementIterations;

    public ConfigurationElement(String className, String sourceFileName, String resultFileName, Integer inputBufferSize,
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

    public ConfigurationElement(String className, String sourceFileName, String resultFileName, Integer inputBufferSize,
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
            Collection<RunResult> results = new Runner(options).run();

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

    @Override
    public String toString() {
        return className + " : " +
                "{result: " + resultFileName +
                "inBuffSize=" + inputBufferSize +
                "outBuffSize=" + outputBufferSize +
                "methodParam=" + sortMethodParameter +
                '}';
    }
}

package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Created by Witold on 2015-12-20.
 */

public class ConfigurationElement implements Runnable {

    private final static Logger log = LogManager.getLogger("algorithms");
    protected String className;
    protected String sourceFileName;
    protected String resultFileName;
    protected Integer inputBufferSize;
    protected Integer outputBufferSize;
    protected Integer[] sortMethodParameters;

    protected int warmupIterations;
    protected int measurementIterations;

    public ConfigurationElement(String className, String sourceFileName, String resultFileName, Integer inputBufferSize,
                                Integer outputBufferSize, Integer[] sortMethodParameters) {
        this.className = className;
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = sortMethodParameters;
        this.warmupIterations = 3;
        this.measurementIterations = 5;
    }

    public ConfigurationElement(String className, String sourceFileName, String resultFileName, Integer inputBufferSize,
                                Integer outputBufferSize, Integer[] sortMethodParameters, int warmupIterations, int measurementIterations) {
        this.className = className;
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = sortMethodParameters;
        this.warmupIterations = warmupIterations;
        this.measurementIterations = measurementIterations;
    }

    @Override
    public void run() {
        Options options = new OptionsBuilder().include(className)
                .param("sourceFileName", sourceFileName)
                .param("resultFileName", resultFileName)
                .param("inputBufferSize", inputBufferSize.toString())
                .param("outputBufferSize", outputBufferSize.toString())
                //                .param("sortMethodParameters", sortMethodParameters.toString())
                .warmupIterations(warmupIterations)
                .measurementIterations(measurementIterations)
                .jvmArgs("-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n")
                .output("wyniki.txt")
                .build();
        try {
            new Runner(options).run();
        } catch (RunnerException e) {
            log.error(e);
        }


    }

//    private void openOutput() throws IOException {
//        if (!Files.exists(Paths.get("wyniki.txt")))
//            Files.createFile(Paths.get("wyniki.txt"));
//        origin = System.out;
//        PrintStream fileOutput = new PrintStream(new FileOutputStream("wyniki.txt"));
//        System.setOut(fileOutput);
//    }
//
//    private void closeOutput() {
//        if (origin != null) {
//            PrintStream fileOutput = System.out;
//            System.setOut(origin);
//            fileOutput.close();
//        }
//    }

}

package configuration;

import com.janosgyerik.microbench.api.BenchmarkRunner;
import configuration.benchmarks.BenchmarkTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Witold on 2015-12-20.
 */

public class ConfigurationElement implements Runnable {

    private final static Logger log = LogManager.getLogger("algorithms");
    protected BenchmarkTemplate benchmark;
    protected PrintStream origin;

    public ConfigurationElement(BenchmarkTemplate benchmark) {
        this.benchmark = benchmark;
    }

    @Override
    public void run() {
        try {
            openOutput();
            BenchmarkRunner.run(benchmark);
            closeOutput();
        } catch (IOException e) {
            log.error(e);
        }
    }

    private void openOutput() throws IOException {
        if (!Files.exists(Paths.get("wyniki.txt")))
            Files.createFile(Paths.get("wyniki.txt"));
        origin = System.out;
        PrintStream fileOutput = new PrintStream(new FileOutputStream("wyniki.txt"));
        System.setOut(fileOutput);
    }

    private void closeOutput() {
        if (origin != null) {
            PrintStream fileOutput = System.out;
            System.setOut(origin);
            fileOutput.close();
        }
    }

    @Override
    public String toString() {
        return benchmark.toString();
    }
}

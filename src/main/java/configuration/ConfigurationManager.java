package configuration;

import configuration.benchmarks.MergeSortBenchmark;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Witold on 2015-12-20.
 */
public final class ConfigurationManager extends Observable {
    private static ConfigurationManager manager = null;
    private ArrayList<Runnable> configurationElements;
    private GeneratorConfigurationElement generatorConfigurationElement;

    private int maxResultsNumber;
    private ArrayList<RunResult> resultsList;

    private ConfigurationManager() {
        configurationElements = new ArrayList<>(5);
        resultsList = new ArrayList<>(5);
        maxResultsNumber = 0;
    }

    public static ConfigurationManager getInstance() {
        if (manager == null) {
            manager = new ConfigurationManager();
        }
        return manager;
    }

    public void runConfiguration() {
        resultsList.clear();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(generatorConfigurationElement);
        configurationElements.forEach(executor::execute);
    }

    public void addConfigurationElement(Runnable configurationElement) {
        configurationElements.add(configurationElement);
        if (configurationElement instanceof BenchmarkConfigurationElement) {
            if (((BenchmarkConfigurationElement) configurationElement).className.equals("MergeSortBenchmark"))
                maxResultsNumber += 2;
            else
                maxResultsNumber++;

        }
    }

    public ArrayList<Result> getResults() throws InterruptedException {
        ArrayList<Result> results = new ArrayList<>();
        for (RunResult result : resultsList)
            results.add(result.getPrimaryResult());
        return results;
    }

    public ArrayList<String> getBenchmarksNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Runnable element : configurationElements) {
            if (element instanceof BenchmarkConfigurationElement) {
                BenchmarkConfigurationElement el = (BenchmarkConfigurationElement) element;
                String name = el.className;
                if (name.equals(MergeSortBenchmark.class.getSimpleName())) {
                    names.add(name + " (4-Pliki)");
                    names.add(name + " (N-Plików)");
                } else
                    names.add(name);
            }
        }
        return names;
    }


    public void removeConfigurationElement(int index) {
        Runnable configurationElement = configurationElements.get(index);
        if (configurationElement instanceof BenchmarkConfigurationElement) {
            if (((BenchmarkConfigurationElement) configurationElement).className.equals("MergeSortBenchmark"))
                maxResultsNumber -= 2;
            else
                maxResultsNumber--;
        }
        configurationElements.remove(index);
    }


    public void setGeneratorConfigurationElement(GeneratorConfigurationElement generatorConfigurationElement) {
        this.generatorConfigurationElement = generatorConfigurationElement;
    }

    public void addResult(RunResult result) {
        resultsList.add(result);
        if (resultsList.size() >= maxResultsNumber) {
            setChanged();
            notifyObservers();
        }

    }

    public int getIterationsNumber() {
        int iterations = 0;
        for (Runnable element : configurationElements) {
            if (element instanceof BenchmarkConfigurationElement) {
                BenchmarkConfigurationElement el = (BenchmarkConfigurationElement) element;
                if (el.className.equals(MergeSortBenchmark.class.getSimpleName()))
                    iterations += (el.measurementIterations + el.warmUpIterations) * 2;
                else
                    iterations += (el.measurementIterations + el.warmUpIterations);
            } else
                iterations++;
        }
        return iterations;
    }


    public void updateSourceFileName(String sourceFileName) {
        for (Runnable owner : configurationElements) {
            SourceFileOwner sourceOwner = (SourceFileOwner) owner;
            sourceOwner.setSourceFileName(sourceFileName);
        }
    }
}

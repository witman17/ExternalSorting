package configuration;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Witold on 2015-12-20.
 */
public class SortingConfigurationManager {
    protected ArrayList<Runnable> configurationElements;
    GeneratorConfigurationElement generatorConfigurationElement;

    public SortingConfigurationManager() {
        configurationElements = new ArrayList<>(5);
    }

    public void runConfiguration() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(generatorConfigurationElement);
        configurationElements.forEach(executor::execute);
    }

    public void addConfigurationElement(Runnable configurationElement) {
        configurationElements.add(configurationElement);
    }


    public void removeConfigurationElement(int index) {
        configurationElements.remove(index);
    }


    public void setGeneratorConfigurationElement(GeneratorConfigurationElement generatorConfigurationElement) {
        this.generatorConfigurationElement = generatorConfigurationElement;
    }
}

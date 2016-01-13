package configuration;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Witold on 2015-12-20.
 */
public class SortingConfigurationManager {
    protected ArrayList<SortingConfigurationElement> configurationElements;


    public SortingConfigurationManager() {
        configurationElements = new ArrayList<>(5);
    }

    public void runConfiguration() {
        Executor executor = Executors.newSingleThreadExecutor();
        configurationElements.forEach(executor::execute);
    }

    public void addConfigurationElement(SortingConfigurationElement configurationElement) {
        configurationElements.add(configurationElement);
    }

    public void removeConfigurationElement(int index) {
        configurationElements.remove(index);
    }

    public void setSourceFileName(String sourceFileName) {
        for (SortingConfigurationElement element : configurationElements)
            element.setSourceFileName(sourceFileName);
    }
}

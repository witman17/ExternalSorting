package configuration;

import java.util.ArrayList;

/**
 * Created by Witold on 2015-12-20.
 */
public class SortingConfigurationManager {
    protected ArrayList<SortingConfigurationElement> configurationElements;


    public SortingConfigurationManager() {
        configurationElements = new ArrayList<>(5);
    }

    public void runConfiguration() {


    }

    public void addConfigurationElement(SortingConfigurationElement configurationElement) {
        configurationElements.add(configurationElement);
    }

    public void removeConfigurationElement(int index) {
        configurationElements.remove(index);
    }
}

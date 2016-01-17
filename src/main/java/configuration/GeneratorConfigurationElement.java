package configuration;

import algorithms.SourceGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Witold on 2016-01-16.
 */
public class GeneratorConfigurationElement implements Runnable {
    private final static Logger log = LogManager.getLogger("algorithms");

    protected String sourcefileName;
    protected int outputBufferSize;
    protected String methodName;
    protected int argument;

    public GeneratorConfigurationElement(String sourcefileName, int outputBufferSize, String methodName, int argument) {
        this.sourcefileName = sourcefileName;
        this.outputBufferSize = outputBufferSize;
        this.methodName = methodName;
        this.argument = argument;
    }

    @Override
    public void run() {
        SourceGenerator generator = new SourceGenerator(sourcefileName, outputBufferSize);
        try {
            Method method = SourceGenerator.class.getMethod(methodName, int.class);
            method.invoke(generator, argument);
        } catch (NoSuchMethodException e) {
            log.error(this.toString(), e);
        } catch (InvocationTargetException e) {
            try {
                throw e.getTargetException();
            } catch (Throwable throwable) {
                log.error(this.toString(), e);
            }
        } catch (IllegalAccessException e) {
            log.error(this.toString(), e);
        }

    }
}

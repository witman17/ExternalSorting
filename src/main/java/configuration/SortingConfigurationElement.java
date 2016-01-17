package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Witold on 2015-12-20.
 */

public class SortingConfigurationElement implements Runnable {

    private final static String tempName = "temp_";
    private final static Logger log = LogManager.getLogger("algorithms");


    protected String sourceFileName;
    protected String resultFileName;
    protected String className;
    protected String methodName;
    protected int inputBufferSize;
    protected int outputBufferSize;
    protected int[] sortMethodParameters;


    public SortingConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize,
                                       String className, String methodName, int... sortMethodParameters) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = sortMethodParameters;
        this.className = className;
        this.methodName = methodName;
    }

    public SortingConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize,
                                       String className, String methodName) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = null;
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public void run() {
        try {
            Class[] constructorParams = {String.class, String.class, String.class, String.class, int.class, int.class};
            String tempA = tempName + className + "A";
            String tempB = tempName + className + "B";
            Class[] methodParamsClasses = null;
            Method method;

            Class sorterClass = Class.forName(className);
            Object sorter = sorterClass.getConstructor(constructorParams).newInstance(sourceFileName, resultFileName, tempA, tempB,
                    inputBufferSize, outputBufferSize);
            if (sortMethodParameters != null) {
                methodParamsClasses = new Class[sortMethodParameters.length];
                for (int i = 0; i < sortMethodParameters.length; i++) {
                    methodParamsClasses[i] = int.class;
                }
                method = sorterClass.getDeclaredMethod(methodName, methodParamsClasses);
                //TODO zrobic kiedys elegancko
                if (sortMethodParameters.length == 1)
                    method.invoke(sorter, sortMethodParameters[0]);
                else
                    method.invoke(sorter, sortMethodParameters[0], sortMethodParameters[1]);
            } else {
                method = sorterClass.getDeclaredMethod(methodName);
                method.invoke(sorter);
            }

        } catch (ClassNotFoundException e) {
            log.error(this.toString(), e);
        } catch (NoSuchMethodException e) {
            log.error(this.toString(), e);
        } catch (IllegalAccessException e) {
            log.error(this.toString(), e);
        } catch (InstantiationException e) {
            log.error(this.toString(), e);
        } catch (InvocationTargetException e) {
            try {
                throw e.getTargetException();
            } catch (Throwable throwable) {
                log.error(this.toString(), throwable);
            }
        }
    }


    private long getBlockSize() throws IOException {
        long fileSize = Files.size(Paths.get(sourceFileName)) / 4;
        return fileSize / 10;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public int getInputBufferSize() {
        return inputBufferSize;
    }

    public void setInputBufferSize(int inputBufferSize) {
        this.inputBufferSize = inputBufferSize;
    }

    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    public int[] getSortMethodParameters() {
        return sortMethodParameters;
    }

    public void setSortMethodParameters(int[] sortMethodParameters) {
        this.sortMethodParameters = sortMethodParameters;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        Pattern pattern = Pattern.compile("[A-Z_]($[A-Z_]|[\\w_])*");
        Matcher matcher = pattern.matcher(className);
        String name = " ";
        if (matcher.find())
            name = matcher.group(0);
        return "[ " + name + " ]:" +
                " inBuffer=" + inputBufferSize +
                ", outBuffer=" + outputBufferSize +
                ", methodParameters=" + Arrays.toString(sortMethodParameters) +
                ", resultFileName='" + resultFileName + '\'';
    }
}

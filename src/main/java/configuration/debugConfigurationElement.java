package configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Witold on 2015-12-20.
 */

public class DebugConfigurationElement implements Runnable {

    private final static String tempName = "_temp";
    private final static Logger log = LogManager.getLogger("algorithms");


    protected String sourceFileName;
    protected String resultFileName;
    protected String className;
    protected String methodName;
    protected int inputBufferSize;
    protected int outputBufferSize;
    protected int[] sortMethodParameters;


    public DebugConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize,
                                     String className, String methodName, int... sortMethodParameters) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = sortMethodParameters;
        this.className = className;
        this.methodName = methodName;
    }

    public DebugConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize,
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
            StringBuilder builderA = new StringBuilder();
            StringBuilder builderB = new StringBuilder();
            Class[] methodParamsClasses = null;
            Method method;
            Class sorterClass = Class.forName(className);
            builderA.append(TemporaryFileBuilder.build(resultFileName, sorterClass.getSimpleName()));
            builderA.append(tempName);
            builderA.append("A.txt");
            builderB.append(TemporaryFileBuilder.build(resultFileName, sorterClass.getSimpleName()));
            builderB.append(tempName);
            builderB.append("B.txt");
            Object sorter = sorterClass.getConstructor(constructorParams).newInstance(sourceFileName, resultFileName, builderA.toString(),
                    builderB.toString(), inputBufferSize, outputBufferSize);
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

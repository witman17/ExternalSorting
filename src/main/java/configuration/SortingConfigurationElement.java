package configuration;

/**
 * Created by Witold on 2015-12-20.
 */
public abstract class SortingConfigurationElement {
    public static final int MERGE_SORT = 0;
    public static final int NATURAL_JOIN_SORT = 1;
    public static final int POLYPHASE_SORT = 2;
    public static final int SIMPLE_JOIN_SORT = 3;

    public static final int BASIC_VARIANT = 11;
    public static final int TWO_WAY_N_FILES_VARIANT = 12;
    public static final int TWO_WAY_4_FILES_VARIANT = 13;
    public static final int K_WAY_VARIANT = 14;


    protected String sourceFileName;
    protected String resultFileName;
    protected int inputBufferSize;
    protected int outputBufferSize;
    protected int[] sortMethodParameters;

    public SortingConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize, int[] sortMethodParameters) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = sortMethodParameters;
    }

    public SortingConfigurationElement(String sourceFileName, String resultFileName, int inputBufferSize, int outputBufferSize, int sortMethodParameter) {
        this.sourceFileName = sourceFileName;
        this.resultFileName = resultFileName;
        this.inputBufferSize = inputBufferSize;
        this.outputBufferSize = outputBufferSize;
        this.sortMethodParameters = new int[]{sortMethodParameter};
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
}

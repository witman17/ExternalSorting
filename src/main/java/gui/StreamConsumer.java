package gui;


import javax.swing.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Witold on 2016-01-22.
 */
public class StreamConsumer {
    protected JTextArea primaryTextArea;
    protected JTextArea secondaryTextArea;
    protected TestingDialog parent;
    private int iterationNumber;
    private HashSet<String> errors;

    public StreamConsumer(JTextArea primaryTextArea) {
        this.primaryTextArea = primaryTextArea;
        secondaryTextArea = null;
        errors = new HashSet<>();
        iterationNumber = 1;
    }

    public StreamConsumer(JTextArea primaryTextArea, JTextArea secondaryTextArea, TestingDialog parent) {
        this.primaryTextArea = primaryTextArea;
        this.secondaryTextArea = secondaryTextArea;
        this.parent = parent;
        errors = new HashSet<>();
        iterationNumber = 1;
    }

    public void consume(String s) {
        primaryTextArea.append(s);
        if (secondaryTextArea != null) {
            Pattern patternBenchmark = Pattern.compile("(INFO)[\\w\\n,.\\-_:\\s]*(Benchmark)+[\\w\\n,.\\-_:\\s]*");
            Pattern basicPattern = Pattern.compile("(INFO)[\\w\\n,.\\-_:\\s]*");
            Pattern errorPattern = Pattern.compile("(ERROR)[\\w\\n,.\\-_:\\s]*");
            Pattern sortClassPattern = Pattern.compile("(\\w)*Sorter");
            Pattern startPattern = Pattern.compile("START");
            Pattern endPattern = Pattern.compile("END");
            Matcher matcher = basicPattern.matcher(s);
            if (matcher.matches()) {
                matcher.usePattern(patternBenchmark);
                if (!matcher.matches()) {
                    StringBuilder builder = new StringBuilder(50);
                    builder.append(String.format("ITERATION %2d : ", iterationNumber));
                    matcher.usePattern(sortClassPattern);
                    matcher.matches();
                    if (matcher.find())
                        builder.append(matcher.group());
                    matcher.usePattern(startPattern);
                    if (matcher.find()) {
                        builder.append("\n");
                        secondaryTextArea.append(builder.toString());
                        iterationNumber++;
                    } else {
                        matcher.usePattern(endPattern);
                        if (matcher.find()) {
                            JProgressBar bar = parent.getProgressBar();
                            bar.setValue(bar.getValue() + 1);
                        }
                    }
                    secondaryTextArea.append(s);
                }
            } else {
                matcher.usePattern(errorPattern);
                if (matcher.matches()) {
                    matcher.usePattern(sortClassPattern);
                    matcher.matches();
                    if (matcher.find()) {
                        errors.add(matcher.group());
                    }
                }
            }
        }
    }

    public Set<String> getErrors() {
        return errors;
    }
}

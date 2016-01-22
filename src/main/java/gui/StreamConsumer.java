package gui;

import javax.swing.*;

/**
 * Created by Witold on 2016-01-22.
 */
public class StreamConsumer {
    JTextArea textArea;

    public StreamConsumer(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void consume(String s) {
        textArea.append(s);
    }
}

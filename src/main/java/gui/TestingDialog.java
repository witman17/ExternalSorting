package gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintStream;

public class TestingDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextArea debugLogTextArea;
    private JTextArea iterationInfoTextArea;
    private JProgressBar progressBar;
    private StreamConsumer consumer;
    private MainWindow parent;
    private PrintStream oldPrintStream;

    public TestingDialog(MainWindow parent, int iterationsNumber) throws IOException {
        this.parent = parent;
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        progressBar.setMinimum(0);
        progressBar.setMaximum(iterationsNumber);
        polesInit();
        consumer = new StreamConsumer(debugLogTextArea, iterationInfoTextArea, this);
        oldPrintStream = System.out;
        System.setOut(new PrintStream(new StreamCapturer(oldPrintStream, consumer)));
        setVisible(true);
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void onCancel() {
        System.setOut(oldPrintStream);
        setVisible(false);
        dispose();
    }

    public void close() {
        onCancel();
    }

    private void polesInit() {
        debugLogTextArea.append("Generowanie pliku źródłowego...\n");
        progressBar.setStringPainted(true);
        progressBar.setValue(progressBar.getMinimum());
        DefaultCaret dCaret = (DefaultCaret) debugLogTextArea.getCaret();
        dCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        debugLogTextArea.setEditable(false);
        DefaultCaret iCaret = (DefaultCaret) iterationInfoTextArea.getCaret();
        iCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        iterationInfoTextArea.setEditable(false);
    }

    @Override
    public MainWindow getParent() {
        return parent;
    }

    public JTextArea getDebugLogTextArea() {
        return debugLogTextArea;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }
}

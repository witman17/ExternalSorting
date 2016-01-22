package gui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintStream;

public class TestingDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextArea textArea;
    private StreamConsumer consumer;

    public TestingDialog() throws IOException {
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consumer = new StreamConsumer(textArea);
        PrintStream ps = System.out;
        System.setOut(new PrintStream(new StreamCapturer(ps, consumer)));
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

        setVisible(false);
        dispose();
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}

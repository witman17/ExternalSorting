package gui;

import javax.swing.*;
import java.awt.event.*;

public class TestingDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextArea textArea;

    public TestingDialog() {
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        textArea.setSize(600, 450);
//        setModal(true);
        pack();
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
// add your code here if necessary

        setVisible(false);
        dispose();
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}

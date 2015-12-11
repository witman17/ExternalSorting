package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Witold on 2015-12-10.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JPanel results;
    private JPanel settings;
    private JButton sourceFileButton;
    private JTextField textField1;

    public MainWindow(String title) throws HeadlessException {
        super(title);
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        sourceFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO filechooser
            }
        });
    }
}

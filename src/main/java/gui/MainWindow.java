package gui;

import configuration.SortingConfigurationElement;
import configuration.SortingConfigurationManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;

/**
 * Created by Witold on 2015-12-10.
 */
public class MainWindow extends JFrame {
    private String sourceFilePath;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JPanel results;
    private JPanel settings;
    private JButton sourceFileButton;
    private JTextField sourceFileTextField;
    private JPanel Generator;
    private JPanel LeftGenerator;
    private JPanel RightGenerator;
    private JComboBox rozkladComboBox;
    private JSlider slider;
    private JPanel Rozklad;
    private JPanel Wybor;
    private JList jlist;
    private JButton addButton;
    private JButton deleteButton;
    private JButton start;
    private JLabel sliderSizeLabel;
    private DefaultListModel<String> listModel;
    private boolean firstShow;
    private SortingConfigurationManager manager;

    public MainWindow(String title, SortingConfigurationManager manager) throws HeadlessException {
        super(title);
        this.manager = manager;
        setContentPane(rootPanel);
        polesInit();
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        sourceFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Plik żródłowy");
                chooser.setDialogType(JFileChooser.FILES_ONLY);
                chooser.setFileFilter(new FileNameExtensionFilter("Text File", "txt"));
                int returnValue = chooser.showDialog(rootPanel.getParent().getComponent(0), "Create");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    sourceFileTextField.setText(chooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlgorithmTestDialog creator = new AlgorithmTestDialog("Kreator Konfiguracji", (MainWindow) rootPanel.getParent().getParent().getParent());
                if (firstShow) {
                    listModel.removeAllElements();
                    firstShow = false;
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selected = jlist.getSelectedIndices();
                for (int i = 0; i < selected.length; i++) {
                    listModel.remove(selected[i] - i);
                    manager.removeConfigurationElement(selected[i] - i);
                }

            }
        });
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sliderSizeLabel.setText("Rozmiar pliku: " + slider.getValue() + "(MB)");
            }
        });
    }


    private void polesInit() {
        firstShow = true;
        rozkladComboBox.addItem(new String("jednorodny"));
        rozkladComboBox.addItem(new String("normalny"));
        listModel = new DefaultListModel<>();
        listModel.addElement("Dodaj Algorytmy");
        jlist.setModel(listModel);
        Dictionary table = slider.getLabelTable();
        for (int i = 200; i < 1000; i += 100) {
            table.remove(i);
        }
        slider.setLabelTable(table);
    }

    public void addConfigurationElement(SortingConfigurationElement element) {
        listModel.addElement(element.toString());
        manager.addConfigurationElement(element);
    }

    public JTextField getSourceFileTextField() {
        return sourceFileTextField;
    }

}

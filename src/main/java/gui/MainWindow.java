package gui;

import configuration.ConfigurationElement;
import configuration.GeneratorConfigurationElement;
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
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JPanel results;
    private JPanel settings;
    private JButton sourceFileButton;
    private JTextField sourceFileTextField;
    private JPanel Generator;
    private JPanel LeftGenerator;
    private JPanel RightGenerator;
    private JComboBox distributionComboBox;
    private JSlider slider;
    private JPanel Rozklad;
    private JPanel Wybor;
    private JList jlist;
    private JButton addButton;
    private JButton deleteButton;
    private JButton startButton;
    private JLabel sliderSizeLabel;
    private DefaultListModel<String> listModel;
    private boolean noElements;
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
                if (noElements) {
                    listModel.removeAllElements();
                    noElements = false;
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
                if (listModel.isEmpty()) {
                    listModel.addElement("Dodaj algorytmy.");
                    noElements = true;
                }

            }
        });
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sliderSizeLabel.setText("Rozmiar pliku: " + slider.getValue() + "(MB)");
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dataCheck();
                    String sourceFileName = sourceFileTextField.getText();
                    int outoutFileSize = slider.getValue() * 1000 * 1000;
                    int outputBufferSize = outoutFileSize / 10;
                    String methodName = getMethodName();
                    manager.setGeneratorConfigurationElement(new GeneratorConfigurationElement(sourceFileName, outputBufferSize, methodName,
                            outoutFileSize));
                    manager.runConfiguration();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPanel.getParent().getComponent(0), ex.getMessage(),
                            ex.getMessage(), JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private String getMethodName() {
        switch (distributionComboBox.getSelectedIndex()) {
            case 0:
                return "generateUniform";
            case 1:
                return "generateNormal";
            default:
                return "generateUniform";
        }

    }


    private void polesInit() {
        noElements = true;
        distributionComboBox.addItem(new String("jednorodny"));
        distributionComboBox.addItem(new String("normalny"));
        listModel = new DefaultListModel<>();
        listModel.addElement("Dodaj algorytmy.");
        jlist.setModel(listModel);
        Dictionary table = slider.getLabelTable();
        for (int i = 200; i < 1000; i += 100) {
            table.remove(i);
        }
        slider.setLabelTable(table);
    }

    public void addConfigurationElement(ConfigurationElement element) {
        listModel.addElement(element.toString());
        manager.addConfigurationElement(element);
    }

    private void dataCheck() throws Exception {
        if (sourceFileTextField.getText().isEmpty())
            throw new Exception("Brak nazwy generowanego pliku");
        if (listModel.contains("Dodaj algorytmy.") || listModel.isEmpty())
            throw new Exception("Brak wybranych algorytmów do testu");


    }

    public JTextField getSourceFileTextField() {
        return sourceFileTextField;
    }

}

package gui;

import configuration.ConfigurationManager;
import configuration.GeneratorConfigurationElement;
import graphs.GraphUtil;
import org.openjdk.jmh.results.Result;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Witold on 2015-12-10.
 */
public class MainWindow extends JFrame implements Observer {
    private final MainWindow myself = this;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JPanel resultsTab;
    private JPanel settingsTab;
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
    private ConfigurationManager manager;
    private TestingDialog testingDialog;

    private boolean noElements;

    public MainWindow(String title) throws HeadlessException {
        super(title);
        this.manager = ConfigurationManager.getInstance();
        manager.addObserver(this);
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
                AlgorithmTestDialog creator = new AlgorithmTestDialog("Kreator Konfiguracji", myself);
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
                    int outputFileSize = slider.getValue() * 1000 * 1024;
                    int outputBufferSize = outputFileSize / 10;
                    String methodName = getMethodName();
                    manager.setGeneratorConfigurationElement(new GeneratorConfigurationElement(sourceFileName, outputBufferSize, methodName,
                            outputFileSize));
                    testingDialog = new TestingDialog(myself, manager.getIterationsNumber());
                    manager.runConfiguration();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPanel.getParent().getComponent(0), ex.getMessage(),
                            ex.getMessage(), JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void showResults() {
        List<String> benchNames = manager.getBenchmarksNames();
        List<Result> results = null;
        try {
            results = manager.getResults();
            String resultUrl = GraphUtil.generateGraphURL(benchNames, results);
            JLabel label = new JLabel(new ImageIcon(ImageIO.read(new URL(resultUrl))));
            //TODO nie wyświetla się na panelu..
            resultsTab.add(label);
            tabbedPane.setSelectedIndex(1);
            testingDialog.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(myself, e, "Problem", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(myself, e, "Problem", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addConfigurationElement(Runnable element) {
        listModel.addElement(element.toString());
        manager.addConfigurationElement(element);
    }

    public JTextField getSourceFileTextField() {
        return sourceFileTextField;
    }

    private void dataCheck() throws Exception {
        if (sourceFileTextField.getText().isEmpty())
            throw new Exception("Brak nazwy generowanego pliku");
        if (listModel.contains("Dodaj algorytmy.") || listModel.isEmpty())
            throw new Exception("Brak wybranych algorytmów do testu");


    }

    private void polesInit() {
        noElements = true;
        distributionComboBox.addItem(new String("jednorodny"));
        distributionComboBox.addItem(new String("normalny"));
        listModel = new DefaultListModel<>();
        listModel.addElement("Dodaj algorytmy.");
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(25);
        jlist.setModel(listModel);
        Dictionary table = slider.getLabelTable();
        for (int i = 50; i < 1000; i += 25) {
            if (!(i % 200 == 0))
                table.remove(i);
        }
        slider.setLabelTable(table);
        resultsTab = new JPanel();

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

    @Override
    public void update(Observable o, Object arg) {
        showResults();
    }
}
